package modelo;

import java.util.HashMap;
import java.util.Map;

public class Lector extends Usuario implements Comparable<Lector> {

    private Tipo tipo;
    private ListaEnlazada<Prestamo> historialPrestamos;
    private ListaEnlazada<Valoracion> valoracionesRealizadas;
    private Map<Lector, Integer> similitudConOtrosLectores;

    public Lector(String nombre, String apellido, String correo, String contraseña,Tipo tipo) {
        super(nombre, apellido, correo, contraseña);
        this.historialPrestamos = new ListaEnlazada<>();
        this.valoracionesRealizadas = new ListaEnlazada<>();
        this.similitudConOtrosLectores = new HashMap<>();
        this.tipo=tipo;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        historialPrestamos.agregar(prestamo);
    }

    public void agregarValoracion(Valoracion valoracion) {
        valoracionesRealizadas.agregar(valoracion);
    }

    public ListaEnlazada<Valoracion> getValoracionesRealizadas() {
        return valoracionesRealizadas;
    }

    public ListaEnlazada<Prestamo> getHistorialPrestamos() {
        return historialPrestamos;
    }

    public void actualizarSimilitudConLector(Lector otroLector, int similitud) {
        similitudConOtrosLectores.put(otroLector, similitud);
    }

    public int obtenerSimilitudConLector(Lector otroLector) {
        return similitudConOtrosLectores.getOrDefault(otroLector, 0);
    }

    public Map<Lector, Integer> getSimilitudConOtrosLectores() {
        return similitudConOtrosLectores;
    }

    @Override
    public String getTipo() {
        return "Lector";
    }

    @Override
    public int compareTo(Lector otro) {
        return this.correo.compareTo(otro.correo);
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
