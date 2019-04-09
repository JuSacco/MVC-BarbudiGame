package vista.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Imagen extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image imagen;
		private boolean proporcional = true;
		private JLabel texto = new JLabel("");


		public Imagen() {
			super();
			this.setLayout(null);
			texto.setAlignmentX(CENTER_ALIGNMENT);
			texto.setAlignmentY(CENTER_ALIGNMENT);
			this.add(texto);
		}

		public void setTexto(String texto){
			this.texto.setText(texto);
		}
		
		public String getTexto(){
			return this.texto.getText();
		}
		
		public Imagen(String nombreImagen) {
			super();
			this.setImagen(nombreImagen);
			setSize(500,500);
		}

		public void setProporcional(boolean valor) {
			proporcional = valor;
		}

		public boolean getProporcional() {
			return proporcional;
		}

		public Imagen(Image imagenInicial) {
			if (imagenInicial != null) {
				imagen = imagenInicial;
			}
		}

		public void setImagen(String nombreImagen) {
			if (nombreImagen != null) {
				imagen = new ImageIcon(getClass().getResource(nombreImagen)).getImage();
			} else {
				imagen = null;
			}

			repaint();
		}

		public void setImagen(Image nuevaImagen) {
			imagen = nuevaImagen;

			repaint();
		}

		@Override
		public void paint(Graphics g) {
			if (imagen != null) {

				int largo = imagen.getHeight(null);
				int ancho = getHeight() * imagen.getWidth(null) / largo;
				int vpLargo = getHeight();
				int vpAncho = ancho;
			
				if (ancho >= getWidth()){
					ancho = imagen.getWidth(null);
					vpLargo = getWidth() * imagen.getHeight(null) / ancho;
					vpAncho = getWidth();
				}
			
				g.drawImage(imagen, 0, 0, vpAncho , vpLargo, this);
				
				texto.setFont(new java.awt.Font("Tahoma", 0, vpLargo * 20 / 100)); 
				texto.setHorizontalAlignment(SwingConstants.CENTER);
				texto.setBounds(0, 0, vpAncho, vpLargo);
				setOpaque(false);
			} else {
				setOpaque(true);
			}

			super.paint(g);
		}
	
}
