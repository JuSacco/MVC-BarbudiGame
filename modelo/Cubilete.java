package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Cubilete implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Dado> dados;
	public Cubilete(){
		dados = new ArrayList<>();
		addDado();
		addDado();
	}

	public int getValores(){
		return (dados.get(0).getValor()*10)+(dados.get(1).getValor());
	}
	public void setValorRandom(){
		for(Dado d:dados){
			//Duermo la ejecucion 27ms para que el random no me de igual en ambos dados
			try {
				Thread.sleep(27);
				} 
			catch (InterruptedException err) {
					err.printStackTrace();
				}
			d.setValorRandom();
		}
	}
	private void addDado(){
		dados.add(new Dado());
	}
}
