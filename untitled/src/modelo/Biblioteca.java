package modelo;

import servicios.GestorAdmin;
import servicios.GestorLibros;
import servicios.GestorUsuarios;
import servicios.ServicioAutenticacion;

import java.util.List;

public class Biblioteca {
    private GestorLibros gestorLibro;
    private GestorUsuarios gestorLectores;
    private GestorAdmin gestorAdmin;
    private ServicioAutenticacion gestorUtentificar;


    public Biblioteca() {

            this.gestorLibro = new GestorLibros();
            this.gestorLectores = new GestorUsuarios();
            this.gestorAdmin = new GestorAdmin();
            this.gestorUtentificar = new ServicioAutenticacion();



    }

    public boolean agregarLibro(Libro libro){
        if(gestorLibro.agregarLibro(libro)){
            return true;
        }return false;

    }
    public  Lector buscarCorreo(String correo){
        Lector lector=getGestorLectores().buscarLectorCorreo(correo);
        if(lector==null){
            return null;
        }
        return lector;
    }
    public void eliminarLibro(Libro libro){
        gestorLibro.eliminarLibro(libro);
    }

    public void agregarLector(Lector lector){
        if(!gestorUtentificar.existeUsuario(lector.correo)){
            gestorUtentificar.registrarUsuario(lector);
            gestorLectores.agregarLector(lector);
        }
    }

    public void eliminarLector(Lector lector){
        gestorUtentificar.eliminarUsuario(lector.getCorreo());
        gestorLectores.eliminarLector(lector);
    }

    public void agregarLector(Usuario usuario){
        if(!gestorUtentificar.existeUsuario(usuario.correo)){
            gestorUtentificar.registrarUsuario(usuario);
        }
    }
    public void agregarAdministrador(Administrador administrador){
        if(!gestorAdmin.contenido(administrador)){
            gestorAdmin.agregarAdmin(administrador);
            getGestorUtentificar().registrarUsuario(administrador);
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

    public GestorUsuarios getGestorLectores() {
        return gestorLectores;
    }

    public void setGestorLectores(GestorUsuarios gestorLectores) {
        this.gestorLectores = gestorLectores;
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

    public void cargarDatos(){
        Administrador administrador=new Administrador("juan","buitrago","1@gmail.com","123",Tipo.ADMIN);
        gestorAdmin.agregarAdmin(administrador);
        gestorUtentificar.registrarUsuario(administrador);

        Lector lector1 = new Lector("Lucía", "Ramírez", "2@gmail.com", "123", Tipo.LECTOR);
        Lector lector2 = new Lector("Carlos", "Gómez", "3@gmail.com", "123", Tipo.LECTOR);
        Lector lector3 = new Lector("Ana", "Pérez", "4@gmail.com", "123", Tipo.LECTOR);
        gestorUtentificar.registrarUsuario(lector1);
        gestorUtentificar.registrarUsuario(lector2);
        gestorUtentificar.registrarUsuario(lector3);

        gestorLectores.agregarLector(lector1);
        gestorLectores.agregarLector(lector2);
        gestorLectores.agregarLector(lector3);




        System.out.println("Datos cargados");
    }





}
