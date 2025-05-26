package gui;

import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaAdmin extends JFrame {

    private DefaultTableModel modeloTablaLibros;
    Biblioteca biblioteca;
    List<Libro> listaLibros = new ArrayList<>();

    public VentanaAdmin(Usuario usuario, Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Panel del Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();

        JPanel panelLibros = new JPanel(new BorderLayout());

        JLabel tituloTabla = new JLabel("Libros");
        tituloTabla.setFont(new Font("Arial", Font.BOLD, 16));
        tituloTabla.setHorizontalAlignment(SwingConstants.CENTER);
        panelLibros.add(tituloTabla, BorderLayout.NORTH);

        String[] columnasLibros = {"Título", "Autor", "Año", "Categoría", "Estado"};
        Object[][] datosLibros = {};

        modeloTablaLibros = new DefaultTableModel(datosLibros, columnasLibros) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaLibros = new JTable(modeloTablaLibros);
        listaLibros = biblioteca.obtenerTodosLibor(); // Este es el método nuevo que creamos
        actualizarTablaLibros();

        int filasVisibles = 6;
        int alturaFila = tablaLibros.getRowHeight();
        int alturaEncabezado = tablaLibros.getTableHeader().getPreferredSize().height;
        int alturaTotal = alturaFila * filasVisibles + alturaEncabezado;

        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        scrollPane.setPreferredSize(new Dimension(550, alturaTotal));
        panelLibros.add(scrollPane, BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JTextField txtTitulo = new JTextField(10);
        JTextField txtAutor = new JTextField(10);
        JTextField txtAno = new JTextField(10);
        JTextField txtCategoria = new JTextField(10);

        JButton botonAgregar = new JButton("Agregar");
        JButton botonEliminar = new JButton("Eliminar");

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Título:"), gbc);
        gbc.gridy = 1;
        panelFormulario.add(txtTitulo, gbc);

        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Autor:"), gbc);
        gbc.gridy = 3;
        panelFormulario.add(txtAutor, gbc);

        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Año:"), gbc);
        gbc.gridy = 5;
        panelFormulario.add(txtAno, gbc);

        gbc.gridy = 6;
        panelFormulario.add(new JLabel("Categoría:"), gbc);
        gbc.gridy = 7;
        panelFormulario.add(txtCategoria, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.add(botonAgregar);
        panelBotones.add(botonEliminar);

        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(panelBotones, gbc);

        panelLibros.add(panelFormulario, BorderLayout.SOUTH);

        pestañas.addTab("Gestionar", panelLibros);

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

        // Botón Cerrar Sesión agregado aquí:
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        JPanel panelCerrarSesion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelCerrarSesion.add(btnCerrarSesion);
        add(panelCerrarSesion, BorderLayout.SOUTH);

        btnCerrarSesion.addActionListener(e -> {
            this.dispose(); // Cierra esta ventana
            // Si tienes ventana login, la puedes abrir aquí:
            // new VentanaLogin().setVisible(true);
        });

        botonAgregar.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText().trim();
                String autor = txtAutor.getText().trim();
                int año = Integer.parseInt(txtAno.getText().trim());
                String categoria = txtCategoria.getText().trim();

                if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Libro libro = new Libro(titulo, autor, año, categoria);
                if (biblioteca.agregarLibro(libro)) {
                    listaLibros.add(libro);
                    actualizarTablaLibros();
                    txtTitulo.setText("");
                    txtAutor.setText("");
                    txtAno.setText("");
                    txtCategoria.setText("");
                    System.out.println(biblioteca.getGestorLibro().getCatalogoLibros().getTamanio());
                } else {
                    JOptionPane.showMessageDialog(this, "El libro ya existe en el catálogo", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El año debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaLibros.getSelectedRow();
            if (filaSeleccionada >= 0) {
                modeloTablaLibros.removeRow(filaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar", "Atención", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void actualizarTablaLibros() {
        modeloTablaLibros.setRowCount(0); // Limpiar tabla

        for (Libro libro : listaLibros) {
            Object[] fila = {
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAño(),
                    libro.getCategoria(),
                    "Disponible" // Puedes cambiar esto si tienes lógica para el estado
            };
            modeloTablaLibros.addRow(fila);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //new VentanaAdmin(null, null).setVisible(true);
        });
    }
}
