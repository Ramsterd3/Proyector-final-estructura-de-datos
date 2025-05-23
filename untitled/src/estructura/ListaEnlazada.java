package estructura;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaEnlazada<T> implements Iterable<T> {
    private class Nodo {//Los mismo hacer esto en una clase para solo llamarlo
                        // y no tener que hacer cada que se haga un lista
        T valor;
        Nodo siguiente;

        public Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;
    private Nodo cola;
    private int tamaño;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    public void agregar(T valor) {
        Nodo nuevoNodo = new Nodo(valor);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            cola = nuevoNodo;
        }

        tamaño++;
    }

    public void agregarAlInicio(T valor) {
        Nodo nuevoNodo = new Nodo(valor);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
        }

        tamaño++;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }

        return actual.valor;
    }

    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        T valorEliminado;

        if (indice == 0) {
            valorEliminado = cabeza.valor;
            cabeza = cabeza.siguiente;

            if (cabeza == null) {
                cola = null;
            }
        } else {
            Nodo anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.siguiente;
            }

            valorEliminado = anterior.siguiente.valor;
            anterior.siguiente = anterior.siguiente.siguiente;

            if (anterior.siguiente == null) {
                cola = anterior;
            }
        }

        tamaño--;
        return valorEliminado;
    }

    public boolean contiene(T valor) {
        Nodo actual = cabeza;

        while (actual != null) {
            if (actual.valor.equals(valor)) {
                return true;
            }
            actual = actual.siguiente;
        }

        return false;
    }

    public int tamaño() {
        return tamaño;
    }

    public boolean estaVacia() {
        return tamaño == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T valor = actual.valor;
                actual = actual.siguiente;
                return valor;
            }
        };
    }
}
