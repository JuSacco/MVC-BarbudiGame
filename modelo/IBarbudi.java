package modelo;

import java.io.IOException;
import java.util.ArrayList;

public interface IBarbudi {
	public void addEmpleado(String nombre);

	public void addJugador(String nombre,String inicial,double dinero);
	
	public void addEspectador(String nombre,String inicial,double dinero);
	
	public void removeEspectador(String e);

	public void removeJugador(String jugador);

	public void removeEmpleado(String e);

	public void addCubilete();
	
	public int tirarDados();

	public void setPrimerTirador(String jugador);

	public String getApostadorName();
	
	public void inicializar();

	public String getTiradorName();

	public int getValorTirada();

	public double getDinero(String nombre);
	
	public double getDineroEnJuegoApostador();
	
	public Ronda getUltimaRonda();
	
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
	
	public void guardarBarbudi(String ruta) throws IOException;
	
	public void leerBarbudiSerializable(String ruta);
	
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

	public Lateral getUltimaApuestaLateral();
}
