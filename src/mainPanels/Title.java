package mainPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import auxiliary.PanelRound;

/**
The Title class represents the title of each panel.
It extends the PanelRound class, and contains a JLabel that displays the title text and an optional image.
@author Ciprian Banci
@version 1.0
*/
public class Title extends PanelRound{
	/*
	 * this class reprezents the title of each panel
	 */
	private JLabel title;
	
	public Title() {super();}
	
	/**

	Creates a new Title object with the specified title text and image.
	@param title the text to display as the title
	@param image the image to display alongside the title, or null if no image should be displayed
	*/
	public Title(String title,Image image) {
		super();
		this.set_backgroundColor(new Color(217,133,72));
		this.title = new JLabel();
		this.setLayout(new GridLayout(1,1));
		
		this.title.setText(title);
		this.title.setFont(new Font("Times New Roman",Font.BOLD,25));
		this.title.setHorizontalAlignment(JLabel.CENTER);
		
		//image
		if(image != null) {
			ImageIcon imageIcon = new ImageIcon(image);
			this.title.setIcon(imageIcon);
		}
		
		this.add(this.title);
		
		this.setMaximumSize(new Dimension(5000,100));
		this.setMinimumSize(new Dimension(200,100));
		this.setPreferredSize(new Dimension(500,100));
	}
	
}
