package modelo;

import java.io.Serializable;

public class Tirada implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cubilete cubilete;
	Jugador tirador;
	int resultado;
	
	public Tirada(Jugador tirador,Cubilete cubilete){
		this.tirador = tirador;
		this.cubilete = cubilete;
		cubilete.setValorRandom();
		this.resultado = cubilete.getValores();
	}
	
	public int getResultado(){
		return resultado;
	}
}
