package modelo;

import java.io.Serializable;

public abstract class Persona implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nombre;
	String iniciales;
	double dinero;
	double dineroEnJuego;
	
	public Persona(){
		this.nombre ="";
		this.iniciales="";
		this.dinero = 0;
	}
	public Persona(String nombre, String inicial, double dinero) {
		this.nombre = nombre;
		this.iniciales = inicial;
		this.dinero = dinero;
	}
	public void setDineroEnJuego(double dinero){
		this.dineroEnJuego = (dinero<=this.dinero)?dinero:0;
	}
	public double getDineroEnJuego(){
		return this.dineroEnJuego;
	}
	protected void addDinero(double cantidad){
		this.dinero += cantidad;
	}
	protected void removeDinero(double cantidad){
		this.dinero -= cantidad;
	}
	public double getDinero(){
		return this.dinero;
	}
}
