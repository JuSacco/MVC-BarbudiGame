package application;


import javax.swing.JOptionPane;

import controlador.*;
import modelo.Barbudi;
import vista.consola.Consola;
import vista.gui.Principal;


public class testApp {

	public static void main(String[] args) {
		new JOptionPane();
		int seleccion = JOptionPane.showConfirmDialog(null, "¿Desea ingresar con la vista grafica?", "Selector de vista", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(seleccion == 0){
			Barbudi modelo = new Barbudi();
			Controlador controlador = new Controlador(modelo);
			Principal iniciar = new Principal("Barbudi",controlador);
			iniciar.setVisible(true);
			modelo.addObserver(iniciar);
		}else{
			Barbudi modelo = new Barbudi();
			Controlador controlador = new Controlador(modelo);
			Consola iniciar = new Consola(controlador);
			modelo.addObserver(iniciar);
		}
	}

}
