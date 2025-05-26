package servicios;

import modelo.ArbolBinarioBusqueda;
import modelo.ColaPrioridad;
import modelo.ListaEnlazada;
import modelo.Libro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorLibros {
    private ArbolBinarioBusqueda<Libro> catalogoLibros;
    private Map<String, ListaEnlazada<Libro>> librosPorCategoria;
    private Map<String, ListaEnlazada<Libro>> librosPorAutor;
    private ColaPrioridad<Libro> librosMejorValorados;

    public GestorLibros() {
        this.catalogoLibros = new ArbolBinarioBusqueda<>();
        this.librosPorCategoria = new HashMap<>();
        this.librosPorAutor = new HashMap<>();
        this.librosMejorValorados = new ColaPrioridad<>(20);
    }

    public boolean agregarLibro(Libro libro) {

        if (catalogoLibros.insertar(libro)) {
            // Actualizar índices
            String categoria = libro.getCategoria();
            if (!librosPorCategoria.containsKey(categoria)) {
                librosPorCategoria.put(categoria, new ListaEnlazada<>());
            }
            librosPorCategoria.get(categoria).agregar(libro);

            String autor = libro.getAutor();
            if (!librosPorAutor.containsKey(autor)) {
                librosPorAutor.put(autor, new ListaEnlazada<>());
            }
            librosPorAutor.get(autor).agregar(libro);
            return true;
        }
        return false;
    }

    public boolean eliminarLibro(Libro libro) {
        Boolean resultado=catalogoLibros.eliminar(libro);
        if(resultado){
            // Actualizar índices
            String categoria = libro.getCategoria();
            if (librosPorCategoria.containsKey(categoria)) {
                for (int i = 0; i < librosPorCategoria.get(categoria).tamaño(); i++) {
                    if (librosPorCategoria.get(categoria).obtener(i).equals(libro)) {
                        librosPorCategoria.get(categoria).eliminar(i);
                        break;

                    }
                }
            }

            String autor = libro.getAutor();
            if (librosPorAutor.containsKey(autor)) {
                for (int i = 0; i < librosPorAutor.get(autor).tamaño(); i++) {
                    if (librosPorAutor.get(autor).obtener(i).equals(libro)) {
                        librosPorAutor.get(autor).eliminar(i);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Libro buscarPorTitulo(String titulo) {
        final Libro[] resultado = {null};

        catalogoLibros.inorden(new ArbolBinarioBusqueda.NodoVisitante<Libro>() {
            @Override
            public void visitar(Libro libro) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    resultado[0] = libro;
                }
            }
        });

        return resultado[0];
    }

    public ListaEnlazada<Libro> buscarPorCategoria(String categoria) {
        return librosPorCategoria.getOrDefault(categoria, new ListaEnlazada<>());
    }

    public ListaEnlazada<Libro> buscarPorAutor(String autor) {
        return librosPorAutor.getOrDefault(autor, new ListaEnlazada<>());
    }

    public void actualizarLibrosMejorValorados() {
        // Reiniciar cola de prioridad
        librosMejorValorados = new ColaPrioridad<>(20);

        catalogoLibros.inorden(new ArbolBinarioBusqueda.NodoVisitante<Libro>() {
            @Override
            public void visitar(Libro libro) {
                if (libro.getCalificacionPromedio() > 0) {
                    // Usamos un comparador inverso para que los libros con mayor calificación tengan mayor prioridad
                    final double calificacion = libro.getCalificacionPromedio();
                    Libro libroConPrioridad = new Libro(libro.getTitulo(), libro.getAutor(), libro.getAño(), libro.getCategoria()) {
                        @Override
                        public int compareTo(Libro otro) {
                            // Orden inverso para que los mejores valorados tengan mayor prioridad
                            return Double.compare(otro.getCalificacionPromedio(), calificacion);
                        }
                    };
                    librosMejorValorados.insertar(libroConPrioridad);
                }
            }
        });
    }

    public ListaEnlazada<Libro> obtenerLibrosMejorValorados(int cantidad) {
        actualizarLibrosMejorValorados();

        ListaEnlazada<Libro> resultado = new ListaEnlazada<>();
        int contador = 0;

        while (!librosMejorValorados.estaVacia() && contador < cantidad) {
            resultado.agregar(librosMejorValorados.extraerMinimo());
            contador++;
        }

        return resultado;
    }

    public List<Libro> obtenerTodosLosLibros() {
        return catalogoLibros.obtenerTodos();
    }

    public ListaEnlazada<String> obtenerTodasLasCategorias() {
        ListaEnlazada<String> categorias = new ListaEnlazada<>();

        for (String categoria : librosPorCategoria.keySet()) {
            categorias.agregar(categoria);
        }

        return categorias;
    }

    public ListaEnlazada<String> obtenerTodosLosAutores() {
        ListaEnlazada<String> autores = new ListaEnlazada<>();

        for (String autor : librosPorAutor.keySet()) {
            autores.agregar(autor);
        }

        return autores;
    }

    public ListaEnlazada<Libro> buscarLibrosPorTexto(String texto) {
        final ListaEnlazada<Libro> resultado = new ListaEnlazada<>();

        catalogoLibros.inorden(new ArbolBinarioBusqueda.NodoVisitante<Libro>() {
            @Override
            public void visitar(Libro libro) {
                if (libro.getTitulo().toLowerCase().contains(texto.toLowerCase()) ||
                        libro.getAutor().toLowerCase().contains(texto.toLowerCase()) ||
                        libro.getCategoria().toLowerCase().contains(texto.toLowerCase())) {
                    resultado.agregar(libro);
                }
            }
        });

        return resultado;
    }

    public ArbolBinarioBusqueda<Libro> getCatalogoLibros() {
        return catalogoLibros;
    }

    public void setCatalogoLibros(ArbolBinarioBusqueda<Libro> catalogoLibros) {
        this.catalogoLibros = catalogoLibros;
    }

    public Map<String, ListaEnlazada<Libro>> getLibrosPorCategoria() {
        return librosPorCategoria;
    }

    public void setLibrosPorCategoria(Map<String, ListaEnlazada<Libro>> librosPorCategoria) {
        this.librosPorCategoria = librosPorCategoria;
    }

    public Map<String, ListaEnlazada<Libro>> getLibrosPorAutor() {
        return librosPorAutor;
    }

    public void setLibrosPorAutor(Map<String, ListaEnlazada<Libro>> librosPorAutor) {
        this.librosPorAutor = librosPorAutor;
    }

    public ColaPrioridad<Libro> getLibrosMejorValorados() {
        return librosMejorValorados;
    }

    public void setLibrosMejorValorados(ColaPrioridad<Libro> librosMejorValorados) {
        this.librosMejorValorados = librosMejorValorados;
    }
}
