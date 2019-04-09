package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Lateral extends Observable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Persona> aFavorTirador;
	private ArrayList<Persona> enContraTirador;

	public Lateral() {
		aFavorTirador = new ArrayList<>();
		enContraTirador = new ArrayList<>();
	}
	
	public boolean agregarAFavor(Persona e,double dinero){
		boolean sePuede=true;
		//Verifico si la Persona no esta en contra, de estarlo le notifico al Observer
		if(this.enContraTirador.contains(e)){
			System.out.println("Retorno false");
			return false;
		}else{
			if((e instanceof Espectador || e instanceof Jugador)&&sePuede){
				e.setDineroEnJuego(dinero);
				e.removeDinero(dinero);
				aFavorTirador.add(e);
			}
		}
		return sePuede;
	}
	
	public boolean agregarEnContra(Persona e,double dinero){
		boolean sePuede=true;
		//Verifico si la Persona no esta en contra, de estarlo le notifico al Observer
		if(this.aFavorTirador.contains(e)){
			return false;
		}else{
			System.out.println("Se puede:"+sePuede);
			if((e instanceof Espectador || e instanceof Jugador)&&sePuede){
				e.setDineroEnJuego(dinero);
				e.removeDinero(dinero);
				enContraTirador.add(e);
			}
		}
		return sePuede;
	}
	
	public void devolverDineroATodosLateral(){
		for(Persona p1:aFavorTirador){
			p1.addDinero(p1.getDineroEnJuego());
			p1.setDineroEnJuego(0);
		}
		for(Persona p2:enContraTirador){
			p2.addDinero(p2.getDineroEnJuego());
			p2.setDineroEnJuego(0);
		}
	}
	
	public boolean isEquilibrado(){
		double dineroFavor = 0;
		double dineroContra = 0;
		for(Persona e:aFavorTirador){
			dineroFavor += e.getDineroEnJuego();
		}
		for(Persona c:enContraTirador){
			dineroContra += c.getDineroEnJuego();
		}

		if(dineroFavor == dineroContra)
			return true;
		
		if(dineroFavor>dineroContra){
			double diferencia = dineroFavor-dineroContra;
			diferencia = diferencia/aFavorTirador.size();
			for(Persona e : aFavorTirador){
				e.addDinero(diferencia);
				e.setDineroEnJuego(e.getDineroEnJuego()-diferencia);
			}
			
			return true;
		}
		if(dineroFavor<dineroContra){
			double diferencia = dineroContra-dineroFavor;
			diferencia = diferencia/enContraTirador.size();
			for(Persona e : enContraTirador){
				e.addDinero(diferencia);
				e.setDineroEnJuego(e.getDineroEnJuego()-diferencia);
			}
			return true;
		}
		return false;
	}

	public void repartirApuestas(boolean ganoTirador,boolean modoNormal) {
		if(modoNormal){
			if(ganoTirador){
				for(Persona p1:aFavorTirador){
					p1.addDinero(p1.getDineroEnJuego()*2);
					p1.setDineroEnJuego(0);
				}
				for(Persona p1:enContraTirador){
					p1.setDineroEnJuego(0);
				}
			}else{
				for(Persona p1:enContraTirador){
					p1.addDinero(p1.getDineroEnJuego()*2);
					p1.setDineroEnJuego(0);
				}
				for(Persona p1:aFavorTirador){
					p1.setDineroEnJuego(0);
				}
			}
		}else{
			if(ganoTirador){
				for(Persona p1:aFavorTirador){
					double ganado = p1.getDineroEnJuego()*2;
					double comision = ((ganado)*0.025);
					p1.addDinero(ganado-comision);
					p1.setDineroEnJuego(0);
				}
				for(Persona p1:enContraTirador){
					p1.setDineroEnJuego(0);
				}
			}else{
				for(Persona p1:enContraTirador){
					double ganado = p1.getDineroEnJuego()*2;
					double comision = ((ganado)*0.025);
					p1.addDinero(ganado-comision);
					p1.setDineroEnJuego(0);
				}
				for(Persona p1:aFavorTirador){
					p1.setDineroEnJuego(0);
				}
			}
		}
	}
}
