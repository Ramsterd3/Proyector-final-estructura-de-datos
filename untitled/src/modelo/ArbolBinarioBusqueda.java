package modelo;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {

    private class Nodo { // Puedes mover esta clase fuera si quieres reutilizarla
        T valor;
        Nodo izquierdo;
        Nodo derecho;

        public Nodo(T valor) {
            this.valor = valor;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private Nodo raiz;
    private int tamanio; // ← Variable global para el tamaño

    public ArbolBinarioBusqueda() {
        this.raiz = null;
        this.tamanio = 0;
    }

    public boolean insertar(T valor) {
        if (raiz == null) {
            raiz = new Nodo(valor);
            tamanio++; // Nuevo nodo insertado
            return true;
        }

        boolean[] insertado = new boolean[1];
        raiz = insertarRecursivo(raiz, valor, insertado);
        if (insertado[0]) {
            tamanio++; // Solo incrementa si realmente se insertó
        }
        return insertado[0];
    }

    private Nodo insertarRecursivo(Nodo nodo, T valor, boolean[] insertado) {
        if (nodo == null) {
            insertado[0] = true;
            return new Nodo(valor);
        }

        int comparacion = valor.compareTo(nodo.valor);

        if (comparacion < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor, insertado);
        } else if (comparacion > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor, insertado);
        } else {
            insertado[0] = false;
        }

        return nodo;
    }

    public boolean contiene(T valor) {
        return buscar(valor) != null;
    }

    public T buscar(T valor) {
        return buscarRecursivo(raiz, valor);
    }

    private T buscarRecursivo(Nodo nodo, T valor) {
        if (nodo == null) {
            return null;
        }

        int comparacion = valor.compareTo(nodo.valor);

        if (comparacion == 0) {
            return nodo.valor;
        } else if (comparacion < 0) {
            return buscarRecursivo(nodo.izquierdo, valor);
        } else {
            return buscarRecursivo(nodo.derecho, valor);
        }
    }

    public boolean eliminar(T valor) {
        int tamanioAntes = tamanio;
        raiz = eliminarRecursivo(raiz, valor);
        return tamanio < tamanioAntes;
    }

    private Nodo eliminarRecursivo(Nodo nodo, T valor) {
        if (nodo == null) {
            return null;
        }

        int comparacion = valor.compareTo(nodo.valor);

        if (comparacion < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, valor);
        } else if (comparacion > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, valor);
        } else {
            tamanio--; // Nodo eliminado

            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }

            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }

            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            nodo.valor = encontrarMinimo(nodo.derecho);
            nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.valor);
        }

        return nodo;
    }

    private T encontrarMinimo(Nodo nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo.valor;
    }

    public void inorden(NodoVisitante<T> visitante) {
        inordenRecursivo(raiz, visitante);
    }

    private void inordenRecursivo(Nodo nodo, NodoVisitante<T> visitante) {
        if (nodo != null) {
            inordenRecursivo(nodo.izquierdo, visitante);
            visitante.visitar(nodo.valor);
            inordenRecursivo(nodo.derecho, visitante);
        }
    }

    public List<T> obtenerTodos() {
        List<T> lista = new ArrayList<>();
        obtenerTodosRecursivo(raiz, lista);
        return lista;
    }

    private void obtenerTodosRecursivo(Nodo nodo, List<T> lista) {
        if (nodo != null) {
            obtenerTodosRecursivo(nodo.izquierdo, lista);
            lista.add(nodo.valor);
            obtenerTodosRecursivo(nodo.derecho, lista);
        }
    }

    public interface NodoVisitante<T> {
        void visitar(T valor);
    }




    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }


    public int getTamanio() {
        return tamanio;
    }
}
