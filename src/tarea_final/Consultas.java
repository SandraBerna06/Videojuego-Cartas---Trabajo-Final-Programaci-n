package tarea_final;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;

public class Consultas {

	// =========================================================================
	// MÉTODOS DE LECTURA (Devuelven DefaultTableModel para pintar en JTables)
	// =========================================================================

	public static DefaultTableModel obtenerModeloCartasPersonaje(Connection conexion) {
		DefaultTableModel modelo = new DefaultTableModel(
				new String[] { "ID", "Nombre", "Descripción", "Tipo", "Región", "Rareza" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			} // Celdas de solo lectura
		};

		String query = "SELECT c.id_carta, c.nombre, c.descripcion, c.tipo_carta, re.nombre_region, ra.nombre AS rareza "
				+ "FROM carta c " + "JOIN region re ON re.id_region = c.id_region "
				+ "JOIN rareza ra ON ra.id_rareza = c.id_rareza " + "WHERE c.tipo_carta = 'Personaje' "
				+ "ORDER BY c.id_carta;";

		try (
				Statement comando = conexion.createStatement();
				ResultSet resultado = comando.executeQuery(query)) {

			while (resultado.next()) {
				modelo.addRow(new Object[] { resultado.getInt("id_carta"), resultado.getString("nombre"),
						resultado.getString("descripcion"), resultado.getString("tipo_carta"),
						resultado.getString("nombre_region"), resultado.getString("rareza") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelo;
	}

	public static DefaultTableModel obtenerModeloCartasHechizo(Connection conexion) {
		DefaultTableModel modelo = new DefaultTableModel(
				new String[] { "ID", "Nombre", "Descripción", "Tipo", "Rareza" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String query = "SELECT c.id_carta, c.nombre, c.descripcion, c.tipo_carta, ra.nombre AS rareza "
				+ "FROM carta c " + "JOIN rareza ra ON ra.id_rareza = c.id_rareza " + "WHERE c.tipo_carta = 'Hechizo' "
				+ "ORDER BY c.id_carta;";

		try (Statement comando = conexion.createStatement(); ResultSet resultado = comando.executeQuery(query)) {

			while (resultado.next()) {
				modelo.addRow(new Object[] { resultado.getInt("id_carta"), resultado.getString("nombre"),
						resultado.getString("descripcion"), resultado.getString("tipo_carta"),
						resultado.getString("rareza") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelo;
	}

	public static DefaultTableModel obtenerModeloRareza(Connection conexion, String rareza) {
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "ID", "Nombre", "Descripción", "Tipo" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String query = "SELECT c.id_carta, c.nombre, c.descripcion, c.tipo_carta " + "FROM carta c "
				+ "JOIN rareza r ON r.id_rareza = c.id_rareza " + "WHERE r.nombre = ?;";

		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, rareza);
			try (ResultSet resultado = ps.executeQuery()) {
				while (resultado.next()) {
					modelo.addRow(new Object[] { resultado.getInt("id_carta"), resultado.getString("nombre"),
							resultado.getString("descripcion"), resultado.getString("tipo_carta") });
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelo;
	}

	public static DefaultTableModel obtenerModeloHabilidades(Connection conexion) {
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Personaje", "Habilidad", "Descripción" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String query = "SELECT c1.nombre, h.nombre_habilidad, h.efecto " + "FROM carta c1 "
				+ "JOIN carta_personaje cp ON c1.id_carta = cp.id_carta "
				+ "JOIN habilidad h ON h.id_carta = cp.id_carta " + "ORDER BY c1.nombre;";

		try (Statement comando = conexion.createStatement(); ResultSet resultado = comando.executeQuery(query)) {

			while (resultado.next()) {
				modelo.addRow(new Object[] { resultado.getString(1), resultado.getString(2), resultado.getString(3) });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelo;
	}

	public static DefaultTableModel obtenerModeloJugadores(Connection conexion) {
		DefaultTableModel modelo = new DefaultTableModel(
				new String[] { "Nombre del Jugador", "Nivel", "Monedas", "Creación" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String query = "SELECT nombre_usuario, nivel, monedas_ficticias, fecha_creacion FROM jugador";

		try (Statement comando = conexion.createStatement(); ResultSet resultado = comando.executeQuery(query)) {

			while (resultado.next()) {
				modelo.addRow(new Object[] { resultado.getString("nombre_usuario"), resultado.getInt("nivel"),
						resultado.getInt("monedas_ficticias"), resultado.getDate("fecha_creacion") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelo;
	}

	// =========================================================================
	// MÉTODOS DE ESCRITURA (Devuelven boolean para que la GUI muestre el éxito)
	// =========================================================================

	public static boolean registrarUsuario(Connection conexion, String nombreUsuario) {
		String query = "INSERT INTO Jugador (nombre_usuario, fecha_creacion) VALUES (?, ?);";

		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, nombreUsuario);
			ps.setDate(2, Date.valueOf(LocalDate.now()));

			int resultado = ps.executeUpdate();
			return resultado > 0; // Retorna true si se insertó correctamente

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean darDeBaja(Connection conexion, String nombreUsuario) {
		String query = "DELETE FROM Jugador WHERE nombre_usuario = ? ";

		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, nombreUsuario);

			int resultado = ps.executeUpdate();
			return resultado > 0; // Retorna true si se eliminó correctamente

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean anadirMonedas(Connection conexion, String usuario, int cantidad) {
		String query = "UPDATE Jugador SET monedas_ficticias = monedas_ficticias + ? WHERE nombre_usuario = ?";

		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setInt(1, cantidad);
			ps.setString(2, usuario);

			int resultado = ps.executeUpdate();
			return resultado > 0; // Retorna true si se actualizaron las monedas

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static DefaultTableModel obtenerModeloTodasLasCartas(Connection conexion) {
	    DefaultTableModel modelo = new DefaultTableModel(
	            new String[] { "ID", "Nombre", "Tipo", "Región/Efecto", "Rareza" }, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    // Usamos IFNULL para devolver el efecto si la región es nula
	    String query = "SELECT c.id_carta, c.nombre, c.tipo_carta, "
	            + "IFNULL(re.nombre_region, ch.tipo_efecto) AS region_efecto, "
	            + "ra.nombre AS rareza\n"
	            + "FROM carta c\n"
	            + "LEFT JOIN region re ON re.id_region = c.id_region\n"
	            + "LEFT JOIN rareza ra ON ra.id_rareza = c.id_rareza\n"
	            + "LEFT JOIN carta_hechizo ch ON c.id_carta = ch.id_carta\n"
	            + "ORDER BY c.id_carta;";

	    try (Statement comando = conexion.createStatement(); 
	         ResultSet resultado = comando.executeQuery(query)) {

	        while (resultado.next()) {
	            modelo.addRow(new Object[] { 
	                    resultado.getInt("id_carta"), 
	                    resultado.getString("nombre"),
	                    resultado.getString("tipo_carta"), 
	                    resultado.getString("region_efecto"), 
	                    resultado.getString("rareza") 
	            });
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return modelo;
	}
	
	
	/*
	public static DefaultTableModel obtenerModeloTodasLasCartas(Connection conexion) {
		DefaultTableModel modelo = new DefaultTableModel(
				new String[] { "ID", "Nombre", "Tipo", "Región/Efecto", "Rareza" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String query = "SELECT c.id_carta, c.nombre, c.tipo_carta, re.nombre_region, ch.tipo_efecto, ra.nombre AS rareza\n"
				+ "FROM carta c\n"
				+ "LEFT JOIN region re ON re.id_region = c.id_region\n"
				+ "LEFT JOIN rareza ra ON ra.id_rareza = c.id_rareza\n"
				+ "LEFT JOIN carta_hechizo ch ON c.id_carta = ch.id_carta\n"
				+ "ORDER BY c.id_carta;";

		try (Statement comando = conexion.createStatement(); ResultSet resultado = comando.executeQuery(query)) {

			while (resultado.next()) {
				modelo.addRow(new Object[] { resultado.getInt("id_carta"), resultado.getString("nombre"),
						resultado.getString("tipo_carta"), resultado.getString("region_efecto"),
						resultado.getString("rareza") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelo;
	}


	*/
	// =========================================================================
	// MÉTODOS DE CÁLCULO LÓGICO
	// =========================================================================

	// Se cambia a public para que el Escuchador pueda usarlo en el simulador
	// gráfico
	public static int calcularPoderMazo(Connection conexion, String nombreUsuario) {
		int poderTotal = 0;
		String query = "CALL ObtenerMazo(?)";

		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, nombreUsuario);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String tipo = rs.getString("tipo_carta");

					if (tipo.equalsIgnoreCase("Personaje")) {
						int salud = rs.getInt("puntos_salud");
						int velocidad = rs.getInt("velocidad");
						poderTotal += (salud + velocidad);
					} else if (tipo.equalsIgnoreCase("Hechizo")) {
						poderTotal += 80;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al calcular el poder del mazo del jugador: " + nombreUsuario);
			e.printStackTrace();
		}

		return poderTotal;
	}
}