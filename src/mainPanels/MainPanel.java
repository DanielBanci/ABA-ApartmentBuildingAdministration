package mainPanels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
The MainPanel class extends JPanel and is responsible for drawing the main 
background image on the panel.
@author Ciprian Banci
@version 1.0
*/
public class MainPanel extends JPanel{
	private Image background;
	
	/**
	Constructor for the MainPanel class.
	Loads the background image from the resources folder and sets it as the panel's background.
	*/
	public MainPanel() {
		super();
		//load image
		background = (new ImageIcon(this.getClass().getResource("/brickBackground.jpg"))).getImage();
	}
	
	/**
	Overrides the paintComponent method of the JPanel class to draw the background image on the panel.
	@param g The Graphics object to draw the image with.
	*/
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null); 
        this.setOpaque(true);
    }
}
