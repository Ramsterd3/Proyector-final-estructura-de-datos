package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import modelo.*;

public class VentanaLector extends JFrame {
    private JTabbedPane pestañas;
    private Lector usuarioActual;
    Biblioteca biblioteca;
    private DefaultTableModel modeloTablaLibros;
    private DefaultTableModel modeloTablaPrestamo;
    List<Libro> listaLibros = new ArrayList<>();
    List<Prestamo> lisbroPrestamo=new ArrayList<>();

    public VentanaLector(Lector usuario, Biblioteca biblioteca) {
        this.usuarioActual = usuario;
        this.biblioteca = biblioteca;


        setTitle("Biblioteca Digital - " + usuario.getNombre() + " " + usuario.getApellido());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pestañas = new JTabbedPane();
        pestañas.addTab("Inicio", crearPanelInicio());

        if (usuario instanceof Lector) {
            pestañas.addTab("Mensajes", crearPanelMensajeria());
            pestañas.addTab("Libros en Préstamo", crearPanelLibrosPrestamo()); // AGREGADO
        }


        add(pestañas);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Catálogo de Libros");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        String[] columnas = {"Título", "Autor", "Año", "Categoría", "Estado", "Calificación"};
        modeloTablaLibros = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTablaLibros);
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);

        listaLibros = biblioteca.obtenerTodosLibor();
        actualizarTablaLibros();

        JButton btnSolicitar = new JButton("Solicitar Préstamo");
        btnSolicitar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String tituloLibro = (String) modeloTablaLibros.getValueAt(fila, 0);
                Libro libroSeleccionado = listaLibros.stream()
                        .filter(l -> l.getTitulo().equals(tituloLibro))
                        .findFirst().orElse(null);

                if (libroSeleccionado != null && libroSeleccionado.getEstado().equalsIgnoreCase("disponible")) {
                    Lector lectorEncontrado = biblioteca.getGestorLectores().buscarLectorCorreo(usuarioActual.getCorreo());

                    boolean yaPrestado = lectorEncontrado.obtenerTodosPrestamos().stream()
                            .anyMatch(p -> p.getLibro().getTitulo().equals(libroSeleccionado.getTitulo()));

                    if (yaPrestado) {
                        JOptionPane.showMessageDialog(this, "Ya tienes este libro en préstamo.");
                        return;
                    }

                    Prestamo prestamo = new Prestamo(usuarioActual, libroSeleccionado);
                    lectorEncontrado.getHistorialPrestamos().agregar(prestamo);
                    biblioteca.getGestorLibro().buscarPorTitulo(libroSeleccionado.getTitulo()).setEstado("prestado");

                    actualizarTablaLibros();
                    actualizarTablaPrestamo();
                    JOptionPane.showMessageDialog(this, "Préstamo solicitado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "El libro no está disponible.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un libro para solicitar.");
            }

        });

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            this.dispose(); // Cierra la ventana actual
            SwingUtilities.invokeLater(() -> {
                VentanaLogin login = new VentanaLogin(biblioteca); // usa la misma instancia
                login.setVisible(true);
                login.toFront(); // Opcional: traer al frente
                login.requestFocus();
            });
        });



        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnSolicitar);
        panelBoton.add(btnCerrarSesion);  // Agrega el botón aquí
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    private void actualizarTablaLibros() {
        modeloTablaLibros.setRowCount(0);
        for (Libro libro : listaLibros) {
            modeloTablaLibros.addRow(new Object[]{
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAño(),
                    libro.getCategoria(),
                    libro.getEstado(),
                    libro.getCalificacionPromedio()
            });
        }
    }

    private JPanel crearPanelMensajeria() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
        JList<String> listaUsuarios = new JList<>(modeloUsuarios);
        listaUsuarios.setPreferredSize(new Dimension(200, 0));
        panel.add(new JScrollPane(listaUsuarios), BorderLayout.WEST);

        JTextArea historialMensajes = new JTextArea();
        historialMensajes.setEditable(false);
        panel.add(new JScrollPane(historialMensajes), BorderLayout.CENTER);

        JPanel panelEnvio = new JPanel(new BorderLayout());
        JTextArea areaMensaje = new JTextArea(3, 20);
        panelEnvio.add(new JScrollPane(areaMensaje), BorderLayout.CENTER);
        JButton btnEnviar = new JButton("Enviar");
        panelEnvio.add(btnEnviar, BorderLayout.EAST);

        panel.add(panelEnvio, BorderLayout.SOUTH);

        return panel;
    }

    // PANEL AGREGADO
    private JPanel crearPanelLibrosPrestamo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Libros en Préstamo");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        String[] columnas = {"Título", "Autor", "Año", "Categoría", "Estado", "Calificación"};
        modeloTablaPrestamo = new DefaultTableModel(columnas, 0);


        JTable tabla = new JTable(modeloTablaPrestamo);
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);
        actualizarTablaPrestamo();



        return panel;
    }

    private void actualizarTablaPrestamo() {
        modeloTablaPrestamo.setRowCount(0);
        lisbroPrestamo=biblioteca.getGestorLectores().buscarLectorCorreo(usuarioActual.getCorreo()).obtenerTodosPrestamos();
        for (Prestamo prestamo : lisbroPrestamo) {
            modeloTablaPrestamo.addRow(new Object[]{
                    prestamo.getLibro().getTitulo(),
                    prestamo.getLibro().getAutor(),
                    prestamo.getLibro().getAño(),
                    prestamo.getLibro().getCategoria(),
                    prestamo.getLibro().getEstado(),
                    prestamo.getLibro().getCalificacionPromedio()
            });
        }
    }


}
