package modelo;

public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String correo, String contraseña) {
        super(nombre, apellido, correo, contraseña);
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }
}
