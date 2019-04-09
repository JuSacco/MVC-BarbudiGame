package controlador;

import java.io.IOException;
import java.util.ArrayList;

public interface IControlador {
	
	public void cargarEstado(String ruta);
	
	public void guardarBarbudi(String ruta) throws IOException;

	public String crearJugador(String nombre, String inicial, double dinero);

	public String eliminarJugador(String jugador);

	public String crearEspectador(String nombre, String inicial, double dinero);

	public String eliminarEspectador(String nombre);

	public String crearEmpleado(String nombre);

	public String eliminarEmpleado(String empleado);

	public void addCubilete();

	public void setPrimerTirador(String jugador);

	public String getApostador();

	public String getTirador();
	
	void inicializar();

	public double tirarDados();
	
	public int getValorTirada();
	
	public double getDinero(String nombre);
	
	public double getDineroEnJuegoApostador();
	
	public void doApuestaNormal(double dinero);
	
	public void doApuestaLateral();
	
	public void llenarApuestaNormal(String nombre,double dinero);

	public boolean apuestaNormalEquilibrada();

	public void devolverRestante();
	
	public void addTiradaToRonda(String nombre);
	
	public boolean tiroGanador(int tiro);

	public boolean tiroPerdedor(int tiro);
	
	public boolean tiroEspecial12(int tiro);

	public boolean tiroEspecial65(int tiro);
	
	public void repartirDineroRonda(boolean ganoTirador);
	
	public void addNewRonda(String nuevoTirador,String nuevoApostador);

	public boolean llenarApuestaLateral(String persona, double dinero, boolean aFavorTirador);
	
	public boolean apuestaLateralEquilibrada();
	
	public ArrayList<String> getAllJugadores();

	public ArrayList<String> getAllEspectadores();

	public ArrayList<String> getAllEmpleados();

	public String getEmpleadoName();

	public String[] getTop5Jugadores();

	public String[] getTop5Espectadores();

	public void devolverDineroATodosLateral();

	public void repartirDineroRondaLateral(boolean ganoTirador);

	public void estadoJugadores();
	
	public void retirarJugador(String nombre);

	public void setEmpleado(String empleado);

	public String[] getEstadoEspectadores();

	public int getUltimaRonda();
	
	public int getUltimaApuestaLateral();
}
