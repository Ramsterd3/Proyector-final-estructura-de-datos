package modelo;

public class ColaPrioridad<T extends Comparable<T>> {
    private T[] heap;
    private int tamaño;
    private int capacidad;

    @SuppressWarnings("unchecked")
    public ColaPrioridad(int capacidad) {//Si es una cola no tiene que tener capacidad fija, hacerla ilimitada
        this.capacidad = capacidad;
        this.tamaño = 0;
        this.heap = (T[]) new Comparable[capacidad];
    }

    public void insertar(T elemento) {
        if (tamaño >= capacidad) {
            expandirCapacidad();
        }

        heap[tamaño] = elemento;
        int actual = tamaño;
        tamaño++;

        while (actual > 0 && heap[actual].compareTo(heap[padre(actual)]) < 0) {
            intercambiar(actual, padre(actual));
            actual = padre(actual);
        }
    }

    public T extraerMinimo() {
        if (tamaño <= 0) {
            return null;
        }

        T resultado = heap[0];
        heap[0] = heap[tamaño - 1];
        tamaño--;

        heapify(0);

        return resultado;
    }

    public T verMinimo() {
        if (tamaño <= 0) {
            return null;
        }
        return heap[0];
    }

    public boolean estaVacia() {
        return tamaño == 0;
    }

    public int tamaño() {
        return tamaño;
    }

    private void heapify(int indice) {
        int izquierdo = hijoIzquierdo(indice);
        int derecho = hijoDerecho(indice);
        int menor = indice;

        if (izquierdo < tamaño && heap[izquierdo].compareTo(heap[menor]) < 0) {
            menor = izquierdo;
        }

        if (derecho < tamaño && heap[derecho].compareTo(heap[menor]) < 0) {
            menor = derecho;
        }

        if (menor != indice) {
            intercambiar(indice, menor);
            heapify(menor);
        }
    }

    private int padre(int indice) {
        return (indice - 1) / 2;
    }

    private int hijoIzquierdo(int indice) {
        return 2 * indice + 1;
    }

    private int hijoDerecho(int indice) {
        return 2 * indice + 2;
    }

    private void intercambiar(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void expandirCapacidad() {
        capacidad *= 2;
        T[] nuevoHeap = (T[]) new Comparable[capacidad];
        System.arraycopy(heap, 0, nuevoHeap, 0, tamaño);
        heap = nuevoHeap;
    }
}
