package modelo;

import java.io.Serializable;

public class Ronda implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Jugador tirador;
	private Jugador apostador;
	@SuppressWarnings("unused")
	private Cubilete cubilete;
	Tirada tirada;
	
	public Ronda(Jugador tirador, Jugador apostador, Cubilete cubilete){
		this.tirador = tirador;
		this.apostador = apostador;
		this.cubilete = cubilete;
	}
	public Ronda(Jugador tirador, Jugador apostador){
		this.tirador = tirador;
		this.apostador = apostador;
	}
	
	public void addTirada(Jugador tira,Cubilete cubilete){
		tirada = new Tirada(tirador,cubilete);
	}
	public int getValorTirada(){
		return this.tirada.getResultado();
	}
	public Tirada getTirada(){
		return this.tirada;
	}
	public Jugador getTirador(){
		return tirador;
	}
	public Jugador getApostador(){
		return apostador;
	}
}
