package vista.consola;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controlador.*;

public class Consola implements Observer {
	private IControlador controlador;
	private Scanner rd = new Scanner(System.in);
	private String genericNombre;
	private String genericIniciales;
	private Double genericDinero;
	private int genericInt;
	private int opcion = 0;
	private ArrayList<String> listaJugadores = new ArrayList<>();
	private ArrayList<String> listaEspectadores = new ArrayList<>();
	private ArrayList<String> listaEmpleados = new ArrayList<>();
	private String tirador;
	private String apostador;
	private boolean tiradorActivo;
	private boolean apostadorActivo;
	private boolean sePuedeTirarDados;
	private boolean avanzaTirador;
	private boolean avanzaApostador;
	private boolean avanzaAmbos;
	@SuppressWarnings("unused")
	private boolean modoNormal = true; //Por Default. Warning porque esta dentro de un if()
	private String empleado = "Sin Empleado"; //Por Default TODO: Nunca lo uso INTEGRAR 
	private boolean terminar;

	
	
	public Consola(IControlador controlador){
		this.controlador = controlador;
		mainMenu();
	}
	
	public static void clearScreen() {  
	    System.out.println("\n\n");  
	   }  
	
	private void mainMenu() {
		System.out.println("################################################################################################");
		System.out.println("##################################____Barbudi____###############################################");
		System.out.println("################################################################################################");
		System.out.println("################################### 1. Jugar    ################################################");
		System.out.println("################################### 2. Opciones ################################################");
		System.out.println("################################### 3. TOP 5    ################################################");
		System.out.println("################################### 4. Cargar   ################################################");
		System.out.println("################################### 5. Guardar  ################################################");
		System.out.println("################################### 6. Salir    ################################################");
		System.out.println("################################################################################################");
		int opcion = rd.nextInt();
		switch(opcion){
			case 1: prepararJuego();break;
			case 2: opciones();break;
			case 3: top5();break;
			case 4: cargar();break;
			case 5: guardar();break;
			case 6: System.exit(0); break;		
		}
	}

	private void guardar() {
		System.out.println("Indique la ruta donde desea guardar:");
		String ruta = rd.next();
		if(!ruta.equals("")){
			try {
				this.controlador.guardarBarbudi(ruta);
				System.out.println("Exito al guardar");
			} catch (IOException e) {
				e.printStackTrace();
			}				
		}
		mainMenu();
	}

	private void cargar() {
		System.out.println("Indique la ruta donde desea cargar el juego:");
		String ruta = rd.next();
		if(!ruta.equals("")){
			try{
				this.controlador.cargarEstado(ruta);
				listaEmpleados = this.controlador.getAllEmpleados();
				listaJugadores = this.controlador.getAllJugadores();
				listaEspectadores = this.controlador.getAllEspectadores();
				continuarEmpezado();
			}catch(Exception e){
				System.out.println(e+": El archivo no fue encontrado");
			}finally{
				mainMenu();
			}
		}
	}

	private void continuarEmpezado() {
		try{
			tirador = this.controlador.getTirador();
			apostador = this.controlador.getApostador();
			empleado = this.controlador.getEmpleadoName();
			System.out.println("El savegame que usted cargo correspondia a un juego en curso. Desea continuar jugando\n1.Si\n2.No");
			genericInt = rd.nextInt();
			if(genericInt==1){
				ventanaJuego();
			}else{
				mainMenu();
			}
		}catch(Exception e){
			System.out.println(e+": La partida habia sido guardada pero nunca empezada");
		}
	}

	public void prepararJuego(){
		clearScreen();
		this.controlador.addCubilete();
		System.out.println("1.Crear Jugador");
		System.out.println("2.Crear Espectador");
		System.out.println("3.Crear Empleado");
		System.out.println("4.Eliminar Jugador");
		System.out.println("5.Eliminar Espectador");
		System.out.println("6.Eliminar Empleado");
		System.out.println("7.Empezar Juego");
		System.out.println("8.Volver");
		opcion = rd.nextInt();
		switch(opcion){
			case 1: crearJugador();break;
			case 2: crearEspectador();break;
			case 3: crearEmpleado();break;
			case 4: eliminarJugador();break;
			case 5: eliminarEspectador();break;
			case 6: eliminarEmpleado();break;
			case 7: empezarJuego();break;
			case 8: mainMenu();break;	
		}
		
		
	}
	private void empezarJuego() {
		empezarPrimeraRonda();
		apostadorActivo = true; //Siempre empieza el apostador
		tiradorActivo = false;
		this.controlador.addNewRonda(tirador, apostador);
		this.controlador.setEmpleado(empleado);
		ventanaJuego();
		
	}

