package servicios;

import modelo.Administrador;
import modelo.ArbolBinarioBusqueda;
import modelo.GrafoNoDirigido;

import java.util.List;

public class GestorAdmin {
    private ArbolBinarioBusqueda<Administrador> admis;
    private GrafoNoDirigido<Administrador> grafoAfinidad;

    public GestorAdmin() {
        this.admis = new ArbolBinarioBusqueda<>();
        this.grafoAfinidad = new GrafoNoDirigido<>();
    }
    public void agregarAdmin(Administrador administrador){
        admis.insertar(administrador);
        grafoAfinidad.agregarVertice(administrador);
    }

    public void eliminarAdmin(Administrador administrador){
        admis.eliminar(administrador);
        grafoAfinidad.agregarVertice(administrador);
    }
    public List<Administrador> obtenerTodos(){
        return admis.obtenerTodos();


    }

    public boolean contenido(Administrador administrador){
        if(admis.contiene(administrador)){
            return true;
        }
        return false;
    }


}


