package modelo;

import java.io.Serializable;

public class Jugador extends Persona implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Jugador(String nombre, String inicial, double dinero) {
		super(nombre,inicial,dinero);
		this.dineroEnJuego = 0;
	}
}
