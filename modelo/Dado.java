package modelo;

import java.io.Serializable;
import java.util.Random;

public class Dado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int valor;
	private Random rnd = new Random();
	
	public int getValor(){
		return valor;
	}
	public void setValorRandom(){
		rnd.setSeed(System.currentTimeMillis());
		this.valor = rnd.nextInt(6) + 1;
	}
}
