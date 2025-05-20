package modelo;

import java.util.Date;

public class Valoracion {
    private Lector lector;
    private Libro libro;
    private int calificacion; // 1-5
    private String comentario;
    private Date fecha;

    public Valoracion(Lector lector, Libro libro, int calificacion, String comentario) {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        this.lector = lector;
        this.libro = libro;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = new Date();

        // Agregar valoración al libro
        libro.agregarValoracion(this);

        // Agregar valoración al historial del lector
        lector.agregarValoracion(this);
    }

    public Lector getLector() {
        return lector;
    }

    public Libro getLibro() {
        return libro;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public Date getFecha() {
        return fecha;
    }
}
