package estructura;

import java.util.EmptyStackException;

public class Pila<T> {
    private class Nodo {//Igua hacer clase nodo
        T valor;
        Nodo siguiente;

        public Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private Nodo cima;
    private int tamaño;

    public Pila() {
        this.cima = null;
        this.tamaño = 0;
    }

    public void push(T valor) {
        Nodo nuevoNodo = new Nodo(valor);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
        tamaño++;
    }

    public T pop() {
        if (estaVacia()) {
            throw new EmptyStackException();
        }

        T valor = cima.valor;
        cima = cima.siguiente;
        tamaño--;

        return valor;
    }

    public T peek() {
        if (estaVacia()) {
            throw new EmptyStackException();
        }

        return cima.valor;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int tamaño() {
        return tamaño;
    }

    public void vaciar() {
        cima = null;
        tamaño = 0;
    }
}
