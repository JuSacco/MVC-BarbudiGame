package modelo;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;

public class Barbudi extends Observable
					 implements IBarbudi,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7640544429675131092L;
	Jugador tirador;
	Jugador apostador;
	Empleado empleado;
	boolean modoNormal;
	
	ArrayList<Jugador> jugadores = new ArrayList<>();
	ArrayList<Espectador> espectadores = new ArrayList<>();
	ArrayList<Empleado> empleados = new ArrayList<>();
	ArrayList<Ronda> rondas = new ArrayList<>();
	ArrayList<Cubilete> cubiletes = new ArrayList<>();
	ArrayList<Normal> historialApuestasNormales = new ArrayList<>();
	ArrayList<Lateral> historialApuestasLaterales = new ArrayList<>();
	ArrayList<Jugador> jugadoresRetirados = new ArrayList<>();

/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		//////////////////////////////////////////////Constructor/////////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
	public Barbudi(){
		this.tirador = null;
		this.apostador = null;
		this.empleado = null;
	}
	/*
	 * ---ADDS---
	 */
	public void addCubilete(){
		cubiletes.add(new Cubilete());
	}
	
	public void addEmpleado(String nombre){
		empleados.add(new Empleado(nombre));
	}
	
	public void addJugador(String nombre,String inicial,double dinero){
		jugadores.add(new Jugador(nombre,inicial,dinero));
	}
	
	public void addEspectador(String nombre,String inicial,double dinero){
		espectadores.add(new Espectador(nombre,inicial,dinero));
	}
	
	public void addRonda(Ronda ronda){
		rondas.add(ronda);
	}
	public void addNewRonda(String nuevoTirador,String nuevoApostador){
		if(!nuevoTirador.equals(nuevoApostador)){
			this.setTirador((Jugador)getPersonaByName(nuevoTirador,"jugador"));
			this.setApostador((Jugador)getPersonaByName(nuevoApostador,"jugador"));
			rondas.add(new Ronda(this.tirador,this.apostador));
			setChanged();
			notifyObservers("tirador "+nuevoTirador);
			notifyObservers("apostador "+nuevoApostador);
		}else{
			setChanged();
			notifyObservers("error apostador=tirador");
		}
	}
	
	public void addTiradaToRonda(String nombre){
		getUltimaRonda().addTirada((Jugador)getPersonaByName(nombre,"jugador"), this.cubiletes.get(0));
	}

	public void estadoJugadores(){
		//Only for developer testing
		for(Jugador e:jugadores){
			System.out.println("Jugador: "+e.nombre+" Dinero disponible:"+e.dinero+" Dinero en juego:"+e.dineroEnJuego);	
		}
		for(Espectador e:espectadores){
			System.out.println("Espectador: "+e.nombre+" Dinero disponible:"+e.dinero+" Dinero en juego:"+e.dineroEnJuego);	
		}
	}

	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		////////////////////////////////////////////---GETTERS---/////////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
	@Override
	public String getApostadorName() {
		return this.apostador.nombre;
	}

	@Override
	public String getTiradorName() {
		return this.tirador.nombre;
	}

