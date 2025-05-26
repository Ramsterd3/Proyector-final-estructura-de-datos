package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaAdmin extends JFrame {

    public VentanaAdmin() {
        setTitle("Panel del Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();

        // Panel: Libros más prestados
        JPanel panelLibros = new JPanel(new BorderLayout());
        String[] columnasLibros = {"Título", "Cantidad de Préstamos"};
        Object[][] datosLibros = {
                {"Cien años de soledad", 120},
                {"El principito", 95},
                {"Don Quijote", 80}
        };
        JTable tablaLibros = new JTable(datosLibros, columnasLibros);
        panelLibros.add(new JScrollPane(tablaLibros), BorderLayout.CENTER);
        pestañas.addTab("Libros Populares", panelLibros);

        // Panel: Categorías más populares
        JPanel panelCategorias = new JPanel(new BorderLayout());
        String[] columnasCat = {"Categoría", "Préstamos"};
        Object[][] datosCat = {
                {"Literatura", 200},
                {"Ciencia", 150},
                {"Tecnología", 130}
        };
        JTable tablaCategorias = new JTable(datosCat, columnasCat);
        panelCategorias.add(new JScrollPane(tablaCategorias), BorderLayout.CENTER);
        pestañas.addTab("Categorías Populares", panelCategorias);

        // Panel: Usuarios activos
        JPanel panelUsuarios = new JPanel(new BorderLayout());
        String[] columnasUsuarios = {"Nombre", "Préstamos Realizados"};
        Object[][] datosUsuarios = {
                {"Juan Pérez", 35},
                {"María Gómez", 28},
                {"Luis Torres", 25}
        };
        JTable tablaUsuarios = new JTable(datosUsuarios, columnasUsuarios);
        panelUsuarios.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
        pestañas.addTab("Usuarios Activos", panelUsuarios);

        add(pestañas, BorderLayout.CENTER);
    }

    // Método main para probarla directamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaAdmin().setVisible(true);
        });
    }
}