	private void ventanaJuego() {
		if(listaJugadores.size()>1 && listaEmpleados.size()>=1){
		if(this.controlador.getUltimaRonda()==1)//1 = no es null
			this.controlador.addNewRonda(tirador, apostador);
			if(tiradorActivo)
				System.out.println("Turno del tirador: "+tirador);
			else
				System.out.println("Turno del apostador: "+apostador);
			System.out.println("Seleccione la opcion");
			System.out.println("1.Apuesta normal");
			System.out.println("2.Apuesta lateral");
			System.out.println("3.Tirar dados");
			System.out.println("4.Pasar");
			System.out.println("5.Retirarse");
			genericInt = rd.nextInt();
			switch(genericInt){
				case 1:{
					hacerApuestaNormal();
				}break;
				case 2:{
					hacerApuestaLateral();
				}break;
				case 3:{
					tirarDados();
				}break;
				case 4:{
					pasar();
				}break;
				case 5:{
					retirarse();
				}break;
			}
		}else{
			System.out.println("No se reunen los requisitos minimos para jugar:\n·2 o mas Jugadores\n·1 Empleado");
			mainMenu();
		}
	}
		

	private void pasar() {
		if(tiradorActivo){
			avanzaTirador();
		}else{
			avanzaApostador();
		}		
		ventanaJuego();
	}

