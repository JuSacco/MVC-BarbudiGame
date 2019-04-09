package vista.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controlador.IControlador;

public class Principal extends JFrame implements ActionListener, Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Contenedor pantalla principal
	private Container contenedor;
	private JButton crearJugador;
	private JButton eliminarJugador;
	private JButton crearEmpleado;
	private JButton eliminarEmpleado;
	private JButton crearEspectador;
	private JButton eliminarEspectador;
	private JButton iniciarJuego;
	private JButton cargarEstado;
	private JButton guardarEstado;
	private JButton listarEmpleados;
	private JButton listarJugadores;
	private JButton listarEspectadores;
	private JButton top5;
	private JButton volverTop5;
	private JButton salir;
	private Imagen imagen;
	
	
	//Contenedor Juego
	private Container contenedorJuego;
	private JButton apuestaNormal;
	private JFrame ventanaApuesta;
	private JFrame frameJuego;
	private JFrame frameTop5;
	//Lateral Frame
	private JFrame ventanaApuestaLateral;
	private JFrame ventanaEstadoEspectadores;
	private JButton aceptarApuestaLateral;
	private JComboBox<String> comboEspectadores;
	private JComboBox<String> comboJugadores;
	private JRadioButton aFavorTirador;
	private JRadioButton aFavorApostador;
	private JSpinner spinnerLateral;
	private JRadioButton esEspectador;
	private JRadioButton esJugador;
	
	private JButton aceptarApuestaNormalApost;
	private JButton aceptarApuestaNormalTirad;
	private JButton apuestaLateral;
	private JButton pasar;
	private JButton nuevaVuelta;
	private JButton tirarDados;
	private JButton retirarseDelJuego;
	private JButton salirJuego;
	private JButton volver;
	private JButton continuar;
	JButton okEstadoEspectadores;
	private JTextArea logJuego;
	private JScrollPane scroll;
	private JLabel dineroAApostar;
	private JLabel nombreTirador;
	private JLabel rolTirador;
	private JLabel dineroDisponibleTirador;
	private JLabel dado1Tirador;
	private JLabel dado2Tirador;
	private JLabel nombreApostador;
	private JLabel rolApostador;
	private JLabel dineroDisponibleApostador;
	private JLabel dado1Apostador;
	private JLabel dado2Apostador;
	private JLabel apostadorActivo;
	private JLabel tiradorActivo;
	
	//Atributos varios
	private JOptionPane mensaje;
	private ArrayList<String> listaJugadores;
	private ArrayList<String> listaEspectadores;
	private ArrayList<String> listaEmpleados;
	private String tirador;
	private String apostador;
	private IControlador controlador;
	private ExploradorDeArchivos explorador;
	private boolean avanzaTirador;
	private boolean avanzaApostador;
	private boolean avanzaAmbos;
	
	//Ventana Opciones 
	private JFrame ventanaOpciones;
	private String empleado;
	private JButton okOpciones;
	JRadioButton modoCasino;
	JRadioButton modoNormal;
	private JComboBox<String> jListEmpleados;
	
	// Font a usar
	Font fuente = new Font("Monospaced", Font.BOLD, 12);
	
	public Principal(String titulo, IControlador controlador){
		setTitle(titulo);
		setSize(850, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		this.controlador = controlador;
		BorderLayout bl = new BorderLayout();
		bl.setHgap(10);
		bl.setVgap(10);
		setLayout(bl);
		contenedor = this.getContentPane();
		listaJugadores = new ArrayList<String>();
		listaEspectadores = new ArrayList<String>();
		listaEmpleados = new ArrayList<String>();
		explorador = new ExploradorDeArchivos();
		this.iniciarComponentes();
	}
	
	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 *   	///////////////////////////////-----INICIADORES DE COMPONENTES-----///////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		iniciarComponentes(): Main menu
 * 		iniciarComponentesJuego: Juego
 */
	/**
	 *  Incia componentes de la ventana principal
	 */
	private void iniciarComponentes(){
		
		// Botones
		crearJugador = this.setBotones(crearJugador, "Nuevo Jugador", fuente);
		eliminarJugador = this.setBotones(eliminarJugador, "Borrar Jugador", fuente);
		crearEmpleado = this.setBotones(crearEmpleado, "Nuevo Empleado", fuente);
		eliminarEmpleado = this.setBotones(eliminarEmpleado, "Borrar Empleado", fuente);
		crearEspectador = this.setBotones(crearEspectador, "Nuevo Espectador", fuente);
		eliminarEspectador = this.setBotones(eliminarEspectador, "Borrar Espectador", fuente);
		iniciarJuego = this.setBotones(iniciarJuego, "Iniciar", fuente);
		continuar = this.setBotones(continuar, "Continuar...", fuente);
		cargarEstado = this.setBotones(cargarEstado, "Cargar partida", fuente);
		guardarEstado = this.setBotones(guardarEstado, "Guardar Partida", fuente);
		listarEmpleados = this.setBotones(listarEmpleados, "Mostrar Empleados", fuente);
		salir = this.setBotones(salir, "Salir", fuente);
		listarJugadores = this.setBotones(listarJugadores, "Mostrar Jugadores", fuente);
		listarEspectadores = this.setBotones(listarEspectadores, "Mostrar Espectadores", fuente);
		top5 = this.setBotones(top5,"TOP 5",fuente);
		
		// GridLayout:7 filas;1 columna
		GridLayout layoutWest = new GridLayout(7, 1);
		layoutWest.setVgap(15); // Espacio entre filas
		
		// Panel Oeste
		JPanel panelWest = new JPanel();
		panelWest.setBorder(BorderFactory.createLineBorder(Color.gray));
		contenedor.add(panelWest, BorderLayout.WEST);
		panelWest.setLayout(layoutWest);
		panelWest.add(crearJugador);
		panelWest.add(eliminarJugador);
		panelWest.add(crearEspectador);
		panelWest.add(eliminarEspectador);
		panelWest.add(crearEmpleado);
		panelWest.add(eliminarEmpleado);
		panelWest.add(iniciarJuego);
		
		// GridLayout:4 filas;1 columna
		GridLayout layoutEast = new GridLayout(4, 1);
		layoutEast.setVgap(75); // Espacio entre filas
		
		// Panel Este
		JPanel panelEast = new JPanel();
		panelEast.setBorder(BorderFactory.createLineBorder(Color.gray));
		contenedor.add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(layoutEast);
		panelEast.add(listarJugadores);
		panelEast.add(listarEspectadores);
		panelEast.add(listarEmpleados);
		panelEast.add(top5);
		
		// Panel Sur + FlowLayout
		JPanel panelSouth = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setHgap(50); //Espacio entre columnas
		continuar.setEnabled(false);
		panelSouth.setLayout(fl);
		panelSouth.setBorder(BorderFactory.createLineBorder(Color.gray));
		this.contenedor.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.add(continuar);
		panelSouth.add(guardarEstado);
		panelSouth.add(cargarEstado);
		panelSouth.add(salir);
		
		//Panel Central
		JPanel panelCenter = new JPanel();
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, 1, 0, 0));
		imagen = new Imagen("img/gambling2.jpg");
		center.add(imagen);
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(center);
		this.contenedor.add(panelCenter, BorderLayout.CENTER);
	}
	/*
	 * iniciarComponentesJuego: Inicia los componentes del juego.
	 */
	private void iniciarComponentesJuego(){
		frameJuego = new JFrame("Jugando Barbudi");
		frameJuego.setSize(850, 550); 
		frameJuego.setLocationRelativeTo(null); 
		frameJuego.setResizable(false);
		this.contenedorJuego = frameJuego;
		
		// Font a usar
		Font fuente = new Font("Monospaced", Font.BOLD, 12);
		Font fuenteActivo = new Font("Monospaced", Font.BOLD,16);
		
		
		// Botones
		apuestaNormal = this.setBotones(apuestaNormal, "Apuesta Normal", fuente);
		apuestaLateral = this.setBotones(apuestaLateral, "Apuesta Lateral", fuente);
		pasar = this.setBotones(pasar, "Pasar", fuente);
		nuevaVuelta = this.setBotones(nuevaVuelta, "Pasar Cubiletes", fuente);
		tirarDados = this.setBotones(tirarDados, "Tirar Dados", fuente);
		retirarseDelJuego = this.setBotones(retirarseDelJuego, "Retirarse del juego", fuente);
		salirJuego = this.setBotones(salirJuego, "Salir", fuente);
		volver = this.setBotones(volver, "Volver a menu principal", fuente);
		
		//Labels
		apostadorActivo = new JLabel("ACTIVO");
		tiradorActivo = new JLabel("ACTIVO");
		nombreTirador = new JLabel("Turno de: ");;
		rolTirador = new JLabel("ROL: Tirador");
		dineroDisponibleTirador = new JLabel("Dinero disponible:");
		dado1Tirador= new JLabel("Primer Dado: ");
		dado2Tirador= new JLabel("Segundo Dado: ");
		nombreApostador = new JLabel("Turno de: ");;
		rolApostador = new JLabel("ROL: Apostador");
		dineroDisponibleApostador = new JLabel("Dinero disponible:");
		dado1Apostador= new JLabel("Primer Dado: ");
		dado2Apostador= new JLabel("Segundo Dado: ");
		
		//Fonts Config
		nombreTirador.setFont(fuente);
		rolTirador.setFont(fuente);
		dineroDisponibleTirador.setFont(fuente);
		dado1Tirador.setFont(fuente);
		dado2Tirador.setFont(fuente);
		nombreApostador.setFont(fuente);
		rolApostador.setFont(fuente);
		dineroDisponibleApostador.setFont(fuente);
		dado1Apostador.setFont(fuente);
		dado2Apostador.setFont(fuente);
		tiradorActivo.setFont(fuenteActivo);
		apostadorActivo.setFont(fuenteActivo);
		
		//Activos = False hasta que se este jugando
		tiradorActivo.setVisible(false);
		apostadorActivo.setVisible(false);
		nuevaVuelta.setVisible(false);
		
		tiradorActivo.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		nombreTirador.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		rolTirador.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		dineroDisponibleTirador.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		dado1Tirador.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		dado2Tirador.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		
		// Panel Oeste
		GridLayout layoutWest = new GridLayout(7, 1);
		layoutWest.setVgap(15);
		JPanel panelWestGameMode = new JPanel();
		panelWestGameMode.setBorder(BorderFactory.createLineBorder(Color.gray));
		this.contenedorJuego.add(panelWestGameMode, BorderLayout.WEST);
		panelWestGameMode.setLayout(layoutWest);
		panelWestGameMode.add(apuestaNormal);
		panelWestGameMode.add(apuestaLateral);
		panelWestGameMode.add(pasar);
		panelWestGameMode.add(tirarDados);
		panelWestGameMode.add(retirarseDelJuego);
		panelWestGameMode.add(salirJuego);
		panelWestGameMode.add(volver);
		
		// Panel Sur + FlowLayout
		JPanel panelSouth = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setHgap(50); //Espacio entre columnas
		panelSouth.setLayout(fl);
		panelSouth.setBorder(BorderFactory.createLineBorder(Color.gray));
		logJuego = new JTextArea(15,75);
		logJuego.setEditable(false);
		scroll = new JScrollPane(logJuego);
		this.contenedorJuego.add(scroll, BorderLayout.SOUTH);
		
		//Panel Central
		JPanel panelCenter = new JPanel();
		JPanel centerIzq = new JPanel();
		JPanel centerDer = new JPanel();
		panelCenter.setLayout(new GridLayout(1,2));
		centerIzq.setLayout(new GridLayout(7, 1));
		centerDer.setLayout(new GridLayout(7, 1));
		
		//Izquierda
		centerIzq.add(tiradorActivo);
		centerIzq.add(nombreTirador);
		centerIzq.add(rolTirador);
		centerIzq.add(dineroDisponibleTirador);
		centerIzq.add(dado1Tirador);
		centerIzq.add(dado2Tirador);
		centerIzq.add(nuevaVuelta);
		
		//Derecha
		centerDer.add(apostadorActivo);
		centerDer.add(nombreApostador);
		centerDer.add(rolApostador);
		centerDer.add(dineroDisponibleApostador);
		centerDer.add(dado1Apostador);
		centerDer.add(dado2Apostador);
		panelCenter.add(centerIzq, 0);
		panelCenter.add(centerDer, 1);
		this.contenedorJuego.add(panelCenter, BorderLayout.CENTER);
	}

