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
    private ServicioAutenticacion gestorUtentificar;


    public Biblioteca() {

            this.gestorLibro = new GestorLibros();
            this.gestorUsuarios = new GestorUsuarios();
            this.gestorAdmin = new GestorAdmin();
            this.gestorUtentificar = new ServicioAutenticacion();



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
        if(!gestorUtentificar.existeUsuario(lector.correo)){
            gestorUtentificar.registrarUsuario(lector);
            gestorUsuarios.agregarLector(lector);
        }
    }

    public void eliminarLector(Lector lector){
        gestorUtentificar.eliminarUsuario(lector.getCorreo());
        gestorUsuarios.eliminarLector(lector);
    }

    public void agregarLector(Usuario usuario){
        if(!gestorUtentificar.existeUsuario(usuario.correo)){
            gestorUtentificar.registrarUsuario(usuario);
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

    public ServicioAutenticacion getGestorUtentificar() {
        return gestorUtentificar;
    }

    public void setGestorUtentificar(ServicioAutenticacion gestorUtentificar) {
        this.gestorUtentificar = gestorUtentificar;
    }

    public GestorAdmin getGestorAdmin() {
        return gestorAdmin;
    }

    public void setGestorAdmin(GestorAdmin gestorAdmin) {
        this.gestorAdmin = gestorAdmin;
    }





}
