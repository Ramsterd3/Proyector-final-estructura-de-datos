package gui;

import javax.swing.*;
import java.awt.*;

import modelo.Tipo;
import modelo.Usuario;
import modelo.Administrador;
import modelo.Lector;
import servicios.GestorLibros;
import servicios.GestorUsuarios;

public class VentanaPrincipal extends JFrame {
    private JTabbedPane pestañas;
    private Usuario usuarioActual;
    private GestorLibros gestorLibros;
    private GestorUsuarios gestorUsuarios;

    public VentanaPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;

        // Configuración básica de la ventana
        setTitle("Biblioteca Digital - " + usuario.getNombre() + " " + usuario.getApellido());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar pestañas
        pestañas = new JTabbedPane();

        // Pestaña de inicio (búsqueda de libros)
        pestañas.addTab("Inicio", crearPanelInicio());

        // Agregar pestañas según el tipo de usuario
        if (usuario instanceof Lector) {
            // Pestañas específicas para lectores
            pestañas.addTab("Mi Perfil", crearPanelPerfilLector());
            pestañas.addTab("Mensajes", crearPanelMensajeria());
            pestañas.addTab("Recomendaciones", crearPanelRecomendaciones());
        } else if (usuario instanceof Administrador) {
            // Pestañas específicas para administradores
            pestañas.addTab("Administración", crearPanelAdministracion());
            pestañas.addTab("Estadísticas", crearPanelEstadisticas());
            pestañas.addTab("Grafo de Afinidad", crearPanelGrafo());
        }

