package modelo;

import java.util.*;

public class Lector extends Usuario implements Comparable<Lector> {


    private ListaEnlazada<Prestamo> historialPrestamos;
    private ListaEnlazada<Valoracion> valoracionesRealizadas;
    private Map<Lector, Integer> similitudConOtrosLectores;

    public Lector(String nombre, String apellido, String correo, String contraseña,Tipo tipo) {
        super(nombre, apellido, correo, contraseña,tipo);
        this.historialPrestamos = new ListaEnlazada<>();
        this.valoracionesRealizadas = new ListaEnlazada<>();
        this.similitudConOtrosLectores = new HashMap<>();

    }

    public boolean agregarPrestamo(Prestamo prestamo) {
        historialPrestamos.agregar(prestamo);
        return true;


    }

    public boolean eliminarPrestamo(Prestamo prestamo) {
        ListaEnlazada<Prestamo> nuevaLista = new ListaEnlazada<>();
        boolean eliminado = false;

        Nodo<Prestamo> nodo = historialPrestamos.getCabeza();
        while (nodo != null) {
            Prestamo actual = nodo.getValor();
            if (!eliminado && actual.getLibro().getTitulo().equals(prestamo.getLibro().getTitulo())) {
                eliminado = true; // no agregamos este
            } else {
                nuevaLista.agregar(actual); // mantenemos el resto
            }
            nodo = nodo.getSiguiente();
        }

        if (eliminado) {
            historialPrestamos = nuevaLista;
        }

        return eliminado;
    }


    public void agregarValoracion(Valoracion valoracion) {
        valoracionesRealizadas.agregar(valoracion);

    }
    public List<Prestamo> obtenerTodosPrestamos(){
        List<Prestamo>listaPrestamo=new ArrayList<>();
        Nodo<Prestamo> nodoArranque=historialPrestamos.getCabeza();
        for(int i=0;i<historialPrestamos.tamaño();i++){
            listaPrestamo.add(nodoArranque.getValor());
            nodoArranque=nodoArranque.getSiguiente();
        }
        return listaPrestamo;
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

    public void setHistorialPrestamos(ListaEnlazada<Prestamo> historialPrestamos) {
        this.historialPrestamos = historialPrestamos;
    }

    public void setValoracionesRealizadas(ListaEnlazada<Valoracion> valoracionesRealizadas) {
        this.valoracionesRealizadas = valoracionesRealizadas;
    }

    public void setSimilitudConOtrosLectores(Map<Lector, Integer> similitudConOtrosLectores) {
        this.similitudConOtrosLectores = similitudConOtrosLectores;
    }

    @Override
    public int compareTo(Lector otro) {
        return this.correo.compareTo(otro.correo);
    }


}
