package modelo;

public class Administrador extends Usuario implements Comparable<Administrador>{


    public Administrador(String nombre, String apellido, String correo, String contraseña ,Tipo tipo) {
        super(nombre, apellido, correo, contraseña ,tipo);
    }


    @Override
    public int compareTo(Administrador o) {
        return 0;
    }
}