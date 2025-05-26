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



    public ListaEnlazada<Lector> obtenerTodosLosLectores() {
        return null;
    }


    // Resto del código que ya tienes...
}
