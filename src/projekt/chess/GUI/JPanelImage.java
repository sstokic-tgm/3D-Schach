package projekt.chess.GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * JPanelImage is used to display each position of the chess board on the UI.<br>
 * Each square of the board has a background color represented by a coloured layout.
 */
public class JPanelImage extends JPanel {

	/* Attributs */
	private ImageIcon imageIcon = null;

	/* Getters & Setters */
	public ImageIcon getImageIcon() {

		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {

		this.imageIcon = imageIcon;
	}

	/* Constructor(s) */
	public JPanelImage() {

		((FlowLayout) this.getLayout()).setVgap(0);
	}	

	/* Paint method */

	@Override
	public void paintComponent(Graphics g) {	

		if(imageIcon != null) {	

			Image image = imageIcon.getImage();	

			int height = 35;
			int width = 35;

			int marginLeft = (this.getWidth()-width) / 2;
			int marginTop = (this.getHeight()-height) / 2;

			super.paintComponent(g);						
			g.drawImage(image, marginLeft, marginTop, width, height, this);

		}else
			super.paintComponent(g);
	}

	/* ColoredLayout methods */	

	/**
	 * Create a JPanel and give it a background color
	 * @param color
	 */
	public void addColoredLayout(Color color) {

		JPanel upperLayout = new JPanel();

		upperLayout.setBackground(color);
		upperLayout.setPreferredSize(this.getPreferredSize());

		this.add(upperLayout);
	}

	/**
	 * Remove the colored layout from this
	 */
	public void cleanColoredLayout() {

		if(this.getComponentCount() >= 1) {

			this.remove(0);
			this.repaint();
		}		
	}
}