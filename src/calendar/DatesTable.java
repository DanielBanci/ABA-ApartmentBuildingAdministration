package calendar;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import mainClasses.Association;

/**
A table that displays dates and highlights certain cells with different colors based on their significance.
*/
public class DatesTable extends JTable{
	private List<Point> pointOfinterest;
	private Point firstDay;
	private Point lastDay;
	private Point currentDay;
	private String[][] data;
	private Association association;
	
	Class[] columnTypes = new Class[] {
			String.class, String.class, String.class, String.class, String.class, String.class, String.class
	}; 
	/**

	Returns the data type of the column at the specified index.
	@param columnIndex the index of the column
	@return the data type of the column
	*/
	public Class getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}
	/**
	An array of booleans that indicate whether a cell in a specific column is editable or not.
	*/
	boolean[] columnEditables = new boolean[] {
			false, false, false, false, false, false, false
	};
	/**

	Returns whether the cell at the specified row and column is editable or not.
	@param row the row index of the cell
	@param column the column index of the cell
	@return true if the cell is editable, false otherwise
	*/
	public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}
	
	/**
	Constructs a DatesTable with the specified data, column names, association, first day, last day, current day,
	and points of interest.
	@param data a two-dimensional array of Strings that represents the data to be displayed in the table
	@param columnNames an array of Strings that represent the names of the columns
	@param association the association object associated with the table
	@param firstDay the Point that represents the first day of the month to be displayed in the table
	@param lastDay the Point that represents the last day of the month to be displayed in the table
	@param currentDay the Point that represents the current day of the month
	@param pointOfinterest a list of points corresponding to cells that are of special interest and should be 
	highlighted
	*/
	public DatesTable(String[][] data,String[] columnNames,Association association,
			Point firstDay, Point lastDay, Point currentDay, List<Point> pointOfinterest) {
		
		super(data,columnNames);
		
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.currentDay = currentDay;
		this.pointOfinterest = pointOfinterest;
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		//column model
		for(int i=0;i<6;i++)
			this.setRowHeight(i,30);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<7;i++) {
			this.getColumnModel().getColumn(i).setMaxWidth(30);
			this.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
		}
	}
	
	/**
	Returns the component used to render the cell at the specified row and column. 
	Customizes the background color of the cell based on the current date, first day, 
	last day, and points of interest.
	@param renderer the renderer to be used
	@param row the row of the cell
	@param column the column of the cell
	@return the component render
	*/
	@Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        Point currentPosition = new Point(row,column);
        c.setBackground(Color.WHITE);
        if( //coloring days from previous month and the next one
        		(currentPosition.getRow() == firstDay.getRow() && currentPosition.getColumn() < firstDay.getColumn()) ||
        		(currentPosition.getRow() == lastDay.getRow() && currentPosition.getColumn() > lastDay.getColumn() ||
        		currentPosition.getRow() > lastDay.getRow())
        		){
        	
            c.setBackground(Color.LIGHT_GRAY);
            
        }else if(//current date
        		currentDay.equalsTo(currentPosition)
        		){
        	c.setBackground(Color.RED);
        	
        }else {
        	c.setBackground(Color.WHITE);
        }
        
        //colorint the paying dates
        for(Point p : pointOfinterest) {
        	if(p.equalsTo(currentPosition)) {
        		c.setBackground(Color.GREEN);
        		if(p.equalsTo(currentDay)) {
        			c.setBackground(Color.ORANGE);
        		}
        	}
        }

        return c;
    }
	
	
}


