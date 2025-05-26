package servicios;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private ArbolBinarioBusqueda<Lector> lectores;
    private List<Administrador> administradores;  // <-- Aquí agregamos lista para admins
    private GrafoNoDirigido<Lector> grafoAfinidad;

    public GestorUsuarios() {
        this.lectores = new ArbolBinarioBusqueda<>();
        this.administradores = new ArrayList<>();  // <-- Inicializar lista admins
        this.grafoAfinidad = new GrafoNoDirigido<>();
    }

    public void agregarLector(Lector lector) {
        lectores.insertar(lector);
        grafoAfinidad.agregarVertice(lector);
    }

    public void agregarAdministrador(Administrador admin) {
        administradores.add(admin);
    }

    public ListaEnlazada<Lector> obtenerTodosLosLectores() {
        return null;
    }


    // Resto del código que ya tienes...
}
