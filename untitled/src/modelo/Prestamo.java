package modelo;

import java.util.Calendar;
import java.util.Date;

public class Prestamo {
    private Lector lector;
    private Libro libro;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private boolean devuelto;

    public Prestamo(Lector lector, Libro libro) {
        this.lector = lector;
        this.libro = libro;
        this.fechaPrestamo = new Date();

        // Fecha de devolución: 15 días después
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaPrestamo);
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        this.fechaDevolucion = calendar.getTime();

        this.devuelto = false;

        // Cambiar estado del libro
        libro.setEstado("prestado");

        // Agregar préstamo al historial del lector
        lector.agregarPrestamo(this);
    }

    public Lector getLector() {
        return lector;
    }

    public Libro getLibro() {
        return libro;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void devolver() {
        this.devuelto = true;
        libro.setEstado("disponible");
    }

    public boolean estaVencido() {
        if (devuelto) {
            return false;
        }

        Date hoy = new Date();
        return hoy.after(fechaDevolucion);
    }
}