	private void tirarDados() {
		boolean noSalioNada = true;
		if(sePuedeTirarDados){
			if(tiradorActivo)
				this.controlador.addTiradaToRonda(tirador); //Tira el tirador
			if(apostadorActivo)
				this.controlador.addTiradaToRonda(apostador);//Tira el apostador
			double resultado = this.controlador.getValorTirada();
			String resultString = Double.toString(resultado/10);
			String[] convertidor = resultString.split("\\.");
			String strDado1 = convertidor[0];
			String strDado2 = convertidor[1];
			if(tiradorActivo){
				System.out.println("Primer Dado: "+strDado1);
				System.out.println("Segundo Dado: "+strDado2);
			}else{
				System.out.println("Primer Dado: "+strDado1);
				System.out.println("Segundo Dado: "+strDado2);				
			}
			if(this.controlador.tiroGanador((int)resultado)){
				System.out.println("¡Felicidades ha sacado un tiro ganador!");
				noSalioNada = false;
				sePuedeTirarDados = false;
				if(tiradorActivo){ //Gano el tirador
					System.out.println("\n¡Hay un nuevo ganador! El jugador "+tirador+" gano la ronda");
					this.controlador.repartirDineroRonda(true);
					this.avanzaAmbos = true;
				}else{ //Gano el apostador
					System.out.println("\n¡Hay un nuevo ganador! El jugador "+apostador+" gano la ronda");
					this.controlador.repartirDineroRonda(false);
					this.avanzaAmbos = true;
				}
			}
			if(this.controlador.tiroPerdedor((int)resultado)){
				System.out.println("¡Ha sacado un tiro perdedor!");
				noSalioNada = false;
				sePuedeTirarDados = false;
				if(tiradorActivo){ //Perdio el tirador
					System.out.println("\n¡Hay un nuevo perdedor! El jugador "+tirador+" perdio la ronda");
					this.controlador.repartirDineroRonda(false);
					this.avanzaAmbos = true;
				}else{ //Perdio el apostador
					System.out.println("\n¡Hay un nuevo perdedor! El jugador "+apostador+" perdio la ronda");
					this.controlador.repartirDineroRonda(true);
					this.avanzaAmbos = true;
				}
			}
			if(this.controlador.tiroEspecial12((int)resultado) && tiradorActivo){
				System.out.println("\n¡Hubo un tiro especial perdedor! El jugador "+tirador+" debe elegir si desea retener los dados\n1.Si\n2.No\n");
				genericInt = rd.nextInt();
				noSalioNada = false;
				sePuedeTirarDados = false;
				this.controlador.repartirDineroRonda(false);
				if(genericInt==1){
					System.out.println("\nEl tirador decide retener los dados, el apostador debe pasar el cubilete a su derecha");
					this.avanzaAmbos = false;
					this.avanzaApostador = true;
					this.avanzaTirador = false;
				}else{
					System.out.println("\nEl tirador decide no retener los dados, el apostador sera ahora el nuevo tirador");
					this.avanzaAmbos = true;
				}
			}
			if(this.controlador.tiroEspecial65((int)resultado) && apostadorActivo){
				System.out.println("\n¡Hubo un tiro especial ganador! El jugador "+apostador+" debe elegir si desea retener los dados\n1.Si\n2.No\n");
				genericInt = rd.nextInt();
				noSalioNada = false;
				sePuedeTirarDados = false;
				this.controlador.repartirDineroRonda(false);
				if(genericInt==1){
					this.avanzaAmbos = true;
					this.avanzaTirador = false;
					this.avanzaApostador = false;
					System.out.println("\nEl apostador decide retener los dados, el tirador debe pasar el cubilete a su derecha");
				}else{
					System.out.println("\nEl tirador decide no retener los dados, el apostador y el tirador deben pasar los cubiletes a su derecha");
					this.avanzaAmbos = true;
				}
			}
			if(noSalioNada){
				this.tiradorActivo = (!tiradorActivo);
				this.apostadorActivo = (!tiradorActivo);
			}
	
		}else{
			System.out.println("En este momento no se puede tirar los dados");
			ventanaJuego();
		}
		if(!noSalioNada){
			if(this.avanzaAmbos){
				avanzaRondaNormal();
				sePuedeTirarDados=false;
			}
			if(this.avanzaTirador){
				avanzaTirador();
				sePuedeTirarDados=false;
			}
			if(this.avanzaApostador){
				avanzaApostador();
				sePuedeTirarDados=false;
			}
			this.tiradorActivo = false;
			this.apostadorActivo= true;
			verificarDineroJugadores();
			if(listaJugadores.size()==1){
				System.out.println("¡Felicitaciones! Le ha ganado a todos los jugadores de la mesa");
				terminar = true;
			}
			this.controlador.doApuestaLateral();//Nueva ronda = nueva apuesta lateral(vacia si no se apuesta)
			this.controlador.estadoJugadores();//Developer opt
		}
		if(!terminar)
			ventanaJuego();
		else
			mainMenu();
	}

	private void verificarDineroJugadores() {
		String aEliminar="";
		for(String e:listaJugadores){
			if(this.controlador.getDinero(e)<=0.90){
				aEliminar = e;
				this.controlador.eliminarJugador(e);
				System.out.println("\n Oops! El jugador "+e+" se ha quedado sin dinero, y fue eliminado del juego!\n");
				if(e.equals(tirador)){
					avanzaTirador();
				}
				if(e.equals(apostador)){
					avanzaApostador();
				}
			}
		}
		listaJugadores.remove(aEliminar);
	}

	private void hacerApuestaNormal() {
		if(tiradorActivo){
			hacerApuestaNormalTirador();
		}else{
			hacerApuestaNormalApostador();
		}
	}


	private void empezarPrimeraRonda() {
		int posGanador=0;
		int mayorValor = 0;
		double tiradaActual = 0;
		System.out.println("\nPrimera ronda..\nEl que saque los dados mas altos sera el tirador, y el de su derecha el apostador.\n¡Buena Suerte!\n");
		for(String e : listaJugadores){
			tiradaActual = this.controlador.tirarDados();
			String resultString = Double.toString(tiradaActual/10);
			String[] convertidor = resultString.split("\\.");
			String strDado1 = convertidor[0];
			String strDado2 = convertidor[1];
			int sumaTotal = Integer.valueOf(strDado1) +  Integer.valueOf(strDado2);
			if(sumaTotal>mayorValor){
				posGanador = listaJugadores.indexOf(e);
				mayorValor = sumaTotal;
				System.out.println("\n¡Nuevo puntaje alto! Hecho por: "+e+"\n1º Dado:"+strDado1+"\n2º Dado:"+strDado2+"\nUn total de: "+sumaTotal+" puntos\n-----");
				this.tirador = e;
			}else{
				System.out.println("\nTiro los dados: "+e+" \n1º Dado:"+strDado1+"\n2º Dado:"+strDado2+"\nUn total de: "+sumaTotal+" puntos\n-----");
			}
		}
		this.controlador.setPrimerTirador(listaJugadores.get(posGanador));
		apostador = this.controlador.getApostador();
		tirador = this.controlador.getTirador();
	}

