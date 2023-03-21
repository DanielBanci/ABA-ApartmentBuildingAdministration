package auxiliary;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
A listener class that implements the DocumentListener interface and is used to check if 
a document has been updated by changing the content color.
@author Ciprian Banci
@version 1.0
*/
public class CheckCreatedDocL implements DocumentListener{
	private PanelRound content;

	/**
	Constructs a CheckCreatedDocL object with the specified panel object. 
	@param content the PanelRound object to be updated when a document is changed.
	*/
	public CheckCreatedDocL(PanelRound content) {
		super();
		this.content = content;
	}

	/**
	Invoked when text is inserted into the document.
	Sets the background color of the PanelRound object to Color.LIGHT_GRAY and repaints it.
	@param e the document event that triggered the update
	*/
	@Override
	public void insertUpdate(DocumentEvent e) {
		content.set_backgroundColor(Color.LIGHT_GRAY);
		content.repaint();
	}
	/**
	Invoked when text is removed from the document.
	Sets the background color of the PanelRound object to Color.LIGHT_GRAY and repaints it.
	@param e the document event that triggered the update
	*/
	@Override
	public void removeUpdate(DocumentEvent e) {
		content.set_backgroundColor(Color.LIGHT_GRAY);
		content.repaint();
	}
	/**
	Invoked when text is changed in the document.
	Sets the background color of the PanelRound object to Color.LIGHT_GRAY and repaints it.
	@param e the document event that triggered the update
	*/
	@Override
	public void changedUpdate(DocumentEvent e) {
		content.set_backgroundColor(Color.LIGHT_GRAY);
		content.repaint();
	}
}
