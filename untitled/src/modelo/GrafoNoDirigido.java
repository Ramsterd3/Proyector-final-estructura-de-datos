package modelo;

import java.util.*;

public class GrafoNoDirigido<T> {
    private Map<T, List<T>> listaAdyacencia;
    private Map<T, Map<T, Integer>> pesos;

    public GrafoNoDirigido() {
        this.listaAdyacencia = new HashMap<>();
        this.pesos = new HashMap<>();
    }

    public void agregarVertice(T vertice) {
        if (!listaAdyacencia.containsKey(vertice)) {
            listaAdyacencia.put(vertice, new ArrayList<>());
            pesos.put(vertice, new HashMap<>());
        }
    }

    public void agregarArista(T origen, T destino) {
        agregarArista(origen, destino, 1);
    }

    public void agregarArista(T origen, T destino, int peso) {
        // Asegurar que ambos vértices existan
        agregarVertice(origen);
        agregarVertice(destino);

        // Agregar aristas en ambas direcciones (grafo no dirigido)
        listaAdyacencia.get(origen).add(destino);
        listaAdyacencia.get(destino).add(origen);

        // Guardar el peso de la arista
        pesos.get(origen).put(destino, peso);
        pesos.get(destino).put(origen, peso);
    }

    public List<T> obtenerAdyacentes(T vertice) {
        return listaAdyacencia.getOrDefault(vertice, new ArrayList<>());
    }

    public int obtenerPeso(T origen, T destino) {
        if (!pesos.containsKey(origen) || !pesos.get(origen).containsKey(destino)) {
            return Integer.MAX_VALUE;
        }
        return pesos.get(origen).get(destino);
    }

    public List<T> buscarCaminoMasCorto(T origen, T destino) {
        // Implementación de BFS para encontrar el camino más corto
        Map<T, T> padres = new HashMap<>();
        Queue<T> cola = new LinkedList<>();
        Set<T> visitados = new HashSet<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            T actual = cola.poll();

            if (actual.equals(destino)) {
                // Reconstruir el camino
                return reconstruirCamino(padres, origen, destino);
            }

            for (T adyacente : obtenerAdyacentes(actual)) {
                if (!visitados.contains(adyacente)) {
                    visitados.add(adyacente);
                    padres.put(adyacente, actual);
                    cola.add(adyacente);
                }
            }
        }

        return new ArrayList<>(); // No hay camino
    }

    private List<T> reconstruirCamino(Map<T, T> padres, T origen, T destino) {
        List<T> camino = new ArrayList<>();
        T actual = destino;

        while (actual != null) {
            camino.add(0, actual);
            actual = padres.get(actual);
        }

        return camino;
    }

    public Set<T> obtenerVertices() {
        return listaAdyacencia.keySet();
    }

    public void eliminarArista(T origen, T destino) {
        if (listaAdyacencia.containsKey(origen) && listaAdyacencia.containsKey(destino)) {
            listaAdyacencia.get(origen).remove(destino);
            listaAdyacencia.get(destino).remove(origen);

            pesos.get(origen).remove(destino);
            pesos.get(destino).remove(origen);
        }
    }

    public void eliminarVertice(T vertice) {
        if (!listaAdyacencia.containsKey(vertice)) {
            return;
        }

        // Eliminar todas las aristas que conectan con este vértice
        for (T adyacente : listaAdyacencia.get(vertice)) {
            listaAdyacencia.get(adyacente).remove(vertice);
            pesos.get(adyacente).remove(vertice);
        }

        // Eliminar el vértice
        listaAdyacencia.remove(vertice);
        pesos.remove(vertice);
    }
}
