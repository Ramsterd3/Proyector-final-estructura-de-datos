package modelo;

import servicios.GestorAdmin;
import servicios.GestorLibros;
import servicios.GestorUsuarios;
import servicios.ServicioAutenticacion;

public class Biblioteca {
    private GestorLibros gestorLibro;
    private GestorUsuarios gestorUsuarios;
    private GestorAdmin gestorAdmin;
    private ServicioAutenticacion gestorUsuario;

    public Biblioteca() {
    }

    public void agregarLibro(Libro libro){
        gestorLibro.agregarLibro(libro);
    }
    public void eliminarLibro(Libro libro){
        gestorLibro.eliminarLibro(libro);
    }

    public void agregarLector(Lector lector){
        if(!gestorUsuario.existeUsuario(lector.correo)){
            gestorUsuario.registrarUsuario(lector);
        }
    }

    public void eliminarLector(Lector lector){
        gestorUsuario.eliminarUsuario(lector.getCorreo());
    }

    public void agregarLector(Usuario usuario){
        if(!gestorUsuario.existeUsuario(usuario.correo)){
            gestorUsuario.registrarUsuario(usuario);
        }
    }
    public void agregarAdministrador(Administrador administrador){
        if(!gestorAdmin.contenido(administrador)){
            gestorAdmin.agregarAdmin(administrador);
        }
    }
    public void eliminarAdministrador(Administrador administrador){
        if(gestorAdmin.contenido(administrador)){
            gestorAdmin.eliminarAdmin(administrador);
        }
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
