package tarea_final;

public class Jugador {
	private String nombre_usuario;
	private int nivel;
	private int monedas;
	private String fecha_creacion;
	
	public Jugador(String nombre_usuario, int nivel, int monedas, String fecha_creacion) {
		super();
		this.nombre_usuario = nombre_usuario;
		this.nivel = nivel;
		this.monedas = monedas;
		this.fecha_creacion = fecha_creacion;
	}
	
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	
	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public int getMonedas() {
		return monedas;
	}
	
	public void setMonedas(int monedas) {
		this.monedas = monedas;
	}
	
	public String getFecha_creacion() {
		return fecha_creacion;
	}
	
	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	@Override
	public String toString() {
		return "Jugador: " + nombre_usuario + "\n- Nivel: " + nivel + "\n- Monedas: " + monedas
				+ "\n - Fecha de creación: " + fecha_creacion;
	}
	
	
}
