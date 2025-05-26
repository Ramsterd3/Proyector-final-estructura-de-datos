package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import modelo.*;

public class VentanaLector extends JFrame {
    private JTabbedPane pestañas;
    private Usuario usuarioActual;
    Biblioteca biblioteca;
    private DefaultTableModel modeloTablaLibros;
    List<Libro> listaLibros = new ArrayList<>();

    public VentanaLector(Usuario usuario, Biblioteca biblioteca) {
        this.usuarioActual = usuario;
        this.biblioteca = biblioteca;

        // Configuración básica de la ventana
        setTitle("Biblioteca Digital - " + usuario.getNombre() + " " + usuario.getApellido());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar pestañas
        pestañas = new JTabbedPane();

        // Pestaña de inicio (mostrar libros)
        pestañas.addTab("Inicio", crearPanelInicio());

        if (usuario instanceof Lector) {

            pestañas.addTab("Mensajes", crearPanelMensajeria());
            pestañas.addTab("Recomendaciones", crearPanelRecomendaciones());
        } else if (usuario instanceof Administrador) {
            pestañas.addTab("Administración", crearPanelAdministracion());
            pestañas.addTab("Estadísticas", crearPanelEstadisticas());
            pestañas.addTab("Grafo de Afinidad", crearPanelGrafo());
        }

        add(pestañas);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // espacio entre componentes
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título arriba
        JLabel titulo = new JLabel("Libros Disponibles");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Columnas de la tabla
        String[] columnasLibros = {"Título", "Autor", "Año", "Categoría", "Estado", "Calificación"};
        modeloTablaLibros = new DefaultTableModel(columnasLibros, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaLibros = new JTable(modeloTablaLibros);

        // Solo fuente, sin cambio de colores
        tablaLibros.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaLibros.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaLibros.setRowHeight(25);

        // Ajustar ancho de columnas (opcional, puedes cambiar valores)
        tablaLibros.getColumnModel().getColumn(0).setPreferredWidth(200); // Título más ancho
        tablaLibros.getColumnModel().getColumn(1).setPreferredWidth(130);
        tablaLibros.getColumnModel().getColumn(2).setPreferredWidth(50);
        tablaLibros.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaLibros.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaLibros.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        panel.add(scrollLibros, BorderLayout.CENTER);

        // Cargar libros y actualizar tabla
        listaLibros = biblioteca.obtenerTodosLibor(); // Asegúrate que este método existe y está bien
        actualizarTablaLibros();

        // Botones acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); // espacio entre botones
        JButton botonSolicitar = new JButton("Solicitar Préstamo");
        JButton botonValorar = new JButton("Valorar Libro");
        JButton botonCerrarSesion = new JButton("Cerrar Sesión");

        Font fontBotones = new Font("Arial", Font.BOLD, 12);
        botonSolicitar.setFont(fontBotones);
        botonValorar.setFont(fontBotones);
        botonCerrarSesion.setFont(fontBotones);

        botonCerrarSesion.addActionListener(e -> {
            this.dispose();
            // new VentanaLogin().setVisible(true); // si tienes ventana login
        });

        panelAcciones.add(botonSolicitar);
        panelAcciones.add(botonValorar);
        panelAcciones.add(botonCerrarSesion);

        panel.add(panelAcciones, BorderLayout.SOUTH);

        return panel;
    }



    private void actualizarTablaLibros() {
        modeloTablaLibros.setRowCount(0); // Limpiar tabla

        for (Libro libro : listaLibros) {
            modeloTablaLibros.addRow(new Object[]{
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAño(),
                    libro.getCategoria(),
                    "Disponible", // Cambia esto si manejas otro estado
                    libro.getValoraciones()
            });
        }
    }

    private JPanel crearPanelMensajeria() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaContactos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaContactos);
        scrollLista.setPreferredSize(new Dimension(200, 0));
        panel.add(scrollLista, BorderLayout.WEST);

        JPanel panelMensajes = new JPanel(new BorderLayout());
        JTextArea areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        panelMensajes.add(scrollHistorial, BorderLayout.CENTER);

        JPanel panelEnvio = new JPanel(new BorderLayout());
        JTextArea areaMensaje = new JTextArea(3, 20);
        JScrollPane scrollMensaje = new JScrollPane(areaMensaje);
        panelEnvio.add(scrollMensaje, BorderLayout.CENTER);
        JButton botonEnviar = new JButton("Enviar");
        panelEnvio.add(botonEnviar, BorderLayout.EAST);
        panelMensajes.add(panelEnvio, BorderLayout.SOUTH);

