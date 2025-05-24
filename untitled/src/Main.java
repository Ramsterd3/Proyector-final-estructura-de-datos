import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.VentanaLogin;
import servicios.GestorLibros;
import servicios.GestorUsuarios;
import servicios.ServicioAutenticacion;
import utilidades.CargadorDatosIniciales;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer look and feel del sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializar servicios
        ServicioAutenticacion servicioAutenticacion = new ServicioAutenticacion();
        GestorLibros gestorLibros = new GestorLibros();
        GestorUsuarios gestorUsuarios = new GestorUsuarios();

        // Cargar datos iniciales de prueba
       // CargadorDatosIniciales.cargarDatos(servicioAutenticacion, gestorLibros, gestorUsuarios);//Pilas revisar

        // Mostrar ventana de login
        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventanaLogin = new VentanaLogin(servicioAutenticacion, gestorUsuarios);
            ventanaLogin.setVisible(true);
        });
    }
}