//getPersonaByName: se le pasa el nombre de la persona junto con el tipo y la busca
	public Persona getPersonaByName(String nombre,String tipo){
		//Solo Jugadores
		if(tipo.equals("jugador"))
			for(Jugador e : jugadores){
				if(e.nombre.equals(nombre)){
					return e;
				}
			}

		//Solo Espectadores
		if(tipo.equals("espectador"))
			for(Espectador e : espectadores){
				if(e.nombre.equals(nombre)){
					return e;
				}
			}

		//Solo Empleados
		if(tipo.equals("empleado")){
			for(Empleado e : empleados){
				if(e.nombre.equals(nombre))
					return e;
			}
		}

		//Espectadores y Jugadores
		if(tipo.equals("espectadorOjugador")){
			for(Jugador e : jugadores){
				if(e.nombre.equals(nombre)){
					return e;
				}
			}
			for(Espectador e : espectadores){
				if(e.nombre.equals(nombre)){
					return e;
				}
			}	
		}

		//Todas las listas
		if(tipo.equals("desconocido")){
			for(Jugador e : jugadores){
				if(e.nombre.equals(nombre)){
					return e;
				}
			}
			for(Espectador e : espectadores){
				if(e.nombre.equals(nombre)){
					return e;
				}
			}
			for(Empleado e : empleados){
				if(e.nombre.equals(nombre))
					return e;
			}
		}
		return null;
	}
	
	//Obtener el dinero disponible de cualquier jugador
	public double getDinero(String nombre){
		for(Persona e : jugadores){
			if(e.nombre.equals(nombre)){
				return e.dinero;
			}
		}
		for(Persona e : espectadores){
			if(e.nombre.equals(nombre)){
				return e.dinero;
			}
		}
		return 0;
	}
	
	public double getDineroEnJuegoApostador(){
			return this.apostador.getDineroEnJuego();
	}	
	
	public Empleado getEmpleado(){
		return empleado;
	}
	public String getEmpleadoName(){
		return empleado.nombre;
	}
	
	public Ronda getUltimaRonda(){
		return (rondas.size()==0)?null:(this.rondas.get(rondas.size()-1));
	}
	
	@Override
	public int getValorTirada() {
		Ronda ultimaRonda = getUltimaRonda();
		return ultimaRonda.getTirada().getResultado();
	}

	public Normal getUltimaApuestaNormal(){
		Normal ultima;
		if(this.historialApuestasNormales.size()==0)
			this.doApuestaNormal(0); //Cuando es la primer mano y el apostador quiere pasar, debo hacer una apuesta(aunque este vacia) es para evitar ArrayIndexOutOfBounds.
		ultima = this.historialApuestasNormales.get(this.historialApuestasNormales.size()-1);
		return ultima;
	}
	
	public Lateral getUltimaApuestaLateral(){
		return (this.historialApuestasLaterales.size()==0)?null:this.historialApuestasLaterales.get(this.historialApuestasLaterales.size()-1);
	}
	public ArrayList<String> getAllJugadores(){
		ArrayList<String> resultado = new ArrayList<>();
		for(Jugador e : jugadores){
			resultado.add(e.nombre);
			System.out.println(e.nombre);
		}
		return resultado;
	}
	
	public ArrayList<String> getAllEspectadores(){
		ArrayList<String> resultado = new ArrayList<>();
		for(Espectador e : espectadores){
			resultado.add(e.nombre);
			System.out.println(e.nombre);
		}
		return resultado;
	}

	public ArrayList<String> getAllEmpleados(){
		ArrayList<String> resultado = new ArrayList<>();
		for(Empleado e : empleados){
			resultado.add(e.nombre);
		}
		return resultado;
	}
	
	@Override
	public String[] getTop5Jugadores() {
		ArrayList<Jugador> auxListaJugadores = jugadores;
		ArrayList<Jugador> auxListaJugadoresRetirados = jugadoresRetirados;
		auxListaJugadores.addAll(auxListaJugadoresRetirados);
		String[] resultado = new String[5];
		String nombre;
		double dinero;
		Collections.sort(auxListaJugadores, new Comparator<Jugador>() {
			public int compare(Jugador p2, Jugador p1) {
				return (int) (p1.dinero - p2.dinero); 
			}
		});
		 for(int i=0;i<5;i++){
			 if(i<=auxListaJugadores.size()-1){
				 nombre = auxListaJugadores.get(i).nombre;
				 dinero = auxListaJugadores.get(i).dinero;
				 resultado[i]= nombre +" "+dinero;
			 }else
			 {
				 nombre = "Vacio";
				 dinero = 0.00;
				 resultado[i]= nombre +" "+dinero;
			 }
		 }
		 return resultado;
	}

	@Override
	public String[] getTop5Espectadores() {
		ArrayList<Espectador> auxListaEspectadores = espectadores;
		String[] resultado = new String[5];
		String nombre;
		double dinero;
		Collections.sort(auxListaEspectadores, new Comparator<Espectador>() {
			public int compare(Espectador p1, Espectador p2) {
				return (int) (p2.dinero - p1.dinero);
		    }
		});
		 for(int i=0;i<5;i++){
			 if(i<=auxListaEspectadores.size()-1){
				 nombre = auxListaEspectadores.get(i).nombre;
				 dinero = auxListaEspectadores.get(i).dinero;
				 resultado[i] = nombre +" "+dinero;
			 }else
			 {
				 nombre = "Vacio";
				 dinero = 0.00;
				 resultado[i] = nombre +" "+dinero;
			 } 
		 }
		 return resultado;
	}
	
	public String[] getEstadoEspectadores(){
		ArrayList<Espectador> auxEspectadores = espectadores;
		String[] resultado = new String[espectadores.size()];
		
		//Ordeno por dinero de mayor a menor
		Collections.sort(auxEspectadores, new Comparator<Espectador>() {
			public int compare(Espectador p2, Espectador p1) {
				return (int) (p1.dinero - p2.dinero); 
			}
		});
		int i = 0;
		for(Espectador e : auxEspectadores){
			 resultado[i]= e.nombre +" Dinero disponible:"+e.dinero;
			 i++;
		}
		return resultado;
	}

	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		////////////////////////////////////////////---REMOVERS---////////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
	public void removeEspectador(String e){
		for(Espectador esp : espectadores){
			if(esp.nombre == e){
				espectadores.remove(esp);
				break; //Esto fixea el problema de que el espectador sea el unico de la lista.
			}
		}
	}
	@Override
	public void removeJugador(String e) {
		for(Jugador j : jugadores){
			if(j.nombre.equals(e)){
				jugadores.remove(e);
				break;
			}
		}
	}
	@Override
	public void removeEmpleado(String e) {
		for(Empleado emp : empleados){
			if(emp.nombre == e){
				empleados.remove(emp);
				break;
			}
		}
	}
	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		/////////////////////////////////////////---METODOS GENERALES---//////////////////////////////////////////
 * 		/////////////////////////////////////////---COMMON    METHODS---//////////////////////////////////////////
