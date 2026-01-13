package com.plagio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    public Main() {
        // Configuración de la ventana principal
        setTitle("Sistema de Detección de Plagio");
        setSize(600, 400); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new BorderLayout()); // Layout para la ventana principal

        // Fondo color suave
        getContentPane().setBackground(new Color(240, 240, 240));

        // Crear el encabezado de la ventana de inicio
        JLabel encabezado = new JLabel("Bienvenido al Sistema de Detección de Plagio", SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 24)); // Cambiar la fuente y tamaño
        encabezado.setForeground(new Color(34, 45, 50)); // Color de texto oscuro
        encabezado.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Espaciado alrededor del encabezado

        // Crear un panel para el contenido principal
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(new Color(240, 240, 240));

        // Crear botón para abrir el detector
        JButton abrirDetectorButton = new JButton("Abrir Detector de Plagio");
        abrirDetectorButton.setFont(new Font("Arial", Font.PLAIN, 16));
        abrirDetectorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        abrirDetectorButton.setPreferredSize(new Dimension(300, 50));
        abrirDetectorButton.setBackground(new Color(58, 128, 255));
        abrirDetectorButton.setForeground(Color.WHITE);
        abrirDetectorButton.setFocusPainted(false);
        abrirDetectorButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        abrirDetectorButton.setContentAreaFilled(false);
        abrirDetectorButton.setOpaque(true);
        abrirDetectorButton.setBorder(BorderFactory.createRaisedBevelBorder());

        // Acción del botón para abrir el detector de plagio
        abrirDetectorButton.addActionListener(e -> abrirVentanaSecundaria());

        // Crear un panel para las operaciones con la base de datos
        JPanel panelBaseDeDatos = new JPanel();
        panelBaseDeDatos.setLayout(new FlowLayout());
        panelBaseDeDatos.setBackground(new Color(240, 240, 240));

        // Botones para las operaciones
        JButton btnInsertar = new JButton("Insertar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");

        // Configurar botones
        configurarBoton(btnInsertar);
        configurarBoton(btnActualizar);
        configurarBoton(btnEliminar);
        configurarBoton(btnMostrar);

        // Agregar botones al panel de base de datos
        panelBaseDeDatos.add(btnInsertar);
        panelBaseDeDatos.add(btnActualizar);
        panelBaseDeDatos.add(btnEliminar);
        panelBaseDeDatos.add(btnMostrar);

        // Añadir acciones a los botones
        btnInsertar.addActionListener(e -> insertarRegistro());
        btnActualizar.addActionListener(e -> actualizarRegistro());
        btnEliminar.addActionListener(e -> eliminarRegistro());
        btnMostrar.addActionListener(e -> mostrarRegistros());

        // Añadir elementos al panel contenido
        panelContenido.add(encabezado);
        panelContenido.add(Box.createVerticalStrut(20)); // Espaciado
        panelContenido.add(abrirDetectorButton);
        panelContenido.add(Box.createVerticalStrut(20)); // Espaciado
        panelContenido.add(panelBaseDeDatos);

        // Agregar el panel contenido al centro de la ventana
        add(panelContenido, BorderLayout.CENTER);
    }

    // Método para configurar botones
    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setBackground(new Color(58, 128, 255));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // Método para abrir la ventana del detector de plagio
    private void abrirVentanaSecundaria() {
        new DetectorPlagioFrame().setVisible(true);
    }

    // Métodos para las operaciones de la base de datos
    private void insertarRegistro() {
        PlagioDAO dao = new PlagioDAO();
        dao.insertarTextoConValidacion("Nuevo", "Usuario", "Texto insertado desde GUI.", false);
        JOptionPane.showMessageDialog(this, "Registro insertado correctamente.");
    }

    private void actualizarRegistro() {
        PlagioDAO dao = new PlagioDAO();
        dao.actualizarTexto(1, "Actualizado", "Apellido", "Texto actualizado desde GUI.", true);
        JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
    }

    private void eliminarRegistro() {
        PlagioDAO dao = new PlagioDAO();
        dao.eliminarTexto(2); // Cambia el ID según sea necesario
        JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
    }

    private void mostrarRegistros() {
        PlagioDAO dao = new PlagioDAO();
        dao.obtenerTextos(); // Esto imprimirá los registros en la consola
        JOptionPane.showMessageDialog(this, "Consulta realizada. Verifica la consola.");
    }

    // Método principal para ejecutar la ventana
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}