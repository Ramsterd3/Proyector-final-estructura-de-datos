package servicios;

import modelo.Usuario;
import java.util.HashMap;
import java.util.Map;

public class ServicioAutenticacion {
    private Map<String, Usuario> usuariosPorCorreo;

    public ServicioAutenticacion() {
        this.usuariosPorCorreo = new HashMap<>();
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuariosPorCorreo.containsKey(usuario.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        usuariosPorCorreo.put(usuario.getCorreo(), usuario);
    }

    public Usuario autenticar(String correo, String contraseña) {
        Usuario usuario = usuariosPorCorreo.get(correo);

        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario;
        }

        return null;
    }

    public boolean existeUsuario(String correo) {
        return usuariosPorCorreo.containsKey(correo);
    }

    public void eliminarUsuario(String correo) {
        usuariosPorCorreo.remove(correo);
    }

    public Map<String, Usuario> obtenerTodosLosUsuarios() {
        return new HashMap<>(usuariosPorCorreo);
    }
}
