package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modelo.Lector;
import modelo.Tipo;
import servicios.ServicioAutenticacion;
import servicios.GestorUsuarios;

public class VentanaRegistro extends JFrame {
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoCorreo;
    private JPasswordField campoContraseña;
    private JPasswordField campoConfirmarContraseña;
    private JButton botonRegistrar;
    private JButton botonCancelar;
    private JComboBox<Tipo> comboBoxTipo;



    private ServicioAutenticacion servicioAutenticacion;
    private GestorUsuarios gestorUsuarios;

    public VentanaRegistro(ServicioAutenticacion servicioAutenticacion, GestorUsuarios gestorUsuarios) {
        this.servicioAutenticacion = servicioAutenticacion;
        this.gestorUsuarios = gestorUsuarios;

        // Configuración básica de la ventana
        setTitle("Biblioteca Digital - Registro de Usuario");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal con padding
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Agregar componentes al panel
        panel.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panel.add(campoNombre);

        panel.add(new JLabel("Apellido:"));
        campoApellido = new JTextField();
        panel.add(campoApellido);

        panel.add(new JLabel("Correo:"));
        campoCorreo = new JTextField();
        panel.add(campoCorreo);

        panel.add(new JLabel("Contraseña:"));
        campoContraseña = new JPasswordField();
        panel.add(campoContraseña);

        panel.add(new JLabel("Confirmar Contraseña:"));
        campoConfirmarContraseña = new JPasswordField();
        panel.add(campoConfirmarContraseña);

        panel.add(new JLabel("Selecione una opccion"));
        comboBoxTipo=new JComboBox<>();
        panel.add(comboBoxTipo);


        botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(botonCancelar);

        botonRegistrar = new JButton("Registrar");
        panel.add(botonRegistrar);
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });



        // Agregar panel al frame
        add(panel);
    }

    private void registrarUsuario() {
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        String correo = campoCorreo.getText();
        String contraseña = new String(campoContraseña.getPassword());
        String confirmarContraseña = new String(campoConfirmarContraseña.getPassword());

        // Validaciones básicas
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un correo válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si el correo ya está registrado
        if (servicioAutenticacion.existeUsuario(correo)) {
            JOptionPane.showMessageDialog(this, "El correo ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear nuevo lector
        Lector nuevoLector = new Lector(nombre, apellido, correo, contraseña,Tipo.LECTOR);

        // Registrar en el servicio de autenticación
        servicioAutenticacion.registrarUsuario(nuevoLector);

        // Agregar al gestor de usuarios
        gestorUsuarios.agregarLector(nuevoLector);

        JOptionPane.showMessageDialog(this, "Usuario registrado con éxito", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
