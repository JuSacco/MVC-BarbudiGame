package modelo;

import java.io.Serializable;

public class Empleado extends Persona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Empleado(String nombre) {
		super();
		this.nombre = nombre;
	}

}
