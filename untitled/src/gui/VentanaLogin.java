package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modelo.Usuario;
import modelo.Administrador;
import modelo.Lector;
import servicios.ServicioAutenticacion;
import servicios.GestorUsuarios;

public class VentanaLogin extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContraseña;
    private JButton botonLogin;
    private JButton botonRegistro;
    private ServicioAutenticacion servicioAutenticacion;
    private GestorUsuarios gestorUsuarios;

    public VentanaLogin(ServicioAutenticacion servicioAutenticacion, GestorUsuarios gestorUsuarios) {
        this.servicioAutenticacion = servicioAutenticacion;
        this.gestorUsuarios = gestorUsuarios;

        // Configuración básica de la ventana
        setTitle("Biblioteca Digital - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal con padding
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Agregar componentes al panel
        JLabel labelCorreo = new JLabel("Correo:");
        campoCorreo = new JTextField();
        panel.add(labelCorreo);
        panel.add(campoCorreo);

        JLabel labelContraseña = new JLabel("Contraseña:");
        campoContraseña = new JPasswordField();
        panel.add(labelContraseña);
        panel.add(campoContraseña);

        botonLogin = new JButton("Iniciar Sesión");
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        panel.add(botonLogin);

        botonRegistro = new JButton("Registrarse");
        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaRegistro();
            }
        });
        panel.add(botonRegistro);

        // Agregar panel al frame
        add(panel);
    }

    private void iniciarSesion() {
        String correo = campoCorreo.getText();
        String contraseña = new String(campoContraseña.getPassword());

        if (correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = servicioAutenticacion.autenticar(correo, contraseña);

        if (usuario != null) {
            VentanaPrincipal ventana = new VentanaPrincipal(usuario);
            ventana.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirVentanaRegistro() {
        VentanaRegistro ventanaRegistro = new VentanaRegistro(servicioAutenticacion, gestorUsuarios);
        ventanaRegistro.setVisible(true);
    }
}