*/
	/*
	 *  tirarDados() en la clase Barbudi solo se va a usar para inicializar la primera ronda, es la que decide quien sera el tirador.
	 *  luego se utiliza en ronda.
	 */
	public int tirarDados(){
		Tirada tirada = new Tirada(tirador,cubiletes.get(0));
		return tirada.getResultado();
	}
	
	public void inicializar(){
		//Este seria el caso que cargas una partida y deseas iniciar un nuevo juego con los jugadores de la partida cargada.
		if(jugadoresRetirados.size()>0){
			for(Jugador e:jugadoresRetirados){
				if(!jugadores.contains(e)){
					jugadores.add(e);
				}
			}
		}
		if(this.apostador != null && this.tirador!=null && this.hasEmpleado()){
			addRonda(new Ronda(this.tirador,this.apostador,this.cubiletes.get(cubiletes.size()-1)));
		}
	}
	
	public void doApuestaNormal(double dinero){
		Normal normal = new Normal(this.apostador,dinero);
		this.historialApuestasNormales.add(normal);
	}
	
	public void doApuestaLateral(){
		Lateral lateral = new Lateral();
		this.historialApuestasLaterales.add(lateral);
	}
	
	public void llenarApuestaNormal(String nombre,double dinero){
		Normal normal = getUltimaApuestaNormal();
		normal.agregarAFavor((Jugador)getPersonaByName(nombre,"jugador"),dinero);
	}

	public boolean llenarApuestaLateral(String persona,double dinero,boolean aFavorTirador){
		Lateral lateral = getUltimaApuestaLateral();
		if(aFavorTirador)
			return lateral.agregarAFavor(getPersonaByName(persona,"espectadorOjugador"), dinero);
		if(!aFavorTirador)
			return lateral.agregarEnContra(getPersonaByName(persona,"espectadorOjugador"), dinero);
		return false;
	}
	
	public boolean apuestaNormalEquilibrada(){
		return getUltimaApuestaNormal().isEquilibrado();
	}
	
	public boolean apuestaLateralEquilibrada(){
		return (getUltimaApuestaLateral()==null)?true:getUltimaApuestaLateral().isEquilibrado();
	}
	public void devolverRestante(){
		getUltimaApuestaNormal().devolverRestante();
	}

	public void repartirDineroRonda(boolean ganoTirador){
		getUltimaApuestaNormal().repartirApuestas(ganoTirador,this.modoNormal);
	}
	
	public void repartirDineroRondaLateral(boolean ganoTirador){
		getUltimaApuestaLateral().repartirApuestas(ganoTirador,this.modoNormal);
	}
	
	public void devolverDineroATodosLateral(){
		getUltimaApuestaLateral().devolverDineroATodosLateral();
	}
	
	public void retirarJugador(String nombre){
		Jugador aEliminar = (Jugador)getPersonaByName(nombre,"jugador");
		jugadoresRetirados.add(aEliminar);
		jugadores.remove(aEliminar);
	}
	
	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		/////////////////////////////////////////////---SETTERS---////////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
	@Override
	public void setPrimerTirador(String jugador) {
		int posTirador=0;
		for(Jugador e:jugadores){
			if(e.nombre == jugador){
				this.setTirador(e);	
				posTirador = jugadores.indexOf(e);
			}
		}
		if(posTirador>=jugadores.size()-1){
			this.setApostador(jugadores.get(0));
		}else{
			this.setApostador(jugadores.get(posTirador+1));
		}
	}

	private void setTirador(Jugador e) {
		this.tirador = e;
		setChanged();
		notifyObservers("tirador "+e.nombre);
	}
	
	private void setApostador(Jugador e) {
		this.apostador = e;
		setChanged();
		notifyObservers("apostador "+e.nombre);
	}
	
	@Override
	public void setEmpleado(String empleado) {
		if(empleado.equals("Sin Empleado")) {
			this.modoNormal=true;
			this.empleado = new Empleado("Generic");
		}else{
			this.empleado = (Empleado) getPersonaByName(empleado,"empleado");
			this.modoNormal = false;
		}
	}

