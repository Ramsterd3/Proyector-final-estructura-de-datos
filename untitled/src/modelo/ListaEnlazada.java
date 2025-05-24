package modelo;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaEnlazada<T> implements Iterable<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamaño;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    public void agregar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }

        tamaño++;
    }

    public void agregarAlInicio(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
        }

        tamaño++;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getValor();
    }

    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        T valorEliminado;

        if (indice == 0) {
            valorEliminado = cabeza.getValor();
            cabeza = cabeza.getSiguiente();

            if (cabeza == null) {
                cola = null;
            }
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getSiguiente();
            }

            Nodo<T> eliminado = anterior.getSiguiente();
            valorEliminado = eliminado.getValor();
            anterior.setSiguiente(eliminado.getSiguiente());

            if (anterior.getSiguiente() == null) {
                cola = anterior;
            }
        }

        tamaño--;
        return valorEliminado;
    }

    public boolean contiene(T valor) {
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual.getValor().equals(valor)) {
                return true;
            }
            actual = actual.getSiguiente();
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
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T valor = actual.getValor();
                actual = actual.getSiguiente();
                return valor;
            }
        };
    }
}
