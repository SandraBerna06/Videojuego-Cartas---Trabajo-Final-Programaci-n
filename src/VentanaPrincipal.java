package tarea_final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;

public class VentanaPrincipal extends JFrame {
    
    private JPanel panelContenido;
    // Declaración de TODOS los botones
    private JButton btnTodasCartas, btnCartasTipo, btnCartasRareza, btnHabilidades, btnJugadores;
    private JButton btnRegistrar, btnAnadirMonedas, btnBaja, btnSimular, btnSalir;

    public VentanaPrincipal(Connection conexion) {
        setTitle("Neo-Veridia Card Game - Panel de Administración");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        Escuchador escuchador = new Escuchador(this, conexion);

        // ================= PANEL LATERAL (MENÚ) =================
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(31, 43, 55)); 
        // Cambiamos el GridLayout a 11 filas para que quepan todos los botones
        panelMenu.setLayout(new GridLayout(12, 1, 5, 8));
        panelMenu.setPreferredSize(new Dimension(280, 0));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel tituloMenu = new JLabel("MENÚ PRINCIPAL", SwingConstants.CENTER);
        tituloMenu.setForeground(Color.WHITE);
        tituloMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panelMenu.add(tituloMenu);

        // Instanciamos todos los botones
        btnTodasCartas = crearBotonMenu("1. Mostrar todas las cartas");
        btnCartasTipo = crearBotonMenu("2. Mostrar cartas por tipo");
        btnCartasRareza = crearBotonMenu("3. Mostrar cartas por rareza");
        btnHabilidades = crearBotonMenu("4. Mostrar habilidades");
        btnJugadores = crearBotonMenu("5. Mostrar jugadores");
        btnRegistrar = crearBotonMenu("6. Registrarse en la app");
        btnAnadirMonedas = crearBotonMenu("7. Añadir monedas");
        btnBaja = crearBotonMenu("8. Darse de baja");
        btnSimular = crearBotonMenu("9. Simular partida");
        btnSalir = crearBotonMenu("10. Finalizar aplicación");

        // Vinculamos el escuchador a todos
        JButton[] botones = {btnTodasCartas, btnCartasTipo, btnCartasRareza, btnHabilidades, 
                             btnJugadores, btnRegistrar, btnAnadirMonedas, btnBaja, btnSimular, btnSalir};
        
        for (JButton btn : botones) {
            btn.addActionListener(escuchador);
            panelMenu.add(btn);
        }
        
     // ================= PANEL CENTRAL =================
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(new Color(245, 247, 250));
        
        // Intentar cargar la imagen de portada desde el paquete (¡Cuidado con el espacio en el nombre!)
        java.net.URL imgURL = VentanaPrincipal.class.getResource("Imagen-portada.png");
        if (imgURL != null) {
            // Opcional: Escalar la imagen si es muy grande para que quepa bien en la ventana
            ImageIcon iconoOriginal = new ImageIcon(imgURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            JLabel lblPortada = new JLabel(new ImageIcon(imagenEscalada), SwingConstants.CENTER);
            panelContenido.add(lblPortada, BorderLayout.CENTER);
        } else {
            // Texto de respaldo si el archivo de imagen no se encuentra en la ruta
            JLabel lblBienvenida = new JLabel("<html><center><h1>Bienvenido a Neo-Veridia</h1><p>Selecciona una operación del menú lateral.</p></center></html>", SwingConstants.CENTER);
            lblBienvenida.setForeground(new Color(120, 130, 140));
            panelContenido.add(lblBienvenida, BorderLayout.CENTER);
        }

        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 10));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public void actualizarPantallaConTabla(String tituloSeccion, DefaultTableModel modelo) {
        panelContenido.removeAll();
        JLabel titulo = new JLabel(tituloSeccion, SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelContenido.add(titulo, BorderLayout.NORTH);

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setBackground(new Color(41, 128, 185));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        panelContenido.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    public void cambiarPanelContenido(JPanel nuevoPanel) {
        panelContenido.removeAll();
        panelContenido.add(nuevoPanel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // GETTERS
    public JButton getBtnTodasCartas() { return btnTodasCartas; }
    public JButton getBtnCartasTipo() { return btnCartasTipo; }
    public JButton getBtnCartasRareza() { return btnCartasRareza; }
    public JButton getBtnHabilidades() { return btnHabilidades; }
    public JButton getBtnJugadores() { return btnJugadores; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnAnadirMonedas() { return btnAnadirMonedas; }
    public JButton getBtnBaja() { return btnBaja; }
    public JButton getBtnSimular() { return btnSimular; }
    public JButton getBtnSalir() { return btnSalir; }
}