	private void eliminarEmpleado() {
		System.out.println("Eliminar Empleado:");
		int i = 0;
		for(String e: listaEmpleados){
			System.out.println(i+". "+e);
			i++;
		}
		System.out.println("Ingrese el numero de empleado a eliminar o -1 para salir");
		genericInt = rd.nextInt();
		if(genericInt>=0){
			listaEmpleados.remove(genericInt);	
			prepararJuego();
		}else{
			prepararJuego();
		}
	}

	private void eliminarEspectador() {
		clearScreen();
		System.out.println("Eliminar Espectador:");
		int i = 0;
		for(String e: listaEspectadores){
			System.out.println(i+". "+e);
			i++;
		}
		System.out.println("Ingrese el numero de espectador a eliminar(-1 para volver):");
		genericInt = rd.nextInt();
		if(genericInt>=0){
			listaEspectadores.remove(genericInt);
			prepararJuego();
		}else{
			prepararJuego();
		}
	}

	private void eliminarJugador() {
		clearScreen();
		System.out.println("Eliminar Jugador:");
		int i = 0;
		for(String e: listaJugadores){
			System.out.println(i+". "+e);
			i++;
		}
		System.out.println("Ingrese el numero de jugador a eliminar(-1 para volver):");
		genericInt = rd.nextInt();
		if(genericInt>=0){
			listaJugadores.remove(genericInt);
			prepararJuego();
		}else{
			prepararJuego();
		}
	}

	private void crearEmpleado() {
		clearScreen();
		System.out.println("Creacion de empleado");
		System.out.println("Ingrese el nombre:");
		genericNombre = rd.next();
		if(!listaEmpleados.contains(genericNombre)){
			this.controlador.crearEmpleado(genericNombre);
			this.listaEmpleados.add(genericNombre);
			prepararJuego();
		}else{
			System.out.println("Nombre en uso. Ingrese otra vez");
		}
	}

	private void crearEspectador() {
		clearScreen();
		System.out.println("Creacion de espectador");
		System.out.println("Ingrese el nombre:");
		genericNombre = rd.next();
		System.out.println("Ingrese las iniciales:");
		genericIniciales = rd.next();
		System.out.println("Ingrese Dinero:");
		genericDinero = rd.nextDouble();
		if(!listaEspectadores.contains(genericNombre)&&!listaJugadores.contains(genericNombre)){
			this.controlador.crearEspectador(genericNombre, genericIniciales, genericDinero);
			this.listaEspectadores.add(genericNombre);
			prepararJuego();
		}else{
			System.out.println("Nombre en uso. Ingrese denuevo porfavor");
			crearEspectador();
		}
	}

	private void crearJugador() {
		clearScreen();
		System.out.println("Creacion de jugador");
		System.out.println("Ingrese el nombre:");
		genericNombre = rd.next();
		System.out.println("Ingrese las iniciales:");
		genericIniciales = rd.next();
		System.out.println("Ingrese Dinero:");
		genericDinero = rd.nextDouble();
		if(!listaEspectadores.contains(genericNombre)&&!listaJugadores.contains(genericNombre)){
			this.controlador.crearJugador(genericNombre, genericIniciales, genericDinero);
			this.listaJugadores.add(genericNombre);
			prepararJuego();
		}else{
			System.out.println("Nombre en uso. Ingrese denuevo porfavor");
			crearJugador();
		}
	}

