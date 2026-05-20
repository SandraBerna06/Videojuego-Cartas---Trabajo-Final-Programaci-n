package tarea_final;

public class Carta {
	protected int id_carta;
	protected String nombre;
	protected String descripcion;
	protected String tipo_carta;
	protected String region;
	protected String rareza;
	
	public Carta(int id_carta, String nombre, String descripcion, String tipo_carta, String region, String rareza) {
		super();
		this.id_carta = id_carta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo_carta = tipo_carta;
		this.region = region;
		this.rareza = rareza;
	}

	public Carta() {
		super();
	}

	public int getId_carta() {
		return id_carta;
	}

	public void setId_carta(int id_carta) {
		this.id_carta = id_carta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo_carta() {
		return tipo_carta;
	}

	public void setTipo_carta(String tipo_carta) {
		this.tipo_carta = tipo_carta;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRareza() {
		return rareza;
	}

	public void setRareza(String rareza) {
		this.rareza = rareza;
	}

	@Override
	public String toString() {
		return "Carta:\n" +"- Nombre: " + nombre + "\n- Descripción: " + descripcion + "\n- Tipo de carta: "
				+ tipo_carta + "\n- Región: " + region + "\n- Rareza: " + rareza;
	}
	
	public static boolean rarezaValida (String rareza) {
		boolean valido = false;
		if (rareza.equalsIgnoreCase("Comun") || rareza.equalsIgnoreCase("Rara") || rareza.equalsIgnoreCase("Epica") || rareza.equalsIgnoreCase("Legendaria")) {
			valido = true;
		} else {
			System.out.println("Ese tipo de rareza no existe.");
		}
		return valido;
	}
}