        panel.add(panelMensajes, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelRecomendaciones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTabbedPane pestañasInternas = new JTabbedPane();

        JPanel panelLibros = new JPanel(new BorderLayout());
        String[] columnasLibros = {"Título", "Autor", "Categoría", "Calificación"};
        Object[][] datosLibros = {};
        JTable tablaLibros = new JTable(datosLibros, columnasLibros);
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        panelLibros.add(scrollLibros, BorderLayout.CENTER);
        pestañasInternas.addTab("Libros Recomendados", panelLibros);

        JPanel panelLectores = new JPanel(new BorderLayout());
        String[] columnasLectores = {"Nombre", "Similitud", "Libros en Común"};
        Object[][] datosLectores = {};
        JTable tablaLectores = new JTable(datosLectores, columnasLectores);
        JScrollPane scrollLectores = new JScrollPane(tablaLectores);
        panelLectores.add(scrollLectores, BorderLayout.CENTER);

        JPanel panelAccionesLectores = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonEnviarMensaje = new JButton("Enviar Mensaje");
        panelAccionesLectores.add(botonEnviarMensaje);
        panelLectores.add(panelAccionesLectores, BorderLayout.SOUTH);
        pestañasInternas.addTab("Lectores Sugeridos", panelLectores);

        panel.add(pestañasInternas, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelAdministracion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTabbedPane pestañasInternas = new JTabbedPane();

        JPanel panelLibros = new JPanel(new BorderLayout());
        String[] columnasLibros = {"Título", "Autor", "Año", "Categoría", "Estado", "Calificación"};
        Object[][] datosLibros = {};
        JTable tablaLibros = new JTable(datosLibros, columnasLibros);
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        panelLibros.add(scrollLibros, BorderLayout.CENTER);

        JPanel panelAccionesLibros = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAccionesLibros.add(new JButton("Agregar Libro"));
        panelAccionesLibros.add(new JButton("Editar Libro"));
        panelAccionesLibros.add(new JButton("Eliminar Libro"));
        panelLibros.add(panelAccionesLibros, BorderLayout.SOUTH);
        pestañasInternas.addTab("Gestión de Libros", panelLibros);

        JPanel panelUsuarios = new JPanel(new BorderLayout());
        String[] columnasUsuarios = {"Nombre", "Apellido", "Correo", "Tipo"};
        Object[][] datosUsuarios = {};
        JTable tablaUsuarios = new JTable(datosUsuarios, columnasUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);

        JPanel panelAccionesUsuarios = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAccionesUsuarios.add(new JButton("Agregar Usuario"));
        panelAccionesUsuarios.add(new JButton("Editar Usuario"));
        panelAccionesUsuarios.add(new JButton("Eliminar Usuario"));
        panelUsuarios.add(panelAccionesUsuarios, BorderLayout.SOUTH);
        pestañasInternas.addTab("Gestión de Usuarios", panelUsuarios);

        JPanel panelPrestamos = new JPanel(new BorderLayout());
        String[] columnasPrestamos = {"Lector", "Libro", "Fecha Préstamo", "Fecha Devolución", "Estado"};
        Object[][] datosPrestamos = {};
        JTable tablaPrestamos = new JTable(datosPrestamos, columnasPrestamos);
        JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamos);
        panelPrestamos.add(scrollPrestamos, BorderLayout.CENTER);
        pestañasInternas.addTab("Gestión de Préstamos", panelPrestamos);

        panel.add(pestañasInternas, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTabbedPane pestañasInternas = new JTabbedPane();

        JPanel panelPrestamos = new JPanel(new BorderLayout());
        panelPrestamos.add(new JLabel("Gráfico de estadísticas de préstamos", JLabel.CENTER), BorderLayout.CENTER);
        pestañasInternas.addTab("Préstamos", panelPrestamos);

        JPanel panelValoraciones = new JPanel(new BorderLayout());
        panelValoraciones.add(new JLabel("Gráfico de estadísticas de valoraciones", JLabel.CENTER), BorderLayout.CENTER);
        pestañasInternas.addTab("Valoraciones", panelValoraciones);

        JPanel panelUsuarios = new JPanel(new BorderLayout());
        panelUsuarios.add(new JLabel("Gráfico de estadísticas de usuarios", JLabel.CENTER), BorderLayout.CENTER);
        pestañasInternas.addTab("Usuarios", panelUsuarios);

        panel.add(pestañasInternas, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelGrafo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelVisualizacion = new JPanel();
        panelVisualizacion.setBackground(Color.WHITE);
        panel.add(panelVisualizacion, BorderLayout.CENTER);

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelControles.add(new JButton("Actualizar Grafo"));
        panelControles.add(new JButton("Exportar Imagen"));
        panelControles.add(new JLabel("Filtro:"));
        panelControles.add(new JComboBox<>(new String[]{"Todos los lectores", "Lectores activos", "Clústeres"}));
        panel.add(panelControles, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        Usuario usuario = new Lector("Juan", "Pérez", "juan.perez@email.com", "12345", Tipo.LECTOR);
        Biblioteca biblioteca = new Biblioteca(); // Asegúrate que exista esta clase con obtenerTodosLibor()
        SwingUtilities.invokeLater(() -> new VentanaLector(usuario, biblioteca).setVisible(true));
    }
}