/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 *   	////////////////////////////////////////-----METODO UPDATE-----///////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 * 		update: Traigo las actualizaciones del modelo. Viene en formato String separado con espacios, se hace un 
 * 		split y se actua segun corresponda.
 * 		Posibles parametros:
 * 			"tirador nombre": Avisa que hay un tirador nuevo.
 * 			"apostador nombre": Avisa que hay un apostador nuevo.
 * 			"error apostador=tirador": Es utilizado para prevenir inconsistencias.
 * 			"carga exito": Cargo una partida desde el Explorador de Archivos con exito.
 */

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
							actualizarLabelTirador();
						}break;
						case ("apostador"):{
							this.apostador = splitter[1];
							actualizarLabelApostador();
						}break;
						case("error"):{
							switch(splitter[1]){
								case("apostador=tirador"):{
									imprimir("El apostador no puede ser tirador a la vez.");
									logJuego.setText(logJuego.getText()+"\nAlgo salio mal..\nPasando turno del apostador...");
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
	
/**
 * setBotones: Establece los botones y le agrego un Listener para cuando ocurren eventos con estos botones.
 * @param boton: Boton a instanciar
 * @param texto : Texto dentro del boton
 * @param fuente : Fuente a utilizar
 */
	private JButton setBotones(JButton boton, String texto, Font fuente){
		boton = new JButton(texto);
		boton.setFont(fuente);
		boton.addActionListener(this);
		return boton;
	}
	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 *   	////////////////////////////////////////-----ACTUALIZADORES-----//////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////	
 * 		actualizarLblJugadores: Llama a los dos siguientes metodos a la vez.
 * 			·actualizarLabelTirador: Actualiza las labels dentro del juego.
 * 			·actualizarLabelApostador: Actualiza las labels dentro del juego.
 * 		deshabilitarBotonesAfterPlay:Deshabilita todos los botones de juego.
 * 		habilitarBotonesApuesta: Habilita botones de apuesta y deshabilita para tirar.
 * 		habilitarBotonesTirar: Habilita botones para tiro de dados y deshabilita para apostar.
 * 		
 */
	private void actualizarLblJugadores(){
		this.actualizarLabelApostador();
		this.actualizarLabelTirador();
	}
	private void actualizarLabelTirador() {
		this.nombreTirador.setText("Nombre :"+this.tirador);
		this.dineroDisponibleTirador.setText("Dinero disponible: "+this.controlador.getDinero(tirador));
		this.dado1Tirador.setText("Primer Dado: ");
		this.dado2Tirador.setText("Segundo Dado: ");
	}
	
	private void actualizarLabelApostador() {
		this.nombreApostador.setText("Nombre :"+this.apostador);
		this.dineroDisponibleApostador.setText("Dinero disponible: "+this.controlador.getDinero(apostador));
		this.dado1Apostador.setText("Primer Dado: ");
		this.dado2Apostador.setText("Segundo Dado: ");
	}	
	/**
	 * deshabilitarBotonesAfterPlay
	 */
	private void deshabilitarBotonesAfterPlay(){
		this.apuestaLateral.setEnabled(false);
		this.apuestaNormal.setEnabled(false);
		this.pasar.setEnabled(false);
		this.tirarDados.setEnabled(false);
		this.retirarseDelJuego.setEnabled(false);
	}
	/**
	 * habilitarBotonesApuesta
	 */
	private void habilitarBotonesApuesta(){
		this.apuestaLateral.setEnabled(true);
		this.apuestaNormal.setEnabled(true);
		this.pasar.setEnabled(true);
		this.tirarDados.setEnabled(false);
		this.retirarseDelJuego.setEnabled(true);
	}
	/**
	 * habilitarBotonesTirar
	 */
	private void habilitarBotonesTirar(){
		this.apuestaLateral.setEnabled(false);
		this.apuestaNormal.setEnabled(false);
		this.pasar.setEnabled(false);
		this.tirarDados.setEnabled(true);
		this.retirarseDelJuego.setEnabled(false);
	}

/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 *   	///////////////////////////////////////////-----EVENTOS-----//////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////	
 *		actionPerformed(ActionEvent evento): Ocurre un evento y ejecuto una accion.
 *
 */

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent evento) {
		
		// Presiono crear jugador
		if(evento.getSource() == this.crearJugador)
			this.crearJugador();
		
		// Presiono eliminar jugador
		if(evento.getSource() == this.eliminarJugador)
			this.eliminarJugador();			
		
		// Presiono crear espectador
		if(evento.getSource() == this.crearEspectador)
			this.crearEspectador();
		
		// Presiono eliminar jugador
		if(evento.getSource() == this.eliminarEspectador)
			this.eliminarEspectador();
		
		// Presiono crear empleado
		if(evento.getSource() == this.crearEmpleado)
			this.agregarEmpleado();
		
		// Presiono eliminar jugador
		if(evento.getSource() == this.eliminarEmpleado)
			this.eliminarEmpleado();
		
		// Presiono iniciar juego
		if(evento.getSource() == iniciarJuego){
			this.opcionesAntesEmpezar();
		}
		
		// Presiono listar jugadores
		if(evento.getSource() == listarJugadores)
			this.listarJugadores();
				
		// Presiono listar espectadores
		if(evento.getSource() == listarEspectadores)
			this.listarEspectadores();
		
		// Presiono listar empleados
		if(evento.getSource() == listarEmpleados)
			this.listarEmpleados();
		
		// Presiono cargar estado
		if(evento.getSource() == this.cargarEstado){
			explorador.abrirArchivo();
			String ruta = explorador.getArchivo();
			System.out.println(ruta);
			if(!ruta.equals("")){
				this.controlador.cargarEstado(ruta);
				continuarEmpezado();
			}
		}
		
		// Presiono guardar estado
		if(evento.getSource() == this.guardarEstado){
			explorador.guardarArchivo();
			String ruta = explorador.getArchivo();
			if(ruta != ""){
				try {
					this.controlador.guardarBarbudi(ruta);
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		}
		
		//Presiono el top 5
		if(evento.getSource() == this.top5){
			ventanaTop5();
		}
		// Presiono salir
		if(evento.getSource() == this.salir)
			System.exit(ABORT);
		
		//Presiono volver del top5
		if(evento.getSource() == volverTop5){
			this.frameTop5.dispose();
		}
		
		//Presiono Listo! en la ventana de opciones
		if(evento.getSource() == this.okOpciones){
			if(modoCasino.isSelected()){
				this.empleado = (String) jListEmpleados.getSelectedItem();
				this.controlador.setEmpleado(this.empleado);
			}else{
				this.empleado = "Sin Empleado";
				this.controlador.setEmpleado(this.empleado);
			}
			this.iniciarJuego();
			this.ventanaOpciones.dispose();
		}
		////////////////////////////////////////////////////---EVENTOS DURANTE EL JUEGO---//////////////////////////////////////////////

		// Presiono salir en el juego
		if(evento.getSource() == this.salirJuego){
			int resultado = mensaje.showConfirmDialog((Component) null, "¿Desea salir sin guardar?","alert", JOptionPane.YES_NO_CANCEL_OPTION);
			if(resultado==0)
				System.exit(ABORT);
			if(resultado==1){
				explorador.guardarArchivo();
				String ruta = explorador.getArchivo();
				try {
					this.controlador.guardarBarbudi(ruta);
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.frameJuego.dispose();
			}
		}
		
		// Presiono volver
		if(evento.getSource() == this.volver){
			int resultado = mensaje.showConfirmDialog((Component) null, "¿Desea volver al menu principal?","alert", JOptionPane.YES_NO_OPTION);
			if(resultado==0){
				this.contenedorJuego.setVisible(false);
				this.continuar.setEnabled(true);
				this.setVisible(true);
				
			}
		}
		
		// Presiono Continuar...
			if(evento.getSource() == this.continuar){
				this.contenedorJuego.setVisible(true);
				this.setVisible(false);
			}
				
		

		/** ---APUESTA NORMAL---
		 * Funcionamiento: Si el label de apostadorActivo es visible quiere decir que el apostador esta de turno
		 * Sino el boton toma otro comportamiento permitiendo que el tirador y los demas jugadores participen de la apuesta
		 */
			if(evento.getSource() == this.apuestaNormal && this.apostadorActivo.isVisible()){
				ventanaApuestaApostador();
			}
			
			if(evento.getSource() == this.apuestaNormal && this.tiradorActivo.isVisible()){ // Vista del lado del tirador
				ventanaApuestaTirador();
			}
			
		//Presiono aceptar la apuesta: apostador
			if(evento.getSource() == this.aceptarApuestaNormalApost && this.apostadorActivo.isVisible()){
				apostadorAceptaApuesta();
			}
			
		//Presiono aceptar la apuesta: tirador	
			if(evento.getSource() == this.aceptarApuestaNormalTirad && this.tiradorActivo.isVisible()){
				tiradorAceptaApuesta();
			}
		
		//Presiono Tirar los Dados
			if(evento.getSource() == this.tirarDados){
				tirarDados();
			}
			
		//Presiono "Pasar Cubiletes" dependiendo a quien le toque pasar se actua
			if(evento.getSource()==this.nuevaVuelta){
				if(this.avanzaAmbos){
					avanzaRondaNormal();
					this.nuevaVuelta.setVisible(false);
					this.habilitarBotonesApuesta();
				}
				if(this.avanzaTirador){
					avanzaTirador();
					this.nuevaVuelta.setVisible(false);
					this.habilitarBotonesApuesta();
				}
				if(this.avanzaApostador){
					avanzaApostador();
					this.nuevaVuelta.setVisible(false);
					this.habilitarBotonesApuesta();
				}
				this.tiradorActivo.setVisible(false);
				this.apostadorActivo.setVisible(true);
				verificarDineroJugadores();
				if(listaJugadores.size()==1){
					this.imprimir("¡Felicitaciones! Le ha ganado a todos los jugadores de la mesa");
					this.frameJuego.setVisible(false);
					this.continuar.setEnabled(false);
					this.setVisible(true);
					this.continuar.setEnabled(false);
				}
				this.controlador.doApuestaLateral();//Nueva ronda = nueva apuesta lateral(vacia si no se apuesta)
				this.controlador.estadoJugadores();
			}
		//Presiono apuesta lateral
			if(evento.getSource()==this.apuestaLateral){
				apuestaLateral();
			}
		//Presiono Aceptar de apuesta lateral	
			if(evento.getSource()==this.aceptarApuestaLateral){
				aceptarApuestaLateral();
			}
		//Presiono pasar dependiendo de quien sea el turno selecciona quien pasa
			if(evento.getSource() == pasar){
				if(this.apostadorActivo.isVisible()){
					this.controlador.devolverRestante();//Devuelve si el apostador ya habia hecho una apuesta
					this.controlador.devolverDineroATodosLateral();;//Devuelve si habia apuestas laterales
					avanzaApostador();
				}
				if(this.tiradorActivo.isVisible()){
					this.controlador.devolverDineroATodosLateral();//Devuelve si habia apuestas laterales
					this.controlador.devolverRestante();
					avanzaTirador();
					this.tiradorActivo.setVisible(false);
					this.apostadorActivo.setVisible(true);
				}
				this.controlador.estadoJugadores();
			}
			
		//Presiono retirarse del juego dependiendo quien selecciona quien retirar.
			if(evento.getSource() == retirarseDelJuego){
				retirarse();
			}
			
		//Presiono Aceptar en ventana Estado Espectadores
			if(evento.getSource() == okEstadoEspectadores){
				this.ventanaEstadoEspectadores.dispose();
			}
	}//Fin eventos.
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 *   	//////////////////////////////////-----PROCEDIMIENTOS DE EVENTOS-----/////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////		 
 * 		retirarse(): retira el jugador activo
 * 		aceptarApuestaLateral():acepta la apuesta lateral
 * 		apostadorAceptaApuesta():acepta apuesta normal - apostador.
 *		tiradorAceptaApuesta():acepta apuesta normal - tirador. Y ademas si no se cubre le pregunta a los demas si
 *		quieren apostar.
 *		tirarDados():tira los dados y muestra los resultados. Ademas de esto se encarga de verificar que tiro salio
 *		Si es un tiro significativo lo reporta con una ventana emergente.
 *		avanzaTirador():
 *		avanzaApostador():
 *		avanzanAmbos():
 *		crearJugador():
 *		eliminarJugador():
 *		crearEspectador():
 *		eliminarEspectador():
 *		agregarEmpleado():
 *		eliminarEmpleado():
*/	
	private void retirarse() {
		if(this.apostadorActivo.isVisible()){
			String aBorrar = apostador;
			this.controlador.retirarJugador(apostador);
			logJuego.setText(logJuego.getText()+"\nEl jugador "+nombreApostador.getText()+" decidio retirarse del juego.\nAvanzando un lugar en la mesa");
			this.controlador.devolverDineroATodosLateral();;//Devuelve si habia apuestas laterales
			if(listaJugadores.size()<=2){
				this.imprimir("¡Ha quedado solo en la mesa, felicidades Ganador!");
				listaJugadores.remove(tirador);
				this.frameJuego.dispose();
				this.setVisible(true);
			}else{
				avanzaApostador();
				listaJugadores.remove(aBorrar);//Primero avanzo y despues borro para evitar inconsistencias
				actualizarLblJugadores();
			}
		}
		if(this.tiradorActivo.isVisible()){
			String aBorrar = tirador;
			this.controlador.retirarJugador(tirador);
			logJuego.setText(logJuego.getText()+"\nEl jugador "+nombreTirador.getText()+" decidio retirarse del juego.\nAvanzando un lugar en la mesa");
			this.controlador.devolverDineroATodosLateral();;//Devuelve si habia apuestas laterales
			this.controlador.devolverRestante();//Devuelve si el apostador ya habia hecho una apuesta
			if(listaJugadores.size()<=2){
				this.imprimir("¡Ha quedado solo en la mesa, felicidades Ganador!");
				listaJugadores.remove(tirador);
				this.frameJuego.dispose();
				this.setVisible(true);
			}else{
				avanzaTirador();
				listaJugadores.remove(aBorrar);
				actualizarLblJugadores();
			}
		}
		this.controlador.estadoJugadores();
	}	


	private void aceptarApuestaLateral() {
		String persona;
		double dineroPersona;
		double apuesta;
		boolean sePuede = false;
		if(this.esJugador.isSelected()){
			persona = (String)this.comboJugadores.getSelectedItem();
			dineroPersona = this.controlador.getDinero(persona);
			apuesta = (double)this.spinnerLateral.getValue();
			if((!persona.equals(tirador))&&(!persona.equals(apostador))){
				if(dineroPersona>=apuesta-1){
					sePuede = this.controlador.llenarApuestaLateral(persona,apuesta, this.aFavorTirador.isSelected());
					if(sePuede){
						this.ventanaApuestaLateral.dispose();
						this.imprimir("¡Hecho! "+persona+" aposto "+apuesta+" a favor del tirador:"+aFavorTirador.isSelected());
					}else{
						this.imprimir("Error: no se puede apostar en ambos bandos.\nVuelva a intentarlo");
					}
				}else{
				this.imprimir("Dinero insuficiente para realizar la apuesta.\nVuelva a intentarlo con otro monto");
				}
			}else{
				this.imprimir("Un apostador o tirador no puede hacer una apuesta lateral.");
			}
		}
		if(this.esEspectador.isSelected()){
			persona = (String)this.comboEspectadores.getSelectedItem();
			dineroPersona = this.controlador.getDinero(persona);
			apuesta = (double)this.spinnerLateral.getValue();
			if(!persona.equals(tirador)||!persona.equals(apostador)){
				if(dineroPersona>apuesta){
					sePuede = this.controlador.llenarApuestaLateral(persona,apuesta, this.aFavorTirador.isSelected());
					if(sePuede){
						this.ventanaApuestaLateral.dispose();
						this.imprimir("¡Hecho! "+persona+" aposto "+apuesta+" a favor del tirador:"+aFavorTirador.isSelected());
					}else{
						this.imprimir("Error: no se puede apostar en ambos bandos.\nVuelva a intentarlo");
					}
				}else{
				this.imprimir("Dinero insuficiente para realizar la apuesta.\nVuelva a intentarlo con otro monto");
				}
			}else{
				this.imprimir("Un apostador o tirador no puede hacer una apuesta lateral.");
			}
		}
	}


	private void apostadorAceptaApuesta() {
		this.logJuego.setText(logJuego.getText()+"\nEl jugador "+apostador+" ha realizado exitosamente una apuesta de:"+this.dineroAApostar.getText()+".\nCambiando turno...\n");
		this.controlador.doApuestaNormal(Double.valueOf(this.dineroAApostar.getText()));
		this.ventanaApuesta.dispose();
		this.apostadorActivo.setVisible(false);
		actualizarLblJugadores();
		this.tiradorActivo.setVisible(true);
		this.logJuego.setText(logJuego.getText()+"\nEsperando la apuesta del tirador...");
	}


	@SuppressWarnings("static-access")
	private void tiradorAceptaApuesta() {
		this.controlador.llenarApuestaNormal(tirador, Double.valueOf(this.dineroAApostar.getText()));
		this.dineroDisponibleTirador.setText("Dinero disponible: "+this.controlador.getDinero(tirador));
		this.ventanaApuesta.dispose();
		if(this.controlador.apuestaNormalEquilibrada()&&this.controlador.apuestaLateralEquilibrada()){
			this.logJuego.setText(logJuego.getText()+"\nEl tirador "+tirador+" ha realizado exitosamente una apuesta de:"+this.dineroAApostar.getText()+".\n¡A tirar los dados!");
			habilitarBotonesTirar();
		}else{
			this.logJuego.setText(logJuego.getText()+"\nEl tirador "+tirador+" ha realizado exitosamente una apuesta de:"+this.dineroAApostar.getText()+".\nComo no alcanza a cubrir la apuesta "
					+ "del Apostador se tomaran apuestas de otros jugadores\nque deseen apostar a favor del Tirador");
			actualizarLblJugadores();
			
			//VENTANA APUESTA NORMAL PARA LOS JUGADORES QUE DESEEN PARTICIPAR DE LA JUGADA ACTUAL
			int i = 0;
			String apuesta;
			double dineroMax = this.controlador.getDineroEnJuegoApostador()-Double.valueOf(this.dineroAApostar.getText());
			while(listaJugadores.size()>i && dineroMax>1){
				String e = listaJugadores.get(i);
				if(!e.equals(apostador) && !e.equals(tirador)){
					apuesta = mensaje.showInputDialog(null,"Jugador: "+e+" Ingresa la apuesta a favor del tirador"+" (Max: "+String.valueOf(dineroMax)+")");
					if(apuesta != null && this.controlador.getDinero(e)>=Double.valueOf(apuesta)){//Me aseguro de que tenga el dinero para pagar la apuesta
						if(Double.valueOf(apuesta)>dineroMax){
							this.imprimir("Error: Su apuesta supero el limite.");
						}else{
							dineroMax -= Double.valueOf(apuesta);
							this.controlador.llenarApuestaNormal(e, Double.valueOf(apuesta));
							this.logJuego.setText(logJuego.getText()+"\nEl jugador "+e+" aposto: "+apuesta+" a favor del tirador.\nAun por cubrir: "+dineroMax);
						}
						
					}else{
						this.logJuego.setText(logJuego.getText()+"\nEl jugador "+e+" paso o no posee el dinero suficiente realizar la apuesta.\nAun por cubrir: "+dineroMax);
					}
				}
				i++;
			}
			if(this.controlador.apuestaNormalEquilibrada()&&this.controlador.apuestaLateralEquilibrada()){
				this.logJuego.setText(logJuego.getText()+"\n\n¡Todo listo para tirar los dados!");
				habilitarBotonesTirar();
			}else{
				this.logJuego.setText(logJuego.getText()+"\nAdvertencia: No se llego a cubrir "+dineroMax+" de la suma impuesta por el apostador.\nDevolviendo el restante al apostador...");
				this.controlador.devolverRestante();
				actualizarLblJugadores();
				this.logJuego.setText(logJuego.getText()+"\n\n¡Todo listo para tirar los dados!");
				habilitarBotonesTirar();
			}
		}
	}

	@SuppressWarnings("static-access")
	private void tirarDados() {
		if(tiradorActivo.isVisible())
			this.controlador.addTiradaToRonda(tirador); //Tira el tirador
		if(apostadorActivo.isVisible())
			this.controlador.addTiradaToRonda(apostador);//Tira el apostador
		boolean noSalioNada = true;
		double resultado = this.controlador.getValorTirada();
		String resultString = Double.toString(resultado/10);
		String[] convertidor = resultString.split("\\.");
		String strDado1 = convertidor[0];
		String strDado2 = convertidor[1];
		if(tiradorActivo.isVisible()){
			this.dado1Tirador.setText("Primer Dado: "+strDado1);
			this.dado2Tirador.setText("Segundo Dado: "+strDado2);
		}else{
			this.dado1Apostador.setText("Primer Dado: "+strDado1);
			this.dado2Apostador.setText("Segundo Dado: "+strDado2);					
		}
		if(this.controlador.tiroGanador((int)resultado)){
			this.mensaje.showMessageDialog(this.frameJuego, "¡Felicidades ha sacado un tiro ganador!","Tiro Ganador c:", JOptionPane.INFORMATION_MESSAGE);
			noSalioNada = false;
			deshabilitarBotonesAfterPlay();
			this.nuevaVuelta.setVisible(true);
			if(tiradorActivo.isVisible()){ //Gano el tirador
				this.logJuego.setText(logJuego.getText()+"\n¡Hay un nuevo ganador! El jugador "+tirador+" gano la ronda");
				this.controlador.repartirDineroRonda(true);
				actualizarLblJugadores(); //Actualizo su dinero disponible
				this.avanzaAmbos = true;
			}else{ //Gano el apostador
				this.logJuego.setText(logJuego.getText()+"\n¡Hay un nuevo ganador! El jugador "+apostador+" gano la ronda");
				this.controlador.repartirDineroRonda(false);
				actualizarLblJugadores(); //Actualizo su dinero disponible
				this.avanzaAmbos = true;
			}
		}
		if(this.controlador.tiroPerdedor((int)resultado)){
			this.mensaje.showMessageDialog(this.frameJuego, "¡Ha sacado un tiro perdedor!","Tiro Perdedor :c", JOptionPane.INFORMATION_MESSAGE);
			noSalioNada = false;
			deshabilitarBotonesAfterPlay();
			this.nuevaVuelta.setVisible(true);
			if(tiradorActivo.isVisible()){ //Perdio el tirador
				this.logJuego.setText(logJuego.getText()+"\n¡Hay un nuevo perdedor! El jugador "+tirador+" perdio la ronda");
				this.controlador.repartirDineroRonda(false);
				actualizarLblJugadores(); //Actualizo su dinero disponible
				this.avanzaAmbos = true;
			}else{ //Perdio el apostador
				this.logJuego.setText(logJuego.getText()+"\n¡Hay un nuevo perdedor! El jugador "+apostador+" perdio la ronda");
				this.controlador.repartirDineroRonda(true);
				actualizarLblJugadores(); //Actualizo su dinero disponible
				this.avanzaAmbos = true;
			}
		}
		if(this.controlador.tiroEspecial12((int)resultado) && tiradorActivo.isVisible()){
			this.logJuego.setText(logJuego.getText()+"\n¡Hubo un tiro especial perdedor! El jugador "+tirador+" debe elegir si desea retener los dados");
			int respuesta = this.mensaje.showConfirmDialog(this.frameJuego, "¡Ha sacado un tiro perdedor especial!¿Desea retener los dados?",
					"Tiro perdedor Especial :o",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			noSalioNada = false;
			deshabilitarBotonesAfterPlay();
			this.nuevaVuelta.setVisible(true);
			this.controlador.repartirDineroRonda(false);
			actualizarLblJugadores(); //Actualizo su dinero disponible
			if(respuesta==0){
				this.logJuego.setText(logJuego.getText()+"\nEl tirador decide retener los dados, el apostador debe pasar el cubilete a su derecha");
				this.avanzaAmbos = false;
				this.avanzaApostador = true;
				this.avanzaTirador = false;
			}else{
				this.logJuego.setText(logJuego.getText()+"\nEl tirador decide no retener los dados, el apostador sera ahora el nuevo tirador");
				this.avanzaAmbos = true;
			}
		}
		if(this.controlador.tiroEspecial65((int)resultado) && apostadorActivo.isVisible()){
			this.logJuego.setText(logJuego.getText()+"\n¡Hubo un tiro especial ganador! El jugador "+apostador+" debe elegir si desea retener los dados");
			int respuesta = this.mensaje.showConfirmDialog(this.frameJuego, "¡Ha sacado un tiro ganador especial!¿Desea retener los dados?",
					"Tiro ganador Especial :D",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			noSalioNada = false;
			deshabilitarBotonesAfterPlay();
			this.nuevaVuelta.setVisible(true);
			this.controlador.repartirDineroRonda(false);
			actualizarLblJugadores(); //Actualizo su dinero disponible
			if(respuesta==0){
				this.avanzaAmbos = true;
				this.avanzaTirador = false;
				this.avanzaApostador = false;
				this.logJuego.setText(logJuego.getText()+"\nEl apostador decide retener los dados, el tirador debe pasar el cubilete a su derecha");
			}else{
				this.logJuego.setText(logJuego.getText()+"\nEl tirador decide no retener los dados, el apostador y el tirador deben pasar los cubiletes a su derecha");
				this.avanzaAmbos = true;
			}
		}
		if(noSalioNada){
			this.tiradorActivo.setVisible(!tiradorActivo.isVisible());
			this.apostadorActivo.setVisible(!tiradorActivo.isVisible());
		}else{
			resumenEspectadores();
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
		}else{
			this.logJuego.setText(logJuego.getText()+"¡Ha quedado solo en la mesa, felicidades Ganador!");
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
		
		//Luego de ejecutar cualquiera de estos eventos debo re-inicializar los booleanos
		this.avanzaAmbos = false;
		this.avanzaApostador = false;
		this.avanzaTirador = false;
		
	}
	/**
	 *  Creo un jugador; No permito crear 2 jugadores con el mismo nombre
	 */
	@SuppressWarnings("static-access")
	private void crearJugador(){
		String nombre = mensaje.showInputDialog("Nombre de jugador:");
		String iniciales = mensaje.showInputDialog("Iniciales de jugador:");
		String dineroStr = mensaje.showInputDialog("Dinero de jugador:");
		double dinero=0;
		if(dineroStr!=null){
			dinero = Double.parseDouble(dineroStr);
		}
		String resultado = "";
		if(!listaJugadores.contains((String) nombre)&&(!listaEspectadores.contains((String) nombre))){
			if(nombre != null && iniciales !=null && dineroStr!=null){
				resultado = this.controlador.crearJugador(nombre,iniciales,dinero);
			}else{
				imprimir("Algo salio mal, porfavor revise los campos antes de Aceptar");
			}
			if(resultado != null){
					listaJugadores.add(resultado);
				}
		}else{
			imprimir("El nombre que usted quiere ingresar ya se encuentra en uso.");
		}
	}
	/**
	 * Elimino un jugador
	 */
	@SuppressWarnings("static-access")
	private void eliminarJugador(){
		if( !this.listaJugadores.isEmpty() ){ 
			String[] jugadores = new String[this.listaJugadores.size()];
			jugadores = this.listaJugadores.toArray(jugadores); // creo un array a partir de un arrayList con los jugadores;
						
			String jugador = (String) mensaje.showInputDialog(null,"Elija el jugador a eliminar","Selector de jugadores", mensaje.QUESTION_MESSAGE, null, jugadores, jugadores[0]);
						
			String resultado = "";
			
			if(jugador != null) // Si es null, oprimio cancelar
				resultado = this.controlador.eliminarJugador(jugador);
			
			if(resultado != null)
				listaJugadores.remove(resultado);
		}
		else this.imprimir("La lista de Jugadores esta vacia");
	}
	/**
	 * Creo un espectador
	 */
	@SuppressWarnings("static-access")
	private void crearEspectador() {
		String nombre = mensaje.showInputDialog("Nombre del espectador:");
		String iniciales = mensaje.showInputDialog("Iniciales del espectador:");
		String dineroStr = mensaje.showInputDialog("Dinero del espectador:");
		double dinero =0;
		if(dineroStr!=null){
			dinero = Double.parseDouble(dineroStr);
		}
		String resultado = "";
		if(!listaJugadores.contains((String) nombre)&&(!listaEspectadores.contains((String) nombre))){
			if(nombre != null && iniciales !=null){
				resultado = this.controlador.crearEspectador(nombre,iniciales,dinero);
			}else{
				imprimir("Algo salio mal, porfavor revise los campos antes de Aceptar");
			}
		
			if(resultado != null){
				listaEspectadores.add(resultado);
			}
		}else{
			imprimir("El nombre que intenta usar ya se encuentra en uso");
		}
	}
	/**
	 * Elimino un espectador
	 */
	@SuppressWarnings("static-access")
	private void eliminarEspectador(){
		if( !this.listaEspectadores.isEmpty() ){ 
			String[] espectadores = new String[this.listaEspectadores.size()];
			espectadores = this.listaEspectadores.toArray(espectadores); // creo un array a partir de un arrayList con los jugadores;
						
			String espectador = (String) mensaje.showInputDialog(null,"Elija el Espectador a eliminar","Selector de espectadores", mensaje.QUESTION_MESSAGE, null, espectadores, espectadores[0]);
			String resultado = "";
			
			if(espectador != null) // si espectador es null significa que el usuario apreto el boton cancelar
				resultado = this.controlador.eliminarEspectador(espectador);
			
			if(resultado != null)
				listaEspectadores.remove(resultado);
		}
		else this.imprimir("La lista de espectadores esta vacia");
	}	
	/**
	 * Creo un empleado nuevo
	 */
	@SuppressWarnings("static-access")
	private void agregarEmpleado() {
		String nombre = mensaje.showInputDialog("Nombre del empleado:");
		String resultado = "";
		if(nombre != null)
			resultado = this.controlador.crearEmpleado(nombre);
		
		if(resultado != null)
			listaEmpleados.add(resultado);
	}
		
	
	/**
	 * Elimino un empleado
	 */
	@SuppressWarnings("static-access")
	private void eliminarEmpleado() {
		if( !this.listaEmpleados.isEmpty() ){ 
			String[] empleados = new String[this.listaEmpleados.size()];
			empleados = this.listaEmpleados.toArray(empleados); // creo un array a partir de un arrayList con los empleados;
						
			String empleado = (String) mensaje.showInputDialog(null,"Elija el empleado a eliminar","Selector de empleados", mensaje.QUESTION_MESSAGE, null, empleados, empleados[0]);
						
			String resultado = "";
			
			if(empleado != null) 
				resultado = this.controlador.eliminarEmpleado(empleado);
			
			if(resultado != null)
				listaEmpleados.remove(resultado);
		}
		else this.imprimir("La lista de empleados esta vacia");		
	}
	
	

	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 *   	///////////////////////////////////////////-----VENTANAS-----/////////////////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////		 
 * 		ventanaTop5: Ventana del top 5 de la partida actual.
 * 		apuestaLateral: Ventana de la apuesta lateral
 * 		ventanaApuestaTirador:
 * 		ventanaApuestaApostador:
 * 		opcionesAntesEmpezar():Ventana que da a elegir el modo de juego.
 * 		ventanaEstadoEspectadores():Muestra el estado de los espectadores cada vez que termina una ronda.
 *
 */	
	private void ventanaTop5() {
		frameTop5 = new JFrame("TOP 5");
		frameTop5.setLayout(new BorderLayout());
		Container containerIzq = new Container();
		Container containerDer = new Container();
		JLabel top = new JLabel("TOP 5");
		top.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		JLabel espectador = new JLabel("Espectadores");
		JLabel jugador = new JLabel("Jugadores");
		volverTop5 = this.setBotones(this.volverTop5, "Volver", fuente);
		GridLayout gl = new GridLayout(7,1);
		containerIzq.setLayout(gl);
		containerDer.setLayout(gl);
		containerIzq.add(espectador);
		containerDer.add(jugador);
		String[] top5Jugadores = new String[5];
		String[] top5Espectadores = new String[5];
		top5Jugadores = this.controlador.getTop5Jugadores();
		top5Espectadores = this.controlador.getTop5Espectadores();
		for(int i=1;i<5;i++){
			containerIzq.add(new JLabel(i+". "+top5Espectadores[i-1]));
			containerDer.add(new JLabel(i+". "+top5Jugadores[i-1]));
		}
		frameTop5.add(top,BorderLayout.NORTH);
		frameTop5.add(containerIzq,BorderLayout.WEST);
		frameTop5.add(containerDer,BorderLayout.EAST);
		frameTop5.add(this.volverTop5,BorderLayout.SOUTH);
		frameTop5.setSize(250,300);
		frameTop5.setResizable(false);
		frameTop5.setLocationRelativeTo(null);
		frameTop5.setVisible(true);
	}

	private void apuestaLateral() {
		ventanaApuestaLateral = new JFrame("Apuesta Lateral");
		ventanaApuestaLateral.setSize(450, 400);
		JLabel lblQuienHaceLateral = new JLabel("¿Quien desea hacer una apuesta lateral?");
		esEspectador = new JRadioButton("Es espectador");
		esJugador  = new JRadioButton("Es jugador");
		ButtonGroup grupoQuienHace = new ButtonGroup();
		String[] espectadores = new String[this.listaEspectadores.size()];
		String[] jugadores = new String[this.listaJugadores.size()];
		espectadores = this.listaEspectadores.toArray(espectadores);
		jugadores = this.listaJugadores.toArray(jugadores); 
		JLabel lblEspectadores = new JLabel("Espectadores:");
		JLabel lblJugadores = new JLabel("Jugadores:");
		ButtonGroup grupoAFavor = new ButtonGroup();
		this.aceptarApuestaLateral = this.setBotones(aceptarApuestaLateral, "Aceptar", fuente);
		this.comboEspectadores = new JComboBox<>(espectadores); 
		this.comboJugadores = new JComboBox<>(jugadores);
		this.aFavorApostador = new JRadioButton("A favor del apostador");
		this.aFavorTirador = new JRadioButton("A favor del tirador");	
		this.spinnerLateral = new JSpinner();
		this.spinnerLateral.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		grupoAFavor.add(aFavorApostador);
		grupoAFavor.add(aFavorTirador);
		comboJugadores.setEnabled(false);
		comboEspectadores.setEnabled(false);
		//Escucha los cambios de los JRadioButton
		esEspectador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton source = (JRadioButton)e.getSource();
				if(source.isSelected()){
					comboJugadores.setEnabled(false);
					comboEspectadores.setEnabled(true);
				}
			}
		});
		esJugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton source = (JRadioButton)e.getSource();
				if(source.isSelected()){
					comboJugadores.setEnabled(true);
					comboEspectadores.setEnabled(false);
				}
			}
		});
		grupoQuienHace.add(esJugador);
		grupoQuienHace.add(esEspectador);
		ventanaApuestaLateral.setLayout(new GridLayout(11,1));
		ventanaApuestaLateral.add(lblQuienHaceLateral);
		ventanaApuestaLateral.add(esEspectador);
		ventanaApuestaLateral.add(esJugador);
		ventanaApuestaLateral.add(lblEspectadores);
		ventanaApuestaLateral.add(comboEspectadores);
		ventanaApuestaLateral.add(lblJugadores);
		ventanaApuestaLateral.add(comboJugadores);
		ventanaApuestaLateral.add(aFavorApostador);
		ventanaApuestaLateral.add(aFavorTirador);
		ventanaApuestaLateral.add(spinnerLateral);
		ventanaApuestaLateral.add(aceptarApuestaLateral);	
		ventanaApuestaLateral.setLocationRelativeTo(null);
		ventanaApuestaLateral.setVisible(true);
		
	}

	private void ventanaApuestaApostador() {
		ventanaApuesta = new JFrame();
		double dineroMax = this.controlador.getDinero(apostador);
		JSlider cantidad = new JSlider(JSlider.HORIZONTAL,0, (int)dineroMax,(int)dineroMax/2);
		dineroAApostar = new JLabel(""+cantidad.getValue());
		aceptarApuestaNormalApost = new JButton("Aceptar");
		cantidad.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				 JSlider source = (JSlider)e.getSource();
				    if (!source.getValueIsAdjusting()) {
				       dineroAApostar.setText(""+source.getValue());	        
				    }
			}
		});
		cantidad.setMajorTickSpacing(10);
		cantidad.setMinorTickSpacing(1);
		dineroAApostar.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		aceptarApuestaNormalApost.addActionListener(this);
		ventanaApuesta.setLayout(new GridLayout(3,1));
		ventanaApuesta.add(cantidad);
		ventanaApuesta.add(dineroAApostar);
		ventanaApuesta.add(aceptarApuestaNormalApost);
		ventanaApuesta.pack();
		ventanaApuesta.setSize(250,200);
		ventanaApuesta.setDefaultCloseOperation(HIDE_ON_CLOSE);
		ventanaApuesta.setLocationRelativeTo(null);
		ventanaApuesta.setVisible(true);
	}

	private void ventanaApuestaTirador() {
		ventanaApuesta = new JFrame();
		JSlider cantidad;
		this.tirador = this.controlador.getTirador();
		double dineroMax = this.controlador.getDineroEnJuegoApostador();
		double dineroDisponible = this.controlador.getDinero(tirador);
		//Me aseguro de que no le deje poner mas de lo que tiene
		if(dineroDisponible>dineroMax)
			cantidad = new JSlider(JSlider.HORIZONTAL,0, (int)dineroMax,(int)dineroMax/2);
		else
			cantidad = new JSlider(JSlider.HORIZONTAL,0, (int)dineroDisponible,(int)dineroDisponible/2);
		dineroAApostar = new JLabel(""+cantidad.getValue());
		aceptarApuestaNormalTirad = new JButton("Aceptar");
		cantidad.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				 JSlider source = (JSlider)e.getSource();
				    if (!source.getValueIsAdjusting()) {
				       dineroAApostar.setText(""+source.getValue());	        
				    }
			}
		});
		cantidad.setMajorTickSpacing(10);
		cantidad.setMinorTickSpacing(1);
		dineroAApostar.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		aceptarApuestaNormalTirad.addActionListener(this);
		ventanaApuesta.setLayout(new GridLayout(3,1));
		ventanaApuesta.add(cantidad);
		ventanaApuesta.add(dineroAApostar);
		ventanaApuesta.add(aceptarApuestaNormalTirad);
		ventanaApuesta.pack();
		ventanaApuesta.setSize(250,200);
		ventanaApuesta.setDefaultCloseOperation(HIDE_ON_CLOSE);
		ventanaApuesta.setLocationRelativeTo(null);
		ventanaApuesta.setVisible(true);
	}
	
	private void opcionesAntesEmpezar() {
		this.setVisible(false);
		ventanaOpciones = new JFrame("Antes de empezar..");
		ventanaOpciones.setSize(500, 150);
		ventanaOpciones.setLayout(new BorderLayout());
		JLabel modo = new JLabel("Porfavor seleccione el modo:");
		JLabel empleado = new JLabel("Porfavor seleccione el empleado:");
		okOpciones = this.setBotones(okOpciones, "Listo!", fuente);
		String[] empleados = new String[this.listaEmpleados.size()]; //Creo un array para usarlo en el JList
		empleados = this.listaEmpleados.toArray(empleados); //Lo cargo
		jListEmpleados = new JComboBox<String>(empleados);
		modoNormal = new JRadioButton("Modo normal(Sin 2,5% de comision)");
		modoCasino = new JRadioButton("Modo casino(Con 2,5% de comision)");
		modoNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modoNormal.isSelected())
					jListEmpleados.setEnabled(false);
			}
		});
		//Activo para que elija el empleado
		modoCasino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modoCasino.isSelected())
					jListEmpleados.setEnabled(true);
			}
		});
		ButtonGroup grupo = new ButtonGroup();
		Container conIzq = new Container();
		Container conDer = new Container();
		Container conGral = new Container();
		conGral.setLayout(new GridLayout(1,2));
		conIzq.setLayout(new GridLayout(3,1));
		conDer.setLayout(new GridLayout(3,1));
		grupo.add(modoNormal);
		grupo.add(modoCasino);
		conIzq.add(modo);
		conIzq.add(modoNormal);
		conIzq.add(modoCasino);
		conDer.add(empleado);
		conDer.add(jListEmpleados);
		conGral.add(conIzq);
		conGral.add(conDer);
		ventanaOpciones.add(okOpciones,BorderLayout.SOUTH);
		ventanaOpciones.add(conGral,BorderLayout.CENTER);
		ventanaOpciones.setLocationRelativeTo(null);
		ventanaOpciones.setVisible(true);		
	}

	private void resumenEspectadores() {
		ventanaEstadoEspectadores = new JFrame("Resumen de Espectadores");
		ventanaEstadoEspectadores.setLayout(new BorderLayout());
		Container contenedorVentana = new Container();
		contenedorVentana.setLayout(new GridLayout(listaEspectadores.size(), 1));
		JLabel estado = new JLabel("Estado de los espectadores:");
		estado.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		okEstadoEspectadores = this.setBotones(okEstadoEspectadores, "Aceptar", fuente);
		String[] estadoEspectadores = new String[listaEspectadores.size()];
		estadoEspectadores = this.controlador.getEstadoEspectadores();
		for(int i = 0;i<=estadoEspectadores.length-1;i++){
			contenedorVentana.add(new JLabel((i+1)+". "+estadoEspectadores[i]));
		}
		contenedorVentana.setVisible(true);
		ventanaEstadoEspectadores.add(estado,BorderLayout.NORTH);
		ventanaEstadoEspectadores.add(contenedorVentana, BorderLayout.CENTER);
		ventanaEstadoEspectadores.add(okEstadoEspectadores, BorderLayout.SOUTH);
		ventanaEstadoEspectadores.setLocationRelativeTo(null);
		ventanaEstadoEspectadores.setSize(250, 300);
		ventanaEstadoEspectadores.setVisible(true);
	}

	
