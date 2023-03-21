package calendar;

/**

This class represents a point on a table with a row and a column index.
@author Ciprian Banci
@version 1.0
*/
public class Point{

	private int row;
	private int column;
	
	/**
	Constructs a new point with row and column indices set to 0.
	*/
	public Point() {};
	
	/**
	Constructs a new point with the specified row and column indices.
	@param row the row index of the point
	@param column the column index of the point
	*/
	public Point(int row,int column) {
		this.row = row;
		this.column = column;
	}
	
	/**

	Checks if this point is equal to another point.
	@param p the point to compare to
	@return true if this point has the same row and column indices as the given point, false otherwise
	*/
	public boolean equalsTo(Point p) {
		return this.row == p.row && this.column == p.column;
	}
	
	/**
	Gets the row index of the point.
	@return the row index
	*/
	public int getRow() {
		return row;
	}
	/**
	Sets the row index of the point.
	@param row the new row index
	*/
	public void setRow(int row) {
		this.row = row;
	}
	/**
	Gets the column index of the point.
	@return the column index
	*/
	public int getColumn() {
		return column;
	}
	/**
	Sets the column index of the point.
	@param column the new column index
	*/
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	Returns a string representation of the point.
	@return a string containing the row and column indices of the point
	 */
	@Override
	public String toString() {
		return "Point [x=" + row + ", column=" + column + "]";
	}
}
