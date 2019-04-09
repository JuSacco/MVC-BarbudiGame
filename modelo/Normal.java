package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Normal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Jugador apostador;
	private double tope;
	private double acumulado;
	private ArrayList<Jugador> aFavorTirador;
	
	public Normal(Jugador apostador,double dinero){
		this.apostador = apostador;
		this.tope = dinero;
		this.acumulado = 0;
		this.apostador.setDineroEnJuego(dinero);
		this.apostador.removeDinero(dinero);
		aFavorTirador = new ArrayList<>();
	}
	
	public void agregarAFavor(Jugador e,double dinero){
		if(dinero<=tope){
			this.acumulado += dinero;
			e.setDineroEnJuego(dinero);
			e.removeDinero(dinero);
			aFavorTirador.add(e);
		}
	}
	public boolean isEquilibrado(){
		return(acumulado==tope)?true:false;
	}
	public void devolverRestante(){
		if(!isEquilibrado()){
			this.apostador.addDinero(tope-acumulado);
			this.apostador.setDineroEnJuego(acumulado);
		}
	}
	public void repartirApuestas(boolean ganoTirador,boolean modoNormal){
		if(modoNormal){
			if(ganoTirador){
				for(Jugador e : aFavorTirador){
					e.addDinero(e.getDineroEnJuego()*2);
					e.setDineroEnJuego(0);
					this.apostador.setDineroEnJuego(0);
				}
			}else{
				this.apostador.addDinero(this.apostador.getDineroEnJuego()*2);
				this.apostador.setDineroEnJuego(0);
				for(Jugador e: aFavorTirador){
					e.setDineroEnJuego(0);
				}
			}
		}else{
			if(ganoTirador){
				for(Jugador e : aFavorTirador){
					double ganado = e.getDineroEnJuego()*2;
					double comision = ((ganado)*0.025);
					e.addDinero(ganado-comision);
					e.setDineroEnJuego(0);
					this.apostador.setDineroEnJuego(0);
				}
			}else{
				double ganado = this.apostador.getDineroEnJuego()*2;
				double comision = ((ganado)*0.025);
				this.apostador.addDinero(ganado-comision);
				this.apostador.setDineroEnJuego(0);
				for(Jugador e: aFavorTirador){
					e.setDineroEnJuego(0);
				}
			}
		}
	}
}
