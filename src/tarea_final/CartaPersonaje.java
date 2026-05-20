package tarea_final;

public class CartaPersonaje extends Carta{
	private String categoria;
	private int puntos_salud;
	private int velocidad;
	
	
	public CartaPersonaje(int id_carta, String nombre, String descripcion, String tipo_carta, String region,
			String rareza, String categoria, int puntos_salud, int velocidad) {
		super(id_carta, nombre, descripcion, tipo_carta, region, rareza);
		this.categoria = categoria;
		this.puntos_salud = puntos_salud;
		this.velocidad = velocidad;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public int getPuntos_salud() {
		return puntos_salud;
	}


	public void setPuntos_salud(int puntos_salud) {
		this.puntos_salud = puntos_salud;
	}


	public int getVelocidad() {
		return velocidad;
	}


	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}


	@Override
	public String toString() {
		return super.toString() +"\n- Categoria: " + categoria + "\n- Puntos de salud: " + puntos_salud + 
				"\n- Velocidad: " + velocidad;
	}
	
	
	
}
