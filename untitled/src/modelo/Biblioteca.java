package modelo;

import servicios.GestorAdmin;
import servicios.GestorLibros;
import servicios.GestorUsuarios;
import servicios.ServicioAutenticacion;

import java.util.List;

public class Biblioteca {
    private GestorLibros gestorLibro;
    private GestorUsuarios gestorUsuarios;
    private GestorAdmin gestorAdmin;
    private ServicioAutenticacion gestorUsuario;
    private ServicioAutenticacion servicioAutenticacion;

    public Biblioteca() {

            this.gestorLibro = new GestorLibros();
            this.gestorUsuarios = new GestorUsuarios();
            this.gestorAdmin = new GestorAdmin();
            this.gestorUsuario = new ServicioAutenticacion();
            this.servicioAutenticacion=new ServicioAutenticacion();


    }

    public boolean agregarLibro(Libro libro){
        if(gestorLibro.agregarLibro(libro)){
            return true;
        }return false;

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
    public List<Libro> obtenerTodosLibor(){
        return gestorLibro.obtenerTodosLosLibros();
    }


    public GestorLibros getGestorLibro() {
        return gestorLibro;
    }
    public int cantidadLibros(){
        return getGestorLibro().getCatalogoLibros().getTamanio();
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

    public GestorAdmin getGestorAdmin() {
        return gestorAdmin;
    }

    public void setGestorAdmin(GestorAdmin gestorAdmin) {
        this.gestorAdmin = gestorAdmin;
    }

    public ServicioAutenticacion getServicioAutenticacion() {
        return servicioAutenticacion;
    }

    public void setServicioAutenticacion(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }
}
