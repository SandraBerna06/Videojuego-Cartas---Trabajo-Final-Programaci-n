package tarea_final;

import javax.swing.UIManager;
import java.sql.Connection;

public class Principal {
	
	public static void main(String[] args) {
		// Poner un diseño moderno a los botones de Swing (Nimbus Look And Feel)
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Usando el diseño visual por defecto.");
		}
		
		// Iniciamos la música de fondo
        // Asegúrate de que "tema_principal.wav" esté en la carpeta del proyecto
        ReproductorAudio.reproducirMusicaFondo("musica-videojuego.wav");
		
		// 1. Usamos tu clase de conexión para conectarnos a la BBDD
		MySQLConnection db = new MySQLConnection();
		Connection conexion = db.mySQLConnect();
		
		// 2. Iniciamos la ventana y le pasamos la conexión
		java.awt.EventQueue.invokeLater(() -> {
			VentanaPrincipal app = new VentanaPrincipal(conexion);
			app.setVisible(true);
		});
	}
}