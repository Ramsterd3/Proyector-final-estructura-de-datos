package modelo;

import estructura.ListaEnlazada;

public class Libro implements Comparable<Libro> {
    private String titulo;
    private String autor;
    private int año;
    private String categoria;
    private String estado; // "disponible" o "prestado" hacer enumeracion
    private ListaEnlazada<Valoracion> valoraciones;
    private double calificacionPromedio;

    public Libro(String titulo, String autor, int año, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.año = año;
        this.categoria = categoria;
        this.estado = "disponible";
        this.valoraciones = new ListaEnlazada<>();
        this.calificacionPromedio = 0.0;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public ListaEnlazada<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void agregarValoracion(Valoracion valoracion) {
        valoraciones.agregar(valoracion);
        actualizarCalificacionPromedio();
    }

    private void actualizarCalificacionPromedio() {
        if (valoraciones.tamaño() == 0) {
            calificacionPromedio = 0.0;
            return;
        }

        double suma = 0.0;
        for (int i = 0; i < valoraciones.tamaño(); i++) {
            suma += valoraciones.obtener(i).getCalificacion();
        }

        calificacionPromedio = suma / valoraciones.tamaño();
    }

    @Override
    public int compareTo(Libro otro) {
        return this.titulo.compareTo(otro.titulo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Libro libro = (Libro) obj;
        return titulo.equals(libro.titulo) && autor.equals(libro.autor);
    }

    @Override
    public int hashCode() {
        return titulo.hashCode() + 31 * autor.hashCode();
    }
}
