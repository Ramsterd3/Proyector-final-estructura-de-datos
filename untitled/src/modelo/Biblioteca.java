package modelo;

import servicios.GestorLibros;
import servicios.GestorUsuarios;
import servicios.ServicioAutenticacion;

public class Biblioteca {
    private GestorLibros gestorLibro;
    private GestorUsuarios gestorUsuarios;
    private ServicioAutenticacion gestorUsuario;

    public Biblioteca() {
    }

    public void agregarLibro(Libro libro){
        gestorLibro.agregarLibro(libro);
    }

    public GestorLibros getGestorLibro() {
        return gestorLibro;
    }

    public void setGestorLibro(GestorLibros gestorLibro) {
        this.gestorLibro = gestorLibro;
    }

    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public void setGestorUsuarios(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
    }

    public ServicioAutenticacion getGestorUsuario() {
        return gestorUsuario;
    }

    public void setGestorUsuario(ServicioAutenticacion gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }
}
