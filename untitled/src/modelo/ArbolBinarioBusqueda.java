package modelo;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {
    private class Nodo {//Cambiar esto y hacerlo en una clase
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

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    public boolean insertar(T valor) {
        if (raiz == null) {
            raiz = new Nodo(valor); // Árbol vacío, crea la raíz
            return true;
        }
        boolean[] insertado = new boolean[1]; // Para saber si se insertó
        raiz = insertarRecursivo(raiz, valor, insertado);
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
            insertado[0] = false; // Valor duplicado, no se inserta
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
        Nodo valorResultado = eliminarRecursivo(raiz, valor);
        if(valorResultado==null){
            return false;
        }else{
            return true;
        }
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
            // Caso 1: Nodo sin hijos
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }

            // Caso 2: Nodo con un hijo
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }

            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Caso 3: Nodo con dos hijos
            // Encontrar el sucesor inorden (mínimo en el subárbol derecho)
            nodo.valor = encontrarMinimo(nodo.derecho);

            // Eliminar el sucesor inorden
            nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.valor);
        }

        return nodo;
    }

    private T encontrarMinimo(Nodo nodo) {
        T minimo = nodo.valor;
        while (nodo.izquierdo != null) {
            minimo = nodo.izquierdo.valor;
            nodo = nodo.izquierdo;
        }
        return minimo;
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
            obtenerTodosRecursivo(nodo.izquierdo, lista); // Recorrer izquierda
            lista.add(nodo.valor);                         // Agregar valor actual
            obtenerTodosRecursivo(nodo.derecho, lista);    // Recorrer derecha
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
}