/**
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
 *   	//////////////////////////////////////-----PROCEDIMIENTOS GENERALES-----//////////////////////////////////
 * 		//////////////////////////////////////////////////////////////////////////////////////////////////////////		
 * 		verificarDineroJugadores():Este metodo se utiliza para saber si hay que eliminar de la mesa de juego a algun jugador.
 * 		iniciarJuego(): Este metodo permite iniciar el juego.(primera vez)
 * 		primeraRonda(): Se encarga de hacer el ciclo para elegir el primer tirador.
 * 		continuarEmpezado():Continuar una partida empezada.
 */
	private void verificarDineroJugadores() {
		String aEliminar="";
		for(String e:listaJugadores){
			if(this.controlador.getDinero(e)<=0.90){
				aEliminar = e;
				this.controlador.eliminarJugador(e);
				this.logJuego.setText(logJuego.getText()+"\n Oops! El jugador "+e+" se ha quedado sin dinero, y fue eliminado del juego!\n");
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

	private void iniciarJuego() {
		if(this.listaJugadores.size()>1 && this.listaEmpleados.size()>=1){ 			
			this.controlador.addCubilete();
			iniciarComponentesJuego();
			this.contenedorJuego.setVisible(true);
			empezarPrimeraRonda();
			//Vuelvo a poner los botones. tirarDados no hasta que apuesten o pasen
			habilitarBotonesApuesta();
			pasar.setEnabled(true);
			retirarseDelJuego.setEnabled(true);
			this.nombreApostador.setText("Nombre :"+ apostador);
			this.nombreTirador.setText("Nombre :"+ tirador);
			this.controlador.inicializar();
			this.controlador.doApuestaLateral();//Primera apuesta lateral
			this.apostadorActivo.setVisible(true);
		}else 
			this.imprimir("Se necesitan como minimo:\n·2 jugadores para iniciar el juego \n·1 Empleado");
	}


	private void empezarPrimeraRonda(){
		int posGanador=0;
		int mayorValor = 0;
		double tiradaActual = 0;
		this.pasar.setEnabled(false);
		this.apuestaLateral.setEnabled(false);
		this.apuestaNormal.setEnabled(false);
		this.tirarDados.setEnabled(false);
		this.retirarseDelJuego.setEnabled(false);
		if(!this.empleado.equals("Sin Empleado"))
			this.logJuego.setText("Se ha iniciado el juego en modo Casino, el empleado que eligio es: "+this.empleado);
		this.logJuego.setText(logJuego.getText()+"\nPrimera ronda..\nEl que saque los dados mas altos sera el tirador, y el de su derecha el apostador.\n¡Buena Suerte!\n");
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
				this.logJuego.setText(logJuego.getText()+"\n¡Nuevo puntaje alto! Hecho por: "+e+"\n1º Dado:"+strDado1+"\n2º Dado:"+strDado2+"\nUn total de: "+sumaTotal+" puntos\n-----");
				this.tirador = e;
			}else{
				this.logJuego.setText(logJuego.getText()+"\nTiro los dados: "+e+" \n1º Dado:"+strDado1+"\n2º Dado:"+strDado2+"\nUn total de: "+sumaTotal+" puntos\n-----");
			}
		}
		this.controlador.setPrimerTirador(listaJugadores.get(posGanador));
	}

	@SuppressWarnings("static-access")
	private void continuarEmpezado(){
		try{
			tirador = this.controlador.getTirador();
			apostador = this.controlador.getApostador();
			empleado = this.controlador.getEmpleadoName();
			iniciarComponentesJuego();
			int resultado = mensaje.showConfirmDialog((Component) null, "¿Desea ir a la ventana del juego en curso?","alert", JOptionPane.YES_NO_OPTION);
			if(resultado==0){
				this.setVisible(false);
				this.frameJuego.setVisible(true);
				actualizarLabelApostador();
				actualizarLabelTirador();
				this.apostadorActivo.setVisible(true);
				habilitarBotonesApuesta();
			}
		}catch(Exception e){
			System.out.println(e+": La partida habia sido guardada pero nunca empezada");
		}
	}
/**
* 		//////////////////////////////////////////////////////////////////////////////////////////////////////////
*   	/////////////////////////////////////////////-----MENSAJES-----///////////////////////////////////////////
* 		//////////////////////////////////////////////////////////////////////////////////////////////////////////	
*		imprimir(String texto):Este metodo lanza un cartel con el texto que se le pasa por parametro.
*		listarJugadores: Muestro un mensaje con la lista de Jugadores.
*		listarEspectadores(): Muestro un mensaje con la lista de Espectadores.
*		listarEmpleados(): Muestro un mensaje con la lista de Empleados
*/

	@SuppressWarnings("static-access")
	private void imprimir(String texto) {
		this.mensaje.showMessageDialog(null, texto, "Info", mensaje.INFORMATION_MESSAGE);		
	}

	@SuppressWarnings("static-access")
	private void listarJugadores() {
		if(!listaJugadores.isEmpty())
			JOptionPane.showMessageDialog(null, this.listaJugadores, "Lista de jugadores", mensaje.INFORMATION_MESSAGE);
		else this.imprimir("La lista de jugadores esta vacia");
	}

	@SuppressWarnings("static-access")
	private void listarEspectadores() {	
		if(!listaEspectadores.isEmpty())
			JOptionPane.showMessageDialog(null, this.listaEspectadores, "lista de espectadores", mensaje.INFORMATION_MESSAGE);
		else this.imprimir("La lista de espectadores esta vacia");
	}

	@SuppressWarnings("static-access")
	private void listarEmpleados() {
		if(!listaEmpleados.isEmpty())
			JOptionPane.showMessageDialog(null, this.listaEmpleados, "Lista de empleados", mensaje.INFORMATION_MESSAGE);
		else this.imprimir("La lista de empleados esta vacia");
	}
}
