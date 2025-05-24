package modelo;

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

    public void insertar(T valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, T valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        int comparacion = valor.compareTo(nodo.valor);

        if (comparacion < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else if (comparacion > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        }

        return nodo;
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

    public void eliminar(T valor) {
        raiz = eliminarRecursivo(raiz, valor);
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

    public interface NodoVisitante<T> {
        void visitar(T valor);
    }
}
