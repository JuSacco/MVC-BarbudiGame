package vista.gui;

import java.io.File;

import javax.swing.JFileChooser;

public class ExploradorDeArchivos extends JFileChooser{

	private static final long serialVersionUID = 1L;
	private File archivo;
	
	
	public ExploradorDeArchivos(){
		this.setFileSelectionMode(FILES_AND_DIRECTORIES);
	}

	/**
	 * Ventana para abrir archivo
	 */
	@SuppressWarnings("static-access")
	public String abrirArchivo(){
		this.setVisible(true);
		int resultado = this.showOpenDialog(null);
		if(resultado == this.CANCEL_OPTION){
			this.setVisible(false);
			return "cancelo";
		}
		System.out.println("Resultado = "+resultado);
		this.archivo = this.getSelectedFile();
		if(archivo == null || archivo.getName().equals(""))
			return "error";
			
		return "exito";
	}
	
	/**
	 * Ventana para guardar archivo
	 */
	@SuppressWarnings("static-access")
	public String guardarArchivo(){
		int resultado = this.showSaveDialog(null);
		if(resultado == this.CANCEL_OPTION)
			this.setVisible(false);
		this.archivo = this.getSelectedFile();
		if(archivo == null || archivo.getName().equals("")){
			return "Error";
		}
		this.setArchivo(archivo);

		return "exito";
	}
	
	private void setArchivo(File archivo){
		this.archivo = archivo;
	}
	
	
	/**
	 * Ruta del archivo
	 * @return
	 */
	public String getArchivo(){
		if(archivo != null)
			return archivo.getAbsolutePath();
		else
			return "";
	}

}
