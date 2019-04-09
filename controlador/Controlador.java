package controlador;

import java.io.IOException;
import java.util.ArrayList;

import modelo.*;

public class Controlador implements IControlador{
	IBarbudi barbudi;

	public Controlador(Barbudi modelo){
		this.barbudi = modelo;
	}
	
	@Override
	public String crearEmpleado(String nombre) {
		this.barbudi.addEmpleado(nombre);
		return nombre;
	}
	@Override
	public String crearJugador(String nombre, String inicial, double dinero){
		this.barbudi.addJugador(nombre, inicial, dinero);
		return nombre;
	}

	@Override
	public void cargarEstado(String ruta) {
		this.barbudi.leerBarbudiSerializable(ruta);
	}

	@Override
	public void guardarBarbudi(String ruta) throws IOException {
		this.barbudi.guardarBarbudi(ruta);
	}
	
	@Override
	public String eliminarJugador(String jugador) {
		this.barbudi.removeJugador(jugador);
		return jugador;
	}

	@Override
	public String crearEspectador(String nombre, String inicial, double dinero) {
		this.barbudi.addEspectador(nombre, inicial, dinero);
		return nombre;
	}

	@Override
	public String eliminarEspectador(String nombre) {
		this.barbudi.removeEspectador(nombre);
		return nombre;
	}

	@Override
	public String eliminarEmpleado(String empleado) {
		this.barbudi.removeEmpleado(empleado);
		return empleado;
	}
	
	@Override
	public void addCubilete(){
		this.barbudi.addCubilete();
	}

	@Override
	public void setPrimerTirador(String jugador) {
		this.barbudi.setPrimerTirador(jugador);
	}
	
	@Override
	public String getApostador(){
		return this.barbudi.getApostadorName();
	}
	@Override
	public void inicializar(){
		this.barbudi.inicializar();
	}

	@Override
	public String getTirador() {
		return this.barbudi.getTiradorName();
	}

	@Override
	public double tirarDados() {
		return this.barbudi.tirarDados();
	}
	
	public int getValorTirada(){
		return this.barbudi.getValorTirada();
	}
	
	public double getDinero(String nombre){
		return this.barbudi.getDinero(nombre);
	}
	
	public double getDineroEnJuegoApostador(){
		return this.barbudi.getDineroEnJuegoApostador();
	}

	@Override
	public void doApuestaNormal(double dinero) {
		this.barbudi.doApuestaNormal(dinero);
	}

	@Override
	public void llenarApuestaNormal(String nombre, double dinero) {
		this.barbudi.llenarApuestaNormal(nombre, dinero);		
	}

	@Override
	public boolean apuestaNormalEquilibrada() {
		return this.barbudi.apuestaNormalEquilibrada();
	}

	@Override
	public void devolverRestante() {
		this.barbudi.devolverRestante();
	}
	
	public void addTiradaToRonda(String nombre){
		this.barbudi.addTiradaToRonda(nombre);
	}

	@Override
	public boolean tiroGanador(int tiro) {
		return this.barbudi.tiroGanador(tiro);
	}

	@Override
	public boolean tiroPerdedor(int tiro) {
		return this.barbudi.tiroPerdedor(tiro);
	}

	@Override
	public boolean tiroEspecial12(int tiro) {
		return this.barbudi.tiroEspecial12(tiro);
	}

	@Override
	public boolean tiroEspecial65(int tiro) {
		return this.barbudi.tiroEspecial65(tiro);
	}

	@Override
	public void addNewRonda(String nuevoTirador, String nuevoApostador) {
		this.barbudi.addNewRonda(nuevoTirador, nuevoApostador);
	}

	@Override
	public void repartirDineroRonda(boolean ganoTirador) {
		this.barbudi.repartirDineroRonda(ganoTirador);
	}
	
	public void doApuestaLateral(){
		this.barbudi.doApuestaLateral();
	}
	public boolean llenarApuestaLateral(String persona,double dinero,boolean aFavorTirador){
		return this.barbudi.llenarApuestaLateral(persona, dinero,aFavorTirador);
	}
	
	public boolean apuestaLateralEquilibrada(){
		return this.barbudi.apuestaLateralEquilibrada();
	}
	
	public ArrayList<String> getAllJugadores(){
		return this.barbudi.getAllJugadores();
	}
	
	public ArrayList<String> getAllEspectadores(){
		return this.barbudi.getAllEspectadores();
	}
	
	public ArrayList<String> getAllEmpleados(){
		return this.barbudi.getAllEmpleados();
	}

	@Override
	public String getEmpleadoName() {
		return this.barbudi.getEmpleadoName();
	}

	@Override
	public String[] getTop5Jugadores() {
		return this.barbudi.getTop5Jugadores();
	}

	@Override
	public String[] getTop5Espectadores() {
		return this.barbudi.getTop5Espectadores();
	}

	@Override
	public void devolverDineroATodosLateral() {
		this.barbudi.devolverDineroATodosLateral();
	}

	@Override
	public void repartirDineroRondaLateral(boolean ganoTirador) {
		this.barbudi.repartirDineroRondaLateral(ganoTirador);
	}

	@Override
	public void estadoJugadores() {
		this.barbudi.estadoJugadores();
	}

	@Override
	public void retirarJugador(String nombre) {
		this.barbudi.retirarJugador(nombre);		
	}

	@Override
	public void setEmpleado(String empleado) {
		this.barbudi.setEmpleado(empleado);
	}
	
	public String[] getEstadoEspectadores(){
		return this.barbudi.getEstadoEspectadores();
	}

	@Override
	public int getUltimaRonda() {
		if(this.barbudi.getUltimaRonda()==null)
			return 0;
		else
			return 1;
	}
	public int getUltimaApuestaLateral() {
		if(this.barbudi.getUltimaApuestaLateral()==null)
			return 0;
		else
			return 1;
	}
}
