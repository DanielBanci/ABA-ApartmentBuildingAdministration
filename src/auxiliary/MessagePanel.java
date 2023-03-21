package auxiliary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
/**
 * A costum panel to display message
 * it has rounded borders and a stroke in the left or right side
 * @author Daniel
 * @version 1.0
 */
public class MessagePanel extends JPanel {
	protected int strokeSize = 1;
    protected Color _shadowColor = Color.BLACK;
    protected boolean shadowed = true; 
    protected boolean _highQuality = true;
    protected Dimension _arcs = new Dimension(30, 30);
    protected int _shadowGap = 5;
    protected int _shadowOffset = 4;
    protected int _shadowAlpha = 150;
    
    private Color color;
    private int thickness = 4;
    private int radii = 8;
    private int pointerSize = 7;
    private Insets insets = null;
    private BasicStroke stroke = null;
    private int strokePad;
    private int pointerPad = 4;
    private boolean left = true;
    RenderingHints hints;

    protected Color _backgroundColor = Color.LIGHT_GRAY;
    protected BufferedImage image = null;

    private int roundTopLeft = 50;
    private int roundTopRight = 50;
    private int roundBottomLeft = 50;
    private int roundBottomRight = 50;

    public MessagePanel() {
    	super();
        setOpaque(false);
    }
    
    public MessagePanel(BufferedImage img) {
    	super();
    	this.image = img;
        setOpaque(false);
    }
    
    /**
     * Constructor of the class.
     * @param color the background color
     * @param thickness the thickness
     * @param radii the radii
     * @param pointerSize the poin size
     * @param left stroke position
     */
    public MessagePanel(Color color, int thickness, int radii, int pointerSize,boolean left) {
        this.thickness = thickness;
        this.radii = radii;
        this.pointerSize = pointerSize;
        this.color = color;
        this.left = left; 

        stroke = new BasicStroke(thickness);
        strokePad = thickness / 2;

        hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = radii + strokePad;
        int bottomPad = pad + pointerSize + strokePad;
        insets = new Insets(pad, pad, bottomPad, pad);
        
        this.setBackground(color);
    }
    @Override
    protected void paintComponent(Graphics grphcs) {
    	int width = getWidth();
        int height = getHeight();
        int shadowGap = this._shadowGap;
        Color shadowColorA = new Color(_shadowColor.getRed(), _shadowColor.getGreen(), _shadowColor.getBlue(), _shadowAlpha);
        Graphics2D g2 = (Graphics2D) grphcs.create();

        int bottomLineY = height - thickness - pointerSize;

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(
                0 + strokePad,
                0 + strokePad,
                width - thickness,
                bottomLineY,
                radii,
                radii);
        
        Polygon pointer = new Polygon();

        if (left) {
            // left point
            pointer.addPoint(
                    strokePad + radii + pointerPad,
                    bottomLineY);
            // right point
            pointer.addPoint(
                    strokePad + radii + pointerPad + pointerSize,
                    bottomLineY);
            // bottom point
            pointer.addPoint(
                    strokePad + radii + pointerPad + (pointerSize / 2),
                    height - strokePad);
        } else {
            // left point
            pointer.addPoint(
                    width - (strokePad + radii + pointerPad),
                    bottomLineY);
            // right point
            pointer.addPoint(
                    width - (strokePad + radii + pointerPad + pointerSize),
                    bottomLineY);
            // bottom point
            pointer.addPoint(
                    width - (strokePad + radii + pointerPad + (pointerSize / 2)),
                    height - strokePad);
        }

        Area area = new Area(bubble);
        area.add(new Area(pointer));
        Shape clip = g2.getClip();
        Area recta = new Area(clip);
        recta.subtract(area);
        
        
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(stroke);
        g2.setColor(getBackground());
        g2.fill(area);
        g2.dispose();
        super.paintComponent(g2);
    }

	public Color get_backgroundColor() {
		return _backgroundColor;
	}

	public void set_backgroundColor(Color _backgroundColor) {
		this._backgroundColor = _backgroundColor;
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
