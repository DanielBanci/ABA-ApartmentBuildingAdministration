package auxiliary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
A customized JPanel with rounded corners and drop shadow effect.
@author Ciprian Banci
@version 1.0
*/
public class PanelRound extends JPanel {

    protected int strokeSize = 1; 
    protected Color shadowColor = Color.BLACK;
    protected boolean shadowed = true;
    protected boolean highQuality = true;
    protected Dimension arcs = new Dimension(30, 30);
    protected int shadowGap = 5;
    protected int shadowOffset = 4;
    protected int shadowAlpha = 150;
    protected Color backgroundColor = Color.LIGHT_GRAY;
    protected BufferedImage image = null;

    private int roundTopLeft = 50;
    private int roundTopRight = 50;
    private int roundBottomLeft = 50;
    private int roundBottomRight = 50;

    /**

    Creates a new instance of PanelRound.
    The panel is set to be opaque false by default.
    */
    public PanelRound() {
    	super();
       setOpaque(false);
    }
    
    /**

    Creates a new instance of PanelRound with a background image.
    The panel is set to be opaque false by default.
    @param img the image to be set as the background of the panel
    */
    public PanelRound(BufferedImage img) {
    	super();
    	this.image = img;
        setOpaque(false);
    }
    
    /**
    Overrides the paintComponent method of JPanel to customize the panel's appearance.
    @param grphcs the graphics context to be used to paint the panel
    */
    @Override
    protected void paintComponent(Graphics grphcs) {
    	int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) grphcs.create();

        if(highQuality)
        {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if(shadowed)
        {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(shadowOffset, shadowOffset, width - strokeSize - shadowOffset,
                    height - strokeSize - shadowOffset, arcs.width, arcs.height);
        }
        else
        {
            shadowGap = 1;
        }

        RoundRectangle2D.Float rr = new RoundRectangle2D.Float(0, 0, (width - shadowGap), 
        		(height - shadowGap), arcs.width, arcs.height);

        Shape clipShape = graphics.getClip();

        if(image == null)
        {
            graphics.setColor(backgroundColor);
            graphics.fill(rr);
        }
        else
        {
            RoundRectangle2D.Float rr2 =  new RoundRectangle2D.Float(0, 0, (width - strokeSize - shadowGap), 
            		(height - strokeSize - shadowGap), arcs.width, arcs.height);

            graphics.setClip(rr2);
            graphics.drawImage(this.image, 0, 0, null);
            graphics.setClip(clipShape);
        }

        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.draw(rr);
        graphics.setStroke(new BasicStroke());
    	
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.dispose();
        super.paintComponent(grphcs);
    }

	public Color get_backgroundColor() {
		return backgroundColor;
	}

	public void set_backgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
        repaint();
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
        repaint();
    }
}
