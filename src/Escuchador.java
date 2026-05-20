package tarea_final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Escuchador implements ActionListener {

    private VentanaPrincipal ventana;
    private Connection conexion;

    public Escuchador(VentanaPrincipal ventana, Connection conexion) {
        this.ventana = ventana;
        this.conexion = conexion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        // 1. TODAS LAS CARTAS
        if (origen == ventana.getBtnTodasCartas()) {
            ventana.actualizarPantallaConTabla("Catálogo General Completo", Consultas.obtenerModeloTodasLasCartas(conexion));

        // 2. CARTAS POR TIPO
        } else if (origen == ventana.getBtnCartasTipo()) {
            String[] opciones = {"Personajes", "Hechizos"};
            int seleccion = JOptionPane.showOptionDialog(ventana, "¿Qué tipo de carta deseas ver?", "Filtrar por Tipo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            
            if (seleccion == 0) {
                ventana.actualizarPantallaConTabla("Cartas de Personajes", Consultas.obtenerModeloCartasPersonaje(conexion));
            } else if (seleccion == 1) {
                ventana.actualizarPantallaConTabla("Cartas de Hechizos", Consultas.obtenerModeloCartasHechizo(conexion));
            }

        // 3. CARTAS POR RAREZA
        } else if (origen == ventana.getBtnCartasRareza()) {
            String[] rarezas = {"Común", "Rara", "Épica", "Legendaria"};
            String seleccion = (String) JOptionPane.showInputDialog(ventana, "Selecciona la rareza:", "Filtrar por Rareza",
                    JOptionPane.QUESTION_MESSAGE, null, rarezas, rarezas[0]);
            
            if (seleccion != null) {
                ventana.actualizarPantallaConTabla("Cartas de Rareza: " + seleccion, Consultas.obtenerModeloRareza(conexion, seleccion));
            }

        // 4. HABILIDADES
        } else if (origen == ventana.getBtnHabilidades()) {
            ventana.actualizarPantallaConTabla("Habilidades de Personajes", Consultas.obtenerModeloHabilidades(conexion));

        // 5. JUGADORES
        } else if (origen == ventana.getBtnJugadores()) {
            ventana.actualizarPantallaConTabla("Jugadores Registrados", Consultas.obtenerModeloJugadores(conexion));

        // 6. REGISTRAR USUARIO
        } else if (origen == ventana.getBtnRegistrar()) {
            String nombre = JOptionPane.showInputDialog(ventana, "Introduce el nuevo nombre de usuario:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                boolean exito = Consultas.registrarUsuario(conexion, nombre.trim());
                if(exito) JOptionPane.showMessageDialog(ventana, "Usuario registrado con éxito.", "Registro OK", JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(ventana, "Error al registrar (quizá el usuario ya existe).", "Error", JOptionPane.ERROR_MESSAGE);
            }

        // 7. AÑADIR MONEDAS
        } else if (origen == ventana.getBtnAnadirMonedas()) {
            String nombre = JOptionPane.showInputDialog(ventana, "Introduce el nombre del jugador:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                String cantidadStr = JOptionPane.showInputDialog(ventana, "¿Cuántas monedas quieres añadir a " + nombre + "?");
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    boolean exito = Consultas.anadirMonedas(conexion, nombre.trim(), cantidad);
                    if(exito) JOptionPane.showMessageDialog(ventana, "Monedas añadidas correctamente.", "Transacción OK", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(ventana, "Error: Jugador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ventana, "Debes introducir un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        // 8. DAR DE BAJA
        } else if (origen == ventana.getBtnBaja()) {
            String nombre = JOptionPane.showInputDialog(ventana, "Introduce el nombre del usuario a dar de baja:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                int confirmacion = JOptionPane.showConfirmDialog(ventana, "¿Seguro que quieres borrar a " + nombre + "?", "Aviso", JOptionPane.YES_NO_OPTION);
                if(confirmacion == JOptionPane.YES_OPTION) {
                    boolean exito = Consultas.darDeBaja(conexion, nombre.trim());
                    if(exito) JOptionPane.showMessageDialog(ventana, "Jugador eliminado del sistema.", "Baja OK", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(ventana, "Jugador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        // 9. SIMULAR BATALLA
        } else if (origen == ventana.getBtnSimular()) {
            gestionarFlujoSimulador();

        // 10. SALIR
        } else if (origen == ventana.getBtnSalir()) {
            int confirmacion = JOptionPane.showConfirmDialog(ventana, "¿Estás seguro de que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) System.exit(0);
        }
    }

    private void gestionarFlujoSimulador() {
        String jugador1 = JOptionPane.showInputDialog(ventana, "Nombre de Usuario del Jugador 1:", "Simulador de Batalla", JOptionPane.QUESTION_MESSAGE);
        if (jugador1 == null || jugador1.trim().isEmpty()) return;

        String jugador2 = JOptionPane.showInputDialog(ventana, "Nombre de Usuario del Jugador 2:", "Simulador de Batalla", JOptionPane.QUESTION_MESSAGE);
        if (jugador2 == null || jugador2.trim().isEmpty()) return;

        int poder1 = Consultas.calcularPoderMazo(conexion, jugador1.trim());
        int poder2 = Consultas.calcularPoderMazo(conexion, jugador2.trim());

        if (poder1 == 0 || poder2 == 0) {
            JOptionPane.showMessageDialog(ventana, "Error: Uno o ambos usuarios no existen o no poseen cartas cargadas.", "Error de Simulación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panelCarga = new JPanel(new GridBagLayout());
        panelCarga.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel lblCalculando = new JLabel("EFECTUANDO CÁLCULO DE PODER DE COMBATE", SwingConstants.CENTER);
        lblCalculando.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCalculando.setForeground(new Color(41, 128, 185));
        panelCarga.add(lblCalculando, gbc);

        JProgressBar barra = new JProgressBar(0, 100);
        barra.setStringPainted(true);
        barra.setPreferredSize(new Dimension(450, 30));
        gbc.gridy = 1;
        panelCarga.add(barra, gbc);

        ventana.cambiarPanelContenido(panelCarga);

        SwingWorker<Void, Integer> tareaAsincrona = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i += 10) {
                    Thread.sleep(120); 
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> trozos) {
                barra.setValue(trozos.get(trozos.size() - 1));
            }

            @Override
            protected void done() {
                panelCarga.removeAll();
                gbc.gridy = 0;

                JPanel tarjetaResultado = new JPanel(new BorderLayout());
                tarjetaResultado.setPreferredSize(new Dimension(550, 250));
                tarjetaResultado.setBorder(BorderFactory.createLineBorder(new Color(180, 190, 200), 2));

                JLabel lblResultadoBanner = new JLabel("", SwingConstants.CENTER);
                lblResultadoBanner.setFont(new Font("Segoe UI", Font.BOLD, 24));
                lblResultadoBanner.setForeground(Color.WHITE);
                lblResultadoBanner.setOpaque(true);
                lblResultadoBanner.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

                JLabel lblCuerpoResultado = new JLabel("", SwingConstants.CENTER);
                lblCuerpoResultado.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                lblCuerpoResultado.setBackground(Color.WHITE);
                lblCuerpoResultado.setOpaque(true);

                if (poder1 > poder2) {
                    lblResultadoBanner.setText("VICTORIA JUGADOR 1");
                    lblResultadoBanner.setBackground(new Color(46, 204, 113));
                    lblCuerpoResultado.setText("<html><center>Ganador: <b>" + jugador1.toUpperCase() + "</b><br><br>Poder Mazo: <font color='green'>" + poder1 + "</font> pts vs <font color='red'>" + poder2 + "</font> pts</center></html>");
                } else if (poder2 > poder1) {
                    lblResultadoBanner.setText("VICTORIA JUGADOR 2");
                    lblResultadoBanner.setBackground(new Color(241, 196, 15));
                    lblResultadoBanner.setForeground(Color.BLACK);
                    lblCuerpoResultado.setText("<html><center>Ganador: <b>" + jugador2.toUpperCase() + "</b><br><br>Poder Mazo: <font color='green'>" + poder2 + "</font> pts vs <font color='red'>" + poder1 + "</font> pts</center></html>");
                } else {
                    lblResultadoBanner.setText("EMPATE ÉPICO");
                    lblResultadoBanner.setBackground(new Color(155, 89, 182));
                    lblCuerpoResultado.setText("<html><center>¡Los ejércitos empataron en combate!<br><br>Ambos contendientes alcanzaron un poder de <b>" + poder1 + "</b> pts.</center></html>");
                }

                tarjetaResultado.add(lblResultadoBanner, BorderLayout.NORTH);
                tarjetaResultado.add(lblCuerpoResultado, BorderLayout.CENTER);

                panelCarga.add(tarjetaResultado, gbc);
                panelCarga.revalidate();
                panelCarga.repaint();
            }
        };

        tareaAsincrona.execute();
    }
}