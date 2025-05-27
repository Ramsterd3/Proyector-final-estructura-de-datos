package servicios;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private ArbolBinarioBusqueda<Lector> listaLectores;
    private GrafoNoDirigido<Lector> grafoAfinidad;

    public GestorUsuarios() {
        this.listaLectores = new ArbolBinarioBusqueda<>();
        this.grafoAfinidad = new GrafoNoDirigido<>();
    }

    public void agregarLector(Lector lector) {
        listaLectores.insertar(lector);
        grafoAfinidad.agregarVertice(lector);
    }
    public void eliminarLector(Lector lector){
        listaLectores.eliminar(lector);
    }
    public Lector buscarLectorCorreo(String correo){
        List<Lector> listaFiltrar=listaLectores.obtenerTodos();
        for(int i=0;i<listaFiltrar.size();i++){
            if(listaFiltrar.get(i).getCorreo().equals(correo)){
            return listaFiltrar.get(i);
        }

    }
        return null;
    }


    public List<Lector> obtenerTodosLectores(){
       return listaLectores.obtenerTodos();
    }



    public ListaEnlazada<Lector> obtenerTodosLosLectores() {
        return null;
    }

    public boolean solicitarPrestamo(Lector usuarioActual, Prestamo prestamo) {
        if(listaLectores.buscar(usuarioActual).agregarPrestamo(prestamo)){
            return true;

        }
        return false;
    }

    public ArbolBinarioBusqueda<Lector> getListaLectores() {
        return listaLectores;
    }

    public void setListaLectores(ArbolBinarioBusqueda<Lector> listaLectores) {
        this.listaLectores = listaLectores;
    }

    public GrafoNoDirigido<Lector> getGrafoAfinidad() {
        return grafoAfinidad;
    }

    public void setGrafoAfinidad(GrafoNoDirigido<Lector> grafoAfinidad) {
        this.grafoAfinidad = grafoAfinidad;
    }
    // Resto del código que ya tienes...
}