	public void opciones(){
		System.out.println("Opciones:");
		System.out.println("¿Desea jugar modo casino? Y/N");
		String c = rd.next();
		if(c.toLowerCase().equals("y")){
			this.modoNormal = false;
			if(listaEmpleados.size()>0){
				for(String e : listaEmpleados){
					System.out.println(listaEmpleados.indexOf(e)+". "+e);
				}
				System.out.println("Seleccione el empleado:");
				genericInt = rd.nextInt();
				this.empleado = listaEmpleados.get(genericInt);
				prepararJuego();
			}else{
				System.out.println("Aun no ha cargado empleados.\nPorfavor primero carge un empleado");
				crearEmpleado();
				opciones();
			}
		}else{
			if(c.toLowerCase().equals("n")){
				this.modoNormal = true;
				prepararJuego();
			}else{
				System.out.println("Ha ingresado mal la opcion.");
				System.out.println(c);
				opciones();
			}
		}		
	}
	
	public void top5(){
		System.out.println("TOP 5");
		System.out.println("Jugadores:");
		for(String e : this.controlador.getTop5Jugadores())
			System.out.println(e);
		System.out.println("Espectadores:");
		for(String e : this.controlador.getTop5Espectadores())
			System.out.println(e);
		System.out.println("Presione cualquier tecla para volver...");
		genericNombre = rd.next();
		mainMenu();
	}

	@Override
	public void update(Observable observado, Object o) {
		String[] splitter;
		if(o instanceof String){
			splitter = ((String) o).split("\\ ");
			for(String e : splitter){
				if(e != null){
					switch(e){
						case ("tirador"):{
							this.tirador = splitter[1];
						}break;
						case ("apostador"):{
							this.apostador = splitter[1];
						}break;
						case("error"):{
							switch(splitter[1]){
								case("apostador=tirador"):{
									System.out.println("El apostador no puede ser tirador a la vez.");
									System.out.println("\nAlgo salio mal..\nPasando turno del apostador...");
									avanzaApostador();
								}break;
							}
						}break;
						case("carga"):{
							if(splitter[1].equals("exito")){
								listaJugadores = this.controlador.getAllJugadores();
								listaEspectadores = this.controlador.getAllEspectadores();
								listaEmpleados = this.controlador.getAllEmpleados();
							}
						}break;
					}
				}
			}
		}
	}