        // Agregar pestañas al frame
        add(pestañas);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new GridLayout(1, 4, 5, 0));
        panelBusqueda.add(new JLabel("Buscar:"));
        JTextField campoBusqueda = new JTextField();
        panelBusqueda.add(campoBusqueda);

        String[] opciones = {"Título", "Autor", "Categoría"};
        JComboBox<String> comboCriterio = new JComboBox<>(opciones);
        panelBusqueda.add(comboCriterio);

        JButton botonBuscar = new JButton("Buscar");
        panelBusqueda.add(botonBuscar);

        panel.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla de resultados
        String[] columnas = {"Título", "Autor", "Año", "Categoría", "Estado", "Calificación"};
        Object[][] datos = {};
        JTable tablaLibros = new JTable(datos, columnas);
        JScrollPane scrollTabla = new JScrollPane(tablaLibros);
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Panel de acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonSolicitar = new JButton("Solicitar Préstamo");
        JButton botonValorar = new JButton("Valorar Libro");
        panelAcciones.add(botonSolicitar);
        panelAcciones.add(botonValorar);
        panel.add(panelAcciones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelPerfilLector() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Información del lector
        JPanel panelInfo = new JPanel(new GridLayout(3, 2, 5, 5));
        panelInfo.add(new JLabel("Nombre:"));
        panelInfo.add(new JLabel(usuarioActual.getNombre() + " " + usuarioActual.getApellido()));
        panelInfo.add(new JLabel("Correo:"));
        panelInfo.add(new JLabel(usuarioActual.getCorreo()));
        panel.add(panelInfo, BorderLayout.NORTH);

        // Pestañas internas
        JTabbedPane pestañasInternas = new JTabbedPane();

        // Pestaña de préstamos actuales
        JPanel panelPrestamos = new JPanel(new BorderLayout());
        String[] columnasPrestamos = {"Título", "Fecha Préstamo", "Fecha Devolución", "Estado"};
        Object[][] datosPrestamos = {};
        JTable tablaPrestamos = new JTable(datosPrestamos, columnasPrestamos);
        JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamos);
        panelPrestamos.add(scrollPrestamos, BorderLayout.CENTER);

        JPanel panelAccionesPrestamos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonDevolver = new JButton("Devolver Libro");
        panelAccionesPrestamos.add(botonDevolver);
        panelPrestamos.add(panelAccionesPrestamos, BorderLayout.SOUTH);

        pestañasInternas.addTab("Préstamos Actuales", panelPrestamos);

        // Pestaña de historial de préstamos
        JPanel panelHistorial = new JPanel(new BorderLayout());
        String[] columnasHistorial = {"Título", "Fecha Préstamo", "Fecha Devolución"};
        Object[][] datosHistorial = {};
        JTable tablaHistorial = new JTable(datosHistorial, columnasHistorial);
        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        panelHistorial.add(scrollHistorial, BorderLayout.CENTER);
        pestañasInternas.addTab("Historial de Préstamos", panelHistorial);

        // Pestaña de valoraciones
        JPanel panelValoraciones = new JPanel(new BorderLayout());
        String[] columnasValoraciones = {"Título", "Calificación", "Comentario", "Fecha"};
        Object[][] datosValoraciones = {};
        JTable tablaValoraciones = new JTable(datosValoraciones, columnasValoraciones);
        JScrollPane scrollValoraciones = new JScrollPane(tablaValoraciones);
        panelValoraciones.add(scrollValoraciones, BorderLayout.CENTER);
        pestañasInternas.addTab("Mis Valoraciones", panelValoraciones);

        panel.add(pestañasInternas, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelMensajeria() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lista de contactos
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaContactos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaContactos);
        scrollLista.setPreferredSize(new Dimension(200, 0));
        panel.add(scrollLista, BorderLayout.WEST);

        // Panel de mensajes
        JPanel panelMensajes = new JPanel(new BorderLayout());

        // Historial de mensajes
        JTextArea areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        panelMensajes.add(scrollHistorial, BorderLayout.CENTER);

        // Panel para enviar mensajes
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

        // Pestañas internas
        JTabbedPane pestañasInternas = new JTabbedPane();

        // Pestaña de libros recomendados
        JPanel panelLibros = new JPanel(new BorderLayout());
        String[] columnasLibros = {"Título", "Autor", "Categoría", "Calificación"};
        Object[][] datosLibros = {};
        JTable tablaLibros = new JTable(datosLibros, columnasLibros);
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        panelLibros.add(scrollLibros, BorderLayout.CENTER);
        pestañasInternas.addTab("Libros Recomendados", panelLibros);

        // Pestaña de lectores sugeridos
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

        // Pestañas internas
        JTabbedPane pestañasInternas = new JTabbedPane();

        // Pestaña de gestión de libros
        JPanel panelLibros = new JPanel(new BorderLayout());

        // Tabla de libros
        String[] columnasLibros = {"Título", "Autor", "Año", "Categoría", "Estado", "Calificación"};
        Object[][] datosLibros = {};
        JTable tablaLibros = new JTable(datosLibros, columnasLibros);
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        panelLibros.add(scrollLibros, BorderLayout.CENTER);

        // Panel de acciones para libros
        JPanel panelAccionesLibros = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonAgregarLibro = new JButton("Agregar Libro");
        JButton botonEditarLibro = new JButton("Editar Libro");
        JButton botonEliminarLibro = new JButton("Eliminar Libro");
        panelAccionesLibros.add(botonAgregarLibro);
        panelAccionesLibros.add(botonEditarLibro);
        panelAccionesLibros.add(botonEliminarLibro);
        panelLibros.add(panelAccionesLibros, BorderLayout.SOUTH);

        pestañasInternas.addTab("Gestión de Libros", panelLibros);

        // Pestaña de gestión de usuarios
        JPanel panelUsuarios = new JPanel(new BorderLayout());

        // Tabla de usuarios
        String[] columnasUsuarios = {"Nombre", "Apellido", "Correo", "Tipo"};
        Object[][] datosUsuarios = {};
        JTable tablaUsuarios = new JTable(datosUsuarios, columnasUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);

        // Panel de acciones para usuarios
        JPanel panelAccionesUsuarios = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonAgregarUsuario = new JButton("Agregar Usuario");
        JButton botonEditarUsuario = new JButton("Editar Usuario");
        JButton botonEliminarUsuario = new JButton("Eliminar Usuario");
        panelAccionesUsuarios.add(botonAgregarUsuario);
        panelAccionesUsuarios.add(botonEditarUsuario);
        panelAccionesUsuarios.add(botonEliminarUsuario);
        panelUsuarios.add(panelAccionesUsuarios, BorderLayout.SOUTH);

        pestañasInternas.addTab("Gestión de Usuarios", panelUsuarios);

        // Pestaña de préstamos
        JPanel panelPrestamos = new JPanel(new BorderLayout());

        // Tabla de préstamos
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

        // Pestañas internas
        JTabbedPane pestañasInternas = new JTabbedPane();

        // Pestaña de estadísticas de préstamos
        JPanel panelPrestamos = new JPanel(new BorderLayout());
        // Aquí iría un componente gráfico para mostrar estadísticas
        JLabel lblPrestamos = new JLabel("Gráfico de estadísticas de préstamos", JLabel.CENTER);
        panelPrestamos.add(lblPrestamos, BorderLayout.CENTER);
        pestañasInternas.addTab("Préstamos", panelPrestamos);

        // Pestaña de estadísticas de valoraciones
        JPanel panelValoraciones = new JPanel(new BorderLayout());
        // Aquí iría un componente gráfico para mostrar estadísticas
        JLabel lblValoraciones = new JLabel("Gráfico de estadísticas de valoraciones", JLabel.CENTER);
        panelValoraciones.add(lblValoraciones, BorderLayout.CENTER);
        pestañasInternas.addTab("Valoraciones", panelValoraciones);

        // Pestaña de estadísticas de usuarios
        JPanel panelUsuarios = new JPanel(new BorderLayout());
        // Aquí iría un componente gráfico para mostrar estadísticas
        JLabel lblUsuarios = new JLabel("Gráfico de estadísticas de usuarios", JLabel.CENTER);
        panelUsuarios.add(lblUsuarios, BorderLayout.CENTER);
        pestañasInternas.addTab("Usuarios", panelUsuarios);

        panel.add(pestañasInternas, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelGrafo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Aquí iría un componente para visualizar el grafo
        JPanel panelVisualizacion = new JPanel();
        panelVisualizacion.setBackground(Color.WHITE);
        panel.add(panelVisualizacion, BorderLayout.CENTER);

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botonActualizar = new JButton("Actualizar Grafo");
        JButton botonExportar = new JButton("Exportar Imagen");
        JComboBox<String> comboFiltro = new JComboBox<>(new String[]{"Todos los lectores", "Lectores activos", "Clústeres"});
        panelControles.add(botonActualizar);
        panelControles.add(botonExportar);
        panelControles.add(new JLabel("Filtro:"));
        panelControles.add(comboFiltro);
        panel.add(panelControles, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        Usuario usuario = new Lector("Juan", "Pérez", "juan.perez@email.com", "12345",Tipo.LECTOR);
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal(usuario).setVisible(true);
        });
    }

}
