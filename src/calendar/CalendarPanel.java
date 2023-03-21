 package calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import appInterface.AdminAccount;
import appInterface.ApartmentAccount;
import auxiliary.PanelRound;
import auxiliary.RoundedBorder;

import mainClasses.Admin;
import mainClasses.Association;
import mainClasses.Database;
import mainClasses.User;

/**

CalendarPanel class represents a JPanel with a calendar table and a legend panel 
for a specific month.
It extends the RoundedPanel class and initializes the title panel, content panel, 
and data that will be presented on the calendar.
@author Ciprian Banci
@version 1.0
*/
public class CalendarPanel extends PanelRound{
	private JPanel title; // panel containing the month and year title
	private JScrollPane datesTable; // table that represents a calendar month
	private JPanel content; // calendar panel container
	private Color backgroundColor; // background color of the panel
	private static List<Point> pointOfinterest; // list containing the date points of interest
	private static Point firstDay; // the first day of the current month
	private static Point lastDay; // the last day of the current month
	private static Point currentDate; // the current date
	private String[][] data; // data that will be displayed on the calendar
	
	/**

	Constructs a new CalendarPanel object and initializes its fields.
	@param user the user for which to make the calendar
	@param association the association to be used to make the calendar
	*/
	public CalendarPanel(User user,Association association) {
		super(null); 
		
		pointOfinterest = new ArrayList<>();

		//Color color = new Color(184,238,245);
		backgroundColor = Color.cyan;
		content = new JPanel();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

		content.add(ApartmentAccount.makeEmptyPanel());
		
		//title; month + year display
		title = new JPanel();
		title.setLayout(new GridLayout(1,1));
		
		JLabel titleLbl = new JLabel();
		titleLbl.setText(AdminAccount.getCurrentMonth(new Date()).toUpperCase() + " " + LocalDate.now().getYear());
		titleLbl.setFont(new Font("Times New Roman",Font.BOLD,16));
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		titleLbl.setBorder(new RoundedBorder(Color.GRAY,3,10,0));
		
		title.add(titleLbl);
		title.setPreferredSize(new Dimension(50,50));
		//this.add(title);
		content.add(title);
		
		//calendar
		datesTable = makeCalendar(user,association);

		content.add(datesTable);
		
		//legend
		JPanel legend = makeLegend(); 
		
		content.add(legend);

		this.setMaximumSize(new Dimension(300,330));
		this.setPreferredSize(new Dimension(300,330));
		this.setBackground(backgroundColor);

		content.setPreferredSize(new Dimension(210,310));
		content.setBackground(backgroundColor);
		title.setBackground(backgroundColor);
		this.set_backgroundColor(backgroundColor);
		this.add(content);

	}
	
	/**
	 * Makes a panel that contain the legend of the calendar
	 * red for current date
	 * green for paying dates
	 * white for current month dates
	 * @return a panel containing the legend of the chat
	 */
	private JPanel makeLegend() {
		JPanel legend = new JPanel();
		legend.setLayout(new BoxLayout(legend,BoxLayout.Y_AXIS));
		legend.setOpaque(false);
		
		//line 1
		JPanel line1 = new JPanel();
		line1.setLayout(new FlowLayout());
		line1.setOpaque(false);
		
		JPanel color1 = new JPanel();
		color1.setPreferredSize(new Dimension(10,10));
		color1.setBackground(Color.red);
		
		JLabel color1l = new JLabel("Data curenta");
		
		JPanel color2 = new JPanel();
		color2.setPreferredSize(new Dimension(10,10));
		color2.setBackground(Color.green);
		
		JLabel color2l = new JLabel("Zile de plata");
		
		line1.add(color1);
		line1.add(color1l);
		line1.add(color2);
		line1.add(color2l);
		
		JPanel line2 = new JPanel();
		line2.setLayout(new FlowLayout());
		line2.setOpaque(false);
		
		JPanel color3 = new JPanel();
		color3.setPreferredSize(new Dimension(10,10));
		color3.setBackground(Color.WHITE);
		
		JLabel color3l = new JLabel("Luna curenta");
				
		line2.add(color3);
		line2.add(color3l);
		
		legend.add(line1);
		legend.add(line2);
		
		return legend;
	}
	