/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		//////////////////////////////////////////---PERSISTENCIA---//////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
	public void guardarBarbudi(String ruta) throws IOException {
			FileOutputStream fileOut = new FileOutputStream(ruta+".dat");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(jugadores);
			out.writeObject(espectadores);
			out.writeObject(empleados);
			out.writeObject(rondas);
			out.writeObject(cubiletes);
			out.writeObject(historialApuestasLaterales);
			out.writeObject(historialApuestasNormales);
			out.writeObject(apostador);
			out.writeObject(tirador);
			out.writeObject(empleado);
			out.writeObject(jugadoresRetirados);
			out.close();
	}
	
	@SuppressWarnings("unchecked")
	public void leerBarbudiSerializable(String ruta) {
			try {//Creo el objeto InputStream
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
				// Se lee el primer objeto
				Object aux = ois.readObject();
				this.jugadores = (ArrayList<Jugador>) aux;
				System.out.println(jugadores);
				aux = ois.readObject();
				this.espectadores = (ArrayList<Espectador>) aux;
				aux = ois.readObject();
				this.empleados = (ArrayList<Empleado>) aux;
				aux = ois.readObject();
				this.rondas = (ArrayList<Ronda>) aux;
				aux = ois.readObject();
				this.cubiletes = (ArrayList<Cubilete>) aux;
				aux = ois.readObject();
				this.historialApuestasLaterales = (ArrayList<Lateral>) aux;
				aux = ois.readObject();
				this.historialApuestasNormales = (ArrayList<Normal>) aux;
				aux = ois.readObject();
				this.apostador = (Jugador) aux;
				aux = ois.readObject();
				this.tirador = (Jugador) aux;
				aux = ois.readObject();
				this.empleado = (Empleado) aux;
				aux = ois.readObject();
				this.jugadoresRetirados = (ArrayList<Jugador>) aux;
				ois.close();
				setChanged();
				this.notifyObservers("carga exito");
			}catch(EOFException e1) {
				System.out.println("Fin de Archivo");
			} catch (Exception e2) {
				e2.printStackTrace();
			}	
	}
	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		//////////////////////////////////////////---REGLAS JUEGO---//////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
	//tiroGanador devuelve true si el tiro es ganador
	public boolean tiroGanador(int tiro){
		double resultado = tiro;
		String resultString = Double.toString(resultado/10);
		String[] convertidor = resultString.split("\\.");
		int dado1 = Integer.valueOf(convertidor[0]);
		int dado2 = Integer.valueOf(convertidor[1]);
		
		boolean b=false;
		if((dado1==3||dado1==5||dado1==6)&&(dado2==3||dado2==5||dado2==6)){
			if(dado1==dado2){
				b=true;
			}
		}
		return b;
	}
	
	//tiroPerdedor devuelve true si el tiro es perdedor.
	public boolean tiroPerdedor(int tiro){
		double resultado = tiro;
		String resultString = Double.toString(resultado/10);
		String[] convertidor = resultString.split("\\.");
		int dado1 = Integer.valueOf(convertidor[0]);
		int dado2 = Integer.valueOf(convertidor[1]);
		boolean b=false;
		if((dado1==1||dado1==2||dado1==4)&&(dado2==1||dado2==2||dado2==4)){
			if(dado1==dado2){
				b=true;
			}
			/*else{
				b=tiroEspecial12(tiro);
			}*/
		}
		return b;
	}
	
	/**
	 * @return true: el tiro es 1-2;2-1. False ninguna de las dos anteriores
	 */
	public boolean tiroEspecial12(int tiro){
		double resultado = tiro;
		String resultString = Double.toString(resultado/10);
		String[] convertidor = resultString.split("\\.");
		int dado1 = Integer.valueOf(convertidor[0]);
		int dado2 = Integer.valueOf(convertidor[1]);
		return ((dado1==1&dado2==2)||(dado1==2&dado2==1))?true:false;
	}
	
	/**
	 * @return true: el tiro es 6-5;5-6. False ninguna de las dos anteriores
	 */
	public boolean tiroEspecial65(int tiro){
		double resultado = tiro;
		String resultString = Double.toString(resultado/10);
		String[] convertidor = resultString.split("\\.");
		int dado1 = Integer.valueOf(convertidor[0]);
		int dado2 = Integer.valueOf(convertidor[1]);
		return ((dado1==6&dado2==5)||(dado1==5&dado2==6))?true:false;
	}
	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		/////////////////////////////////////////////---MISC---///////////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
	private boolean hasEmpleado() {
		return (empleado==null)?false:true;
	}



}