	private void avanzaApostador() {	
		int pos=0;
		int posTirador=0;
		int posApostador=0;
		//Reconoce las posiciones actuales de tirador y apostador
		if(listaJugadores.size()>1){	
			for(String e: listaJugadores){
				if(e.equals(tirador)){
					posTirador=pos;
				}
				if(e.equals(apostador)){
					posApostador=pos;
				}
				pos++;
			}
			if(posApostador+1>listaJugadores.size()-1){
				posApostador = 0;
				if(posTirador==0){
					posApostador=posTirador+1;
					this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador));
				}else{
					this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador));
				}
			}else{
				if((posApostador+1)==(posTirador)){
					posApostador++;
					if(posApostador+1>listaJugadores.size()-1){
						posApostador=0;
						this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador));
					}else{
						this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador+1));
					}
				}else{
					this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador+1));					
				}
			}
			//Luego de ejecutar cualquiera de estos eventos debo re-inicializar los booleanos
			this.avanzaAmbos = false;
			this.avanzaApostador = false;
			this.avanzaTirador = false;
			this.apostador = this.controlador.getApostador();
			this.tirador = this.controlador.getTirador();
		}else{
			System.out.println("¡Ha quedado solo en la mesa, felicidades Ganador!");
		}
	}

	private void avanzaTirador() {
		int pos=0;
		int posTirador=0;
		int posApostador=0;
		//Reconoce las posiciones actuales de tirador y apostador
		for(String e: listaJugadores){
			if(e.equals(tirador)){
				posTirador=pos;
			}
			if(e.equals(apostador)){
				posApostador=pos;
			}
			pos++;
		}
		if(posTirador+1>listaJugadores.size()-1){
			posTirador = 0;
			if(posApostador==0){
				posTirador=posApostador+1;
				this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador));
			}else{
				this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador));
			}
		}else{
			if((posTirador+1)==(posApostador)){
				posTirador++;
				if(posTirador+1>listaJugadores.size()-1){
					posTirador=0;
					this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador));
				}else{
					this.controlador.addNewRonda(listaJugadores.get(posTirador+1), listaJugadores.get(posApostador));
				}
			}else{
				this.controlador.addNewRonda(listaJugadores.get(posTirador+1), listaJugadores.get(posApostador));
			}
			
		}
		//Luego de ejecutar cualquiera de estos eventos debo re-inicializar los booleanos
		this.avanzaAmbos = false;
		this.avanzaApostador = false;
		this.avanzaTirador = false;
		this.apostador = this.controlador.getApostador();
		this.tirador = this.controlador.getTirador();
	}

	private void avanzaRondaNormal() {
		int pos=0;
		int posTirador=0;
		int posApostador=0;
		for(String e: listaJugadores){
			if(e.equals(tirador)){
				posTirador=pos;
			}
			if(e.equals(apostador)){
				posApostador=pos;
			}
			pos++;
		}
		if((posTirador+1>listaJugadores.size()-1) && !(posApostador+1>listaJugadores.size()-1)){
			posTirador=0;
			this.controlador.addNewRonda(listaJugadores.get(posTirador), listaJugadores.get(posApostador+1));
		}else{
			if(!(posTirador+1>listaJugadores.size()-1) && (posApostador+1>listaJugadores.size()-1)){
			posApostador=0;
			this.controlador.addNewRonda(listaJugadores.get(posTirador+1), listaJugadores.get(posApostador));
			}else{
				if(!(posTirador+1>listaJugadores.size()-1) && !(posApostador+1>listaJugadores.size()-1)){
				this.controlador.addNewRonda(listaJugadores.get(posTirador+1), listaJugadores.get(posApostador+1));
				}
			}
		}
		this.avanzaAmbos = false;
		this.apostador = this.controlador.getApostador();
		this.tirador = this.controlador.getTirador();
	}
	
	private void retirarse() {
		if(this.apostadorActivo){
			String aBorrar = apostador;
			this.controlador.retirarJugador(apostador);
			System.out.println("\nEl jugador "+apostador+" decidio retirarse del juego.\nAvanzando un lugar en la mesa");
			if(this.controlador.getUltimaApuestaLateral()==1)//1 = hay alguna apuesta lateral, esta resticcion esta por el caso de que se retire ni bien empieza el juego.
				this.controlador.devolverDineroATodosLateral();//Devuelve si habia apuestas laterales
			if(listaJugadores.size()<=2){
				System.out.println("¡Ha quedado solo en la mesa, felicidades Ganador!");
				mainMenu();
			}else{
				avanzaApostador();
				listaJugadores.remove(aBorrar);//Primero avanzo y despues borro para evitar inconsistencias
				ventanaJuego();
			}
		}
		if(this.tiradorActivo){
			String aBorrar = tirador;
			this.controlador.retirarJugador(tirador);
			System.out.println("\nEl jugador "+tirador+" decidio retirarse del juego.\nAvanzando un lugar en la mesa");
			if(this.controlador.getUltimaApuestaLateral()==1)//1 = hay alguna apuesta lateral, esta resticcion esta por el caso de que se retire ni bien empieza el juego.
				this.controlador.devolverDineroATodosLateral();//Devuelve si habia apuestas laterales
			this.controlador.devolverRestante();//Devuelve si el apostador ya habia hecho una apuesta
			if(listaJugadores.size()<=2){
				System.out.println("¡Ha quedado solo en la mesa, felicidades Ganador!");
				mainMenu();
			}else{
				avanzaTirador();
				listaJugadores.remove(aBorrar);
				ventanaJuego();
			}
		}
		this.controlador.estadoJugadores();
	}	

	private void hacerApuestaNormalApostador(){
		System.out.println("Ingrese dinero a apostar:");
		genericDinero = rd.nextDouble();
		if(genericDinero>=this.controlador.getDinero(apostador)){
			System.out.println("Dinero disponible: "+this.controlador.getDinero(apostador));
			System.out.println("Apuesta: "+genericDinero);
			System.out.println("No tiene el dinero suficiente para realizar la apuesta");
			hacerApuestaNormalApostador();
		}else{
			this.controlador.doApuestaNormal(genericDinero);
		}
		System.out.println("El apostador realizo correctamente la apuesta\nPasando el turno al tirador...");
		apostadorActivo = false;
		tiradorActivo = true;
		ventanaJuego();
	}


	private void hacerApuestaNormalTirador() {
		System.out.println("Ingrese dinero a apostar:");
		genericDinero = rd.nextDouble();
		double dineroMax = 0;
		if(genericDinero>=this.controlador.getDinero(tirador)){
			System.out.println("Dinero disponible: "+this.controlador.getDinero(apostador));
			System.out.println("Apuesta: "+genericDinero);
			System.out.println("No tiene el dinero suficiente para realizar la apuesta");
			hacerApuestaNormalTirador();
		}else{
			if(genericDinero > this.controlador.getDineroEnJuegoApostador()){
				System.out.println("No puedes apostar mas dinero que el apostador, espera tu turno para apostar mas cantidad.");
				hacerApuestaNormalTirador();
			}
			this.controlador.llenarApuestaNormal(tirador, genericDinero);
			if(this.controlador.apuestaNormalEquilibrada()&&this.controlador.apuestaLateralEquilibrada()){
				System.out.println("\nEl tirador "+tirador+" ha realizado exitosamente una apuesta de:"+genericDinero+".\n¡A tirar los dados!");				
				sePuedeTirarDados = true;
				ventanaJuego();
			}else{
				System.out.println("\nEl tirador "+tirador+" ha realizado exitosamente una apuesta de: "+genericDinero+".\nComo no alcanza a cubrir la apuesta "
						+ "del Apostador se tomaran apuestas de otros jugadores\nque deseen apostar a favor del Tirador");
				int i = 0;
				String apuesta;
				dineroMax = this.controlador.getDineroEnJuegoApostador()-Double.valueOf(genericDinero);
				while(listaJugadores.size()>i && dineroMax>0){
					String e = listaJugadores.get(i);
					if(!e.equals(apostador) && !e.equals(tirador)){
						System.out.println("Jugador: "+e+" dinero disponible: "+this.controlador.getDinero(e)+" (Apuesta Max posible:"+String.valueOf(dineroMax)+")");
						apuesta = rd.next();
						if(apuesta != null && this.controlador.getDinero(e)>=Double.valueOf(apuesta)){//Me aseguro de que tenga el dinero para pagar la apuesta
							if(Double.valueOf(apuesta)>dineroMax){
								System.out.println("Error: Su apuesta supero el limite.");
							}else{
								dineroMax -= Double.valueOf(apuesta);
								this.controlador.llenarApuestaNormal(e, Double.valueOf(apuesta));
								System.out.println("\nEl jugador "+e+" aposto: "+apuesta+" a favor del tirador.\nAun por cubrir: "+dineroMax);
							}
						}else{
							System.out.println("\nEl jugador "+e+" paso o no posee el dinero suficiente realizar la apuesta.\nAun por cubrir: "+dineroMax);
						}
					}
					i++;
				}
				if(this.controlador.apuestaNormalEquilibrada()&&this.controlador.apuestaLateralEquilibrada()){
					System.out.println("\n\n¡Todo listo para tirar los dados!");
					this.sePuedeTirarDados = true;
					ventanaJuego();
				}else{
					System.out.println("\nAdvertencia: No se llego a cubrir "+dineroMax+" de la suma impuesta por el apostador.\nDevolviendo el restante al apostador...");
					this.controlador.devolverRestante();
					System.out.println("\n\n¡Todo listo para tirar los dados!");
					this.sePuedeTirarDados = true;
					ventanaJuego();
				}
			}
			//VENTANA APUESTA NORMAL PARA LOS JUGADORES QUE DESEEN PARTICIPAR DE LA JUGADA ACTUAL
			if(this.controlador.apuestaNormalEquilibrada()&&this.controlador.apuestaLateralEquilibrada()){
				System.out.println("\n\n¡Todo listo para tirar los dados!");
				sePuedeTirarDados = true;
			}else{
				System.out.println("\nAdvertencia: No se llego a cubrir "+dineroMax+" de la suma impuesta por el apostador.\nDevolviendo el restante al apostador...");
				this.controlador.devolverRestante();
				System.out.println("\n\n¡Todo listo para tirar los dados!");
				sePuedeTirarDados = true;
			}
			ventanaJuego();
		}
	}
	
	private void hacerApuestaLateral() {
		String persona;
		boolean aFavorTirador;
		double dineroPersona;
		double apuesta;
		System.out.println("Ingrese quien desea hacer la apuesta");
		System.out.println("1.Jugador");
		System.out.println("2.Espectador");
		System.out.println("3.Volver");
		genericInt = rd.nextInt();
		if(genericInt == 1){
			for(String e:listaJugadores){
				System.out.println(listaJugadores.indexOf(e)+". "+e);
			}
			System.out.println("Seleccione el jugador:");
			genericInt = rd.nextInt();
			persona = listaJugadores.get(genericInt);
			if(persona == tirador || persona == apostador){
				System.out.println("El tirador/apostador no puede iniciar una apuesta lateral");
			}else{
				System.out.println("¿A favor de quien desea apostar?");
				System.out.println("1.Tirador");
				System.out.println("2.Apostador");
				genericInt = rd.nextInt();
				if(genericInt == 1){
					aFavorTirador = true;
					System.out.println("¿Cuanto dinero desea apostar?");
					apuesta = rd.nextDouble();
					dineroPersona = this.controlador.getDinero(persona);
					if(dineroPersona<apuesta){
						System.out.println("No posee el dinero suficiente para realizar la apuesta.");
					}else{
						this.controlador.llenarApuestaLateral(persona, apuesta, aFavorTirador);
						System.out.println("Apuesta realizada correctamente.");
					}
				}
				if(genericInt == 2){
					aFavorTirador = false;
					System.out.println("¿Cuanto dinero desea apostar?");
					apuesta = rd.nextDouble();
					dineroPersona = this.controlador.getDinero(persona);
					if(dineroPersona<apuesta){
						System.out.println("No posee el dinero suficiente para realizar la apuesta.");
					}else{
						this.controlador.llenarApuestaLateral(persona, apuesta, aFavorTirador);
						System.out.println("Apuesta realizada correctamente.");
					}
				}
				if(genericInt != 1||genericInt!=2){
					System.out.println("Algo salio mal...");
				}
			}
		}
		if(genericInt == 2){
			for(String e:listaEspectadores){
				System.out.println(listaEspectadores.indexOf(e)+". "+e);
			}
			System.out.println("Seleccione el espectador:");
			genericInt = rd.nextInt();
			persona = listaEspectadores.get(genericInt);
			if(persona == tirador || persona == apostador){
				System.out.println("El tirador/apostador no puede iniciar una apuesta lateral");
			}else{
				System.out.println("¿A favor de quien desea apostar?");
				System.out.println("1.Tirador");
				System.out.println("2.Apostador");
				genericInt = rd.nextInt();
				if(genericInt == 1){
					aFavorTirador = true;
					System.out.println("¿Cuanto dinero desea apostar?");
					apuesta = rd.nextDouble();
					dineroPersona = this.controlador.getDinero(persona);
					if(dineroPersona<apuesta){
						System.out.println("No posee el dinero suficiente para realizar la apuesta.");
					}else{
						this.controlador.llenarApuestaLateral(persona, apuesta, aFavorTirador);
						System.out.println("Apuesta realizada correctamente.");
					}
				}
				if(genericInt == 2){
					aFavorTirador = false;
					System.out.println("¿Cuanto dinero desea apostar?");
					apuesta = rd.nextDouble();
					dineroPersona = this.controlador.getDinero(persona);
					if(dineroPersona<apuesta){
						System.out.println("No posee el dinero suficiente para realizar la apuesta.");
					}else{
						this.controlador.llenarApuestaLateral(persona, apuesta, aFavorTirador);
						System.out.println("Apuesta realizada correctamente.");
					}
				}
			}
			if(genericInt != 1||genericInt!=2){
				System.out.println("Algo salio mal...");
			}
		}
		if(genericInt == 3){
			ventanaJuego();
		}
	}
		
/*		
	@SuppressWarnings("static-access")
	private void tirarDados() {

	}
	*/
}