	/**
	 * Makes the calendar panel for user
	 * @param user the user for which the calendar is made
	 * @param association the association of the user
	 * @return a JScrollPane containing a table(=calendar)
	 */
	private JScrollPane makeCalendar(User user,Association association) {
		
		//table for calendar
		String[] columnNames = new String[] {
				"Lun","Mar","Mie","Joi","Vin","Sam","Dum"
		};
		
		data = makeColumnData(association);
		
		JTable datesTable = new DatesTable(data,columnNames,association,firstDay,lastDay,currentDate,pointOfinterest); 
			
		if(user instanceof Admin) {
			datesTable.addMouseListener(makeMouseAdapter(association));
		}
		
		JScrollPane pane = new JScrollPane(datesTable,JScrollPane.VERTICAL_SCROLLBAR_NEVER
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		return pane;
	}
	
	/**
	 * Makes mouse adapter for admin user
	 * Double click to set a date as paying date and double click again
	 * to reset it
	 * @param association
	 * @return
	 */
	private MouseAdapter makeMouseAdapter(Association association) {
		
		MouseAdapter act = new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			    if (e.getClickCount() == 2) {
			    	
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      
			      //check if date is from this month and is at least today or later
			      if(!(row == firstDay.getRow() && column < firstDay.getColumn() || 
			    	row == lastDay.getRow() && column > lastDay.getColumn()) && !(row > lastDay.getRow() 
			    	) && (row >= currentDate.getRow() && column >= currentDate.getColumn())) {
			    	  //save the point
			    	  Point newP = new Point(row,column);
			    	  
			    	  boolean exists = false;
			    	  for(Integer i=0;i<pointOfinterest.size();i++) {
			    		  if((pointOfinterest.get(i)).equalsTo(newP)) {//remove if created before
			    			  pointOfinterest.remove(pointOfinterest.get(i));
			    			  exists = true;
			    		  }
			    	  }
			    	  
			    	  //delete from association
			    	  for(Integer i=0;i<association.getPayingDates().size();i++) {
			    		  Calendar calendar = Calendar.getInstance();
			    		  calendar.setTime(association.getPayingDates().get(i));
			    		  if(calendar.get(Calendar.DAY_OF_MONTH) == 
			    				  Integer.valueOf(target.getModel().getValueAt(row, column).toString()) ) {
			    			  association.getPayingDates().remove(association.getPayingDates().get(i));
			    		  }
			    	  }
			    	  
			    	  if(!exists) {//if not created before, save the new date
			    		  pointOfinterest.add(newP);
			    		  //get the new date and save it in association
			    		  LocalDate date = LocalDate.now();
			    		  java.sql.Date sqlDate = new java.sql.Date(date.getYear()-1900,date.getMonthValue()-1,
			    				  Integer.valueOf((String) target.getModel().getValueAt(row, column)));
			    		  association.getPayingDates().add(sqlDate);
			    		  
			    	  }
			    	  
			    	  Database.getInstance().insertDates(association);
			    	  datesTable.repaint();
			    	  
			      }
			    	  
			    }
			  }
			};
			return act;
	}
	
	/**
	 * Makes a poin from the given date
	 * @param date the date we need the point of
	 * @return a point of the date
	 */
	public static Point makeDatePoint(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);

	    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	    int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);//actually starts from 1
	    
	    dayOfWeek = convertColumn(dayOfWeek);
	    
	    return new Point(weekOfMonth,dayOfWeek);
	    
	  }
	/**
	 * Convert the column to match: 1-mon,2-tue,...,7-sun
	 * @param column reprezents a day of week matching: 1-sun,2-mon,...,7-sat
	 * @return column of the day in calendar
	 */
	public static int convertColumn(int column) {
		column = (column + 6) % 7 == 0 ? 7 : (column + 6) % 7; 
		column--;
		return column;
	}

	/**
	 * Makes data of the calendar
	 * days from previous month (as needed for fill the empty space)
	 * days from the next month same as before
	 * days of current month
	 * set the dates of interest (points in dates[] table)
	 * @param association the association to display the paying dates for
	 * @return the calendar data
	 */
	public static String[][] makeColumnData(Association association){
		/*
		 * dates for table
		 * days from previous month (as needed for fill the empty space)
		 * days from the next month same as before
		 * days of current month
		 * set the dates of interest (points in dates[] table)
		 */
		String[][] dates = new String[6][7];
		Calendar cal = Calendar.getInstance();
		
		//get the start of month
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, 1);//to get the 1st day of month e.g. mon, tue s.o.
		int column = cal.get(Calendar.DAY_OF_WEEK);//1-sun,2-mon s.o.
		
		//convert column to get 1-mon etc
		column = convertColumn(column);
		
		//retain the position for first day of month
		firstDay = new Point(0,column);
		
		//values from previous month
		if(column != 0) {
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.MONTH, -1);
			int maxDay2 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int index = column-1;
			while(index >= 0) {
				dates[0][index] = ((Integer)maxDay2).toString();
				index--;
				maxDay2--;
			}
		}
		Integer day = 1;
		int row = 0;
		//last day of current month
		while(day <= maxDay) {
			dates[row][column] = day.toString();
			lastDay = new Point(row,column);
			//mark the dates of interest
			if(association.getPayingDates() != null) {
				for(Date d : association.getPayingDates()) {
					Calendar aux = Calendar.getInstance();
					aux.setTime(d);
					//paying dates
					if(day == aux.get(Calendar.DAY_OF_MONTH)) {
						pointOfinterest.add(new Point(row,column));
					}
				}
			}
			//current date
			if(day == LocalDate.now().getDayOfMonth()) {
				currentDate = new Point(row,column);
			}
			
			column++;
			day++;
			if(column > 6) {
				column = 0;
				row++;
			}
		}
		//values from next month
		day = 1;
		while(column < 7 && row < 6) {
			dates[row][column] = ((Integer)day).toString();
			column++;
			day++;
			//start again for the next row
			if(column == 7) {
				row++;
				column = 0;
			}
		}
		
		return dates;
	}
}

