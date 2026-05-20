package tarea_final;

public class CartaHechizo extends Carta{
	private String tipo_efecto;
	private int duracion_turnos;
	
	public CartaHechizo(int id_carta, String nombre, String descripcion, String tipo_carta, String region,
			String rareza, String tipo_efecto, int duracion_turnos) {
		super(id_carta, nombre, descripcion, tipo_carta, region, rareza);
		this.tipo_efecto = tipo_efecto;
		this.duracion_turnos = duracion_turnos;
	}

	public String getTipo_efecto() {
		return tipo_efecto;
	}

	public void setTipo_efecto(String tipo_efecto) {
		this.tipo_efecto = tipo_efecto;
	}

	public int getDuracion_turnos() {
		return duracion_turnos;
	}

	public void setDuracion_turnos(int duracion_turnos) {
		this.duracion_turnos = duracion_turnos;
	}

	@Override
	public String toString() {
		return super.toString() + "\n- Tipo de efecto: " + tipo_efecto + "\n- Turnos: " + duracion_turnos;
	}
	
	
}
