package auxiliary;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainClasses.User;

/**
 * The panel that containt the notice board tabel
 * @author Daniel
 * @version 1.0
 */
public class TablePanel extends JPanel{
	private JTableHeader dummyHeader;
	private JTableHeader header;
	private JScrollPane pane;
	JTable table;
	public String[][] columnData;

	/**
	 * Constructor of the class. Make the panel and add the notice board in it.
	 * @param user the current user 
	 * @param association the association of the user
	 * @param frame the frame that contain the panel
	 */
	public TablePanel(User user, Association association, JFrame frame) {
		/*
		 * makes notice board panel and put it on contentPane
		 */
		super();
		//making the table
		frame.pack();
		table = new JTable();
		
		//refresh data
		association = Database.getInstance().getAssociation(user);
		columnData = makeColumnData(user, association);
		String[] columnNames = new String[] {
				"Nr. ap.", "Mp", "Nr. pers.", "F reparatii", "F rulment", "F imbunatatiri", "Elect", "Salarii", "Apa calda", 
				"Apa rece", "Apa calda", "Apa rece", "Gaz", "Gunoi", "Incalzire", "Total luna", "Penalizari", "Restante", 
				"Total Plata"
		};
		table.setModel(new DefaultTableModel(
			columnData,
			columnNames
		   ) {
		   	Class[] columnTypes = new Class[] {
		   		String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, 
		   		String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, 
		   		String.class, String.class, String.class
		   	};
		   	public Class getColumnClass(int columnIndex) {
		   		return columnTypes[columnIndex];
		   	}
		   	boolean[] columnEditables = new boolean[] {
		   		false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
		   		false, false, false
		   	};
		   	public boolean isCellEditable(int row, int column) {
		   		return columnEditables[column];
		   	}
		});
		//set minimum width to all columns
		int minWidth = 70;
		for(int i=0;i<columnNames.length;i++) {
			table.getColumnModel().getColumn(i).setMinWidth(minWidth);
		}
		
		//set width to all columns
		int columnWidth = 70;
		for(int i=0;i<columnNames.length;i++) {
			table.getColumnModel().getColumn(i).setMinWidth(columnWidth);
		}
		
		//setting size for column with different size
		table.getColumnModel().getColumn(5).setWidth(100);
		table.getColumnModel().getColumn(5).setMinWidth(100);
		
		//setting size of the table based on columns dimensions
		if( user instanceof Apartment) {
			table.setPreferredSize(new Dimension( columnWidth*19 + 30, 16 * 3));
		}
		else
			table.setPreferredSize(new Dimension( columnWidth*19 + 30, 16 * (association.getApartments().size()+2)));
	    
		//making the header of header
	    final TableColumnModel model = table.getColumnModel();
	    header = table.getTableHeader();
	    header.setReorderingAllowed(false);
	    Enumeration<TableColumn> enumColumns = model.getColumns();
	    final List<TableColumn> columns = Collections.list(enumColumns);
	      
	    final JTable dummy = new JTable();
	    String[] headerValues = new String[] {
	    		"", "Cheltuieli / cota parte", "Consum", "Total", "Cheltuieli / nr. persoane",
	    		"Chelt./mp", ""
	    };
	    dummy.setModel(new DefaultTableModel(
		    	null,
		    	headerValues
			    ) {
			    	Class[] columnTypes = new Class[] {
			    		String.class, String.class, String.class, String.class, String.class
			    	};
			    	public Class getColumnClass(int columnIndex) {
			    		return columnTypes[columnIndex];
			    	}
			    });
	    dummyHeader = dummy.getTableHeader();
	    dummyHeader.setReorderingAllowed(false);
	    dummyHeader.setResizingAllowed(false);
	    final TableColumnModel dummyModel = dummy.getColumnModel();
	    Enumeration<TableColumn> enumDummyColumns = dummyModel.getColumns();
	    final List<TableColumn> dummyColumns = Collections.list(enumDummyColumns);
	      
	    model.addColumnModelListener(new TableColumnModelListener() {

	         @Override
	         public void columnAdded(TableColumnModelEvent e) {
	         }

	         @Override
	         public void columnRemoved(TableColumnModelEvent e) {
	         }

	         @Override
	         public void columnMoved(TableColumnModelEvent e) {
	         }

	         @Override
	         public void columnMarginChanged(ChangeEvent e) {
	            dummyColumns.get(0).setWidth(columns.get(0).getWidth()+
	                    columns.get(2).getWidth() + columns.get(1).getWidth());
	            dummyColumns.get(1).setWidth(columns.get(3).getWidth() + columns.get(4).getWidth() 
	            		+ columns.get(5).getWidth() + columns.get(6).getWidth() + columns.get(7).getWidth());
	            dummyColumns.get(2).setWidth(columns.get(8).getWidth() + columns.get(9).getWidth());
	            dummyColumns.get(3).setWidth(columns.get(10).getWidth() + columns.get(11).getWidth());
	            dummyColumns.get(4).setWidth(columns.get(12).getWidth() + columns.get(13).getWidth());
	            dummyColumns.get(5).setWidth(columns.get(14).getWidth());
	            dummyColumns.get(6).setWidth(columns.get(15).getWidth() + columns.get(16).getWidth()
	            		+ columns.get(17).getWidth() + columns.get(18).getWidth());
	         }

	         @Override
	         public void columnSelectionChanged(ListSelectionEvent e) {
	         }
	      });
	    table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
	    pane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    int height; 
	    
	    height = (association.getApartments().size() + 2) * 16 + 58;//table line * 16 + table header
	    if(user instanceof Apartment) height = 3*16 + 58; //just 3 lines
	    height = height > 425 ? height = 425 : height;//if too big
	    pane.setMaximumSize(new Dimension(columnWidth*19 + 30,height));

	    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	    this.add(pane);
	    
	    refreshTable(frame);
	}
	
	/**
	 * Refresh the header of the table;
	 * @param frame frame that has the table
	 */
	public void refreshTable(JFrame frame) {
		 JPanel tableHeader = new JPanel();
		 tableHeader.setLayout(new GridLayout(2,1));
		 tableHeader.add(dummyHeader);
		 tableHeader.add(header);
		 pane.setColumnHeaderView(tableHeader);
		 frame.pack();
	}
	
	/**
	 * Calculate warming price by devide the price with the total square meters
	 * of apartments that do not have a warming contor
	 * @param association the current association
	 * @return the warming price per square meter
	 */
	public static Double makeWarmingPrice(Association association) {
		Double warmingPrice = 0.0; 
		//getting total area for warmin where is the case
		Double totalArea = association.getTotalArea();
		for(Apartment ap : association.getApartments()) {
			if(ap.getCentralHeating()) {
				totalArea -= ap.getArea();
			}
		}
		//calculate the price
		if(totalArea == 0.0)
			warmingPrice = 0.0;
		else
			warmingPrice =(association.getWarmingBill()/totalArea)/1.0;
		
		return warmingPrice;
	}
	
	/**
	 * Calculate gas price by devide the price with the total people number
	 * of apartments that do not have a gas contor
	 * @param association the current association
	 * @return the gas price per person
	 */
	public static Double makeGasPrice(Association association) {
		Double gasPrice = 0.0;//association.getGasBill()/association.getPeopleNoTotal()*1.0;
		Double gasBill = association.getGasBill();
		Integer peopleNo = association.getPeopleNoTotal();
		
		//substract the people that has gas contor
		for(Apartment ap : association.getApartments()) {
			if(ap.getGasContor()) {
				peopleNo -= ap.getPeopleNo();
			}
		}
		//calculating the price
		if(peopleNo == 0)
			gasPrice = 0.0;
		else
			gasPrice = gasBill/peopleNo;
		
		return gasPrice;
	}
	
	/**
	 * Make the notice board data by calculating the values for all apartments
	 * Prices are calculated as folow
	 * repair,rul,update fund, salaries and stair electricity /apartment
	 * water /consum
	 * gas /number of people if no gas contor 
	 * trasg /number of people
	 * warmin /square meters if no warming contor
	 * @param user the current user 
	 * @param association the association for which the data will be calculated
	 * @return the notice board data
	 */
	public static String[][] makeColumnData(User user, Association association) {
		/*
		 * make data for notice board
		 */
		
		//calculate the prices
		Double repairFundPrice = association.getRepairFund()/association.getApartments().size()/1.0;
		Double rulFundPrice = association.getRulFund()/association.getApartments().size()/1.0;
		Double updateFundPrice = association.getUpdateFund()/association.getApartments().size()/1.0;
		Double electBillPrice = association.getElectBill()/association.getApartments().size()/1.0;
		Double salariesPrice = association.getSalaries()/association.getApartments().size()/1.0;
		Double gasPrice = makeGasPrice(association);
		Double warmingPrice = makeWarmingPrice(association);

		//making tabel model
		int size = user instanceof Apartment ? 3 : association.getApartments().size()+2;
		String[][] data = new String[size][19];
		
		//making pices row
		data[0][0] = "Pret ->";data[0][1]="-";data[0][2]="-";
		data[0][3]= ((Double)(Math.round(repairFundPrice*100)/100.0)).toString();
		data[0][4] = ((Double)(Math.round(rulFundPrice*100)/100.0)).toString();
		data[0][5] = ((Double)(Math.round(updateFundPrice*100)/100.0)).toString();
		data[0][6] = ((Double)(Math.round(electBillPrice*100)/100.0)).toString();
		data[0][7] = ((Double)(Math.round(salariesPrice*100)/100.0)).toString();
		data[0][8] = "-";data[0][9] = "-";
		data[0][10] = ((Double)(Math.round(association.getwWaterPrice()*100)/100.0)).toString();
		data[0][11]= ((Double)(Math.round(association.getcWaterPrice()*100)/100.0)).toString();
		data[0][12]= ((Double)(Math.round(gasPrice*100)/100.0)).toString();
		data[0][13]= ((Double)(Math.round(association.getTrashPrice()*100)/100.0)).toString();
		data[0][14]= ((Double)(Math.round(warmingPrice*100)/100.0)).toString();
		data[0][15]= "-";data[0][16] = "0.2%";data[0][17]="-";data[0][18] = "-";
		
		//for total row
		Double mpTotal = (double) 0;
		Long peopleNoTotal = (long) 0;
		Double repairFTotal = (double) 0;
		Double rulFTotal = (double) 0;
		Double updateFTotal = (double) 0;
		Double electBillTotal = (double) 0;
		Double salariesTotal = (double) 0;
		Double wWaterTotal = (double) 0;
		Double cWaterTotal = (double) 0;
		Double wWaterPriceTotal = (double) 0;
		Double cWaterPriceTotal = (double) 0;
		Double gasTotal = (double) 0;
		Double trashTotal = (double) 0;
		Double warmingTotal = (double) 0;
		Double monthTotal = (double) 0;
		Double overduePenaltiesTotal = (double) 0;
		Double overdueTotal = (double) 0;
		Double payTotal = (double) 0;
	
		//make data for apartments
		int row = 1;
		boolean apTargetFlag = false;
		boolean adminFlag = user instanceof Admin ? true : false;
		
		for(Apartment apartment : association.getApartments()) {
			//cheching the apartment of interest
			if(!adminFlag)
				apTargetFlag = Apartment.compare(apartment,(Apartment)user) == 0 ? true : false;
			//fill the row with data for target apartment
			if(adminFlag || apTargetFlag) {
				data[row][0] = apartment.getNo().toString();
				data[row][1] = apartment.getArea().toString();
				data[row][2] = apartment.getPeopleNo().toString();
				data[row][3] = ((Double)(Math.round(repairFundPrice*100)/100.0)).toString();
				data[row][4] = ((Double)(Math.round(rulFundPrice*100)/100.0)).toString();
				data[row][5] = ((Double)(Math.round(updateFundPrice*100)/100.0)).toString();
				data[row][6] = ((Double)(Math.round(electBillPrice*100)/100.0)).toString();
				data[row][7] = ((Double)(Math.round(salariesPrice*100)/100.0)).toString();
			}
				
			//updateing totals
			mpTotal += apartment.getArea(); 				
			peopleNoTotal += apartment.getPeopleNo();
			repairFTotal += repairFundPrice;
			rulFTotal += rulFundPrice;
			updateFTotal += updateFundPrice;
			electBillTotal += electBillPrice; 
			salariesTotal += salariesPrice;
				
			//water consum
			//check if the contors was updated this month
			Date updateDate = apartment.getContorUpdateDate();
			LocalDate currentDate = LocalDate.now();
			//getting month from updateDate
			Calendar cal = Calendar.getInstance();
			cal.setTime(updateDate);
			int month = cal.get(Calendar.MONTH); //month starts from 0!
			boolean waterContors = month+1 == currentDate.getMonthValue() ? true : false;
			
			//getting consum
			Double wWConsum = 0.0;
			Double cWConsum = 0.0;
			if(waterContors) {
				//put data on table
				wWConsum = apartment.getwWContorNew() - apartment.getwWContorOld();
				cWConsum = apartment.getcWContorNew() - apartment.getcWContorOld();
				
				if(apartment.getIndividualWContors()) {
					wWConsum += apartment.getwWCNewK() - apartment.getwWCOldK();
					cWConsum += apartment.getcWCNewK() - apartment.getcWCOldK();
				}
				
				if(adminFlag || apTargetFlag) {
					data[row][8] = ((Double)(Math.round(wWConsum*100)/100.0)).toString();
					data[row][9] = ((Double)(Math.round(cWConsum*100)/100.0)).toString();
				}
					
				wWaterTotal += wWConsum;
				cWaterTotal += cWConsum;
			}else if(adminFlag || apTargetFlag){
				data[row][8] = "Lipsa contor";
				data[row][9] = "Lipsa contor";
			}
			
			if(waterContors) {
				//water prices -> based on consum
				if(adminFlag || apTargetFlag) {
					data[row][10] = ((Double)(Math.round(association.getwWaterPrice() * wWConsum * 100)/100.0)).toString();
					data[row][11] = ((Double)(Math.round(association.getcWaterPrice() * cWConsum * 100)/100.0)).toString();
				}
				wWaterPriceTotal += association.getwWaterPrice() * wWConsum;
				cWaterPriceTotal += association.getcWaterPrice() * cWConsum;
			}else if(adminFlag || apTargetFlag){
				data[row][10] = "Lipsa contor";
				data[row][11] = "Lipsa contor";
			}
			
			//gas, trash and warming prices
			//getting warming price
			Double value = 0.0;
			if(!apartment.getCentralHeating()) {
				value = warmingPrice * apartment.getArea();
				//value = ;
			}
			
			if(adminFlag || apTargetFlag) {
				data[row][12] = ((Double)(Math.round(gasPrice * (apartment.getGasContor() ? 0 : apartment.getPeopleNo())*100)/100.0)).toString();
				data[row][13] = ((Double)(association.getTrashPrice() * apartment.getPeopleNo())).toString();
				data[row][14] = ((Double)(Math.round(value*100)/100.0)).toString();
			}
			
			gasTotal += gasPrice * (apartment.getGasContor() ? 0 : apartment.getPeopleNo());	
			trashTotal += association.getTrashPrice() * apartment.getPeopleNo();
			warmingTotal += value;
				
			//make total/month
			
			//in case contor update is not provided for this month
			Double wWaterConsum = 0.0;
			Double cWaterConsum = 0.0;
			
			if(waterContors) {
				wWaterConsum = association.getwWaterPrice() * wWConsum;
				cWaterConsum = association.getcWaterPrice() * cWConsum;
			}
			//calculate total value
			Double value2 = (Double)(repairFundPrice + rulFundPrice + updateFundPrice + electBillPrice + salariesPrice +
					association.getwWaterPrice() * wWConsum + association.getcWaterPrice() * cWConsum + (
					gasPrice * (apartment.getGasContor() ? 0 : apartment.getPeopleNo()))  + association.getTrashPrice() * apartment.getPeopleNo() +
					value);
			
			if(adminFlag || apTargetFlag)
				data[row][15] = ((Double)(Math.round(value2*100)/100.0)).toString();
			monthTotal += value2;
			
			//checking if there is an overdue
			Double overduePenalties = 0.0;
			if(apartment.getOverdue() != 0) {
				//calculate the value with adding 0,2%/day
				overduePenalties = calculatePenalties(apartment.getOverdue(),association);
				if(adminFlag || apTargetFlag)
					data[row][16] = ((Double)(Math.round(overduePenalties * 100)/100.0)).toString();
			}else if(adminFlag || apTargetFlag){
				data[row][16] = "0.0";
			}
					
			overduePenaltiesTotal += overduePenalties;
			if(adminFlag || apTargetFlag)
				data[row][17] = ((Double)(Math.round(apartment.getOverdue()*100)/100.0)).toString();
			overdueTotal += apartment.getOverdue();
			
			//total pay column 
			Double value3 = value2 + overduePenalties + apartment.getOverdue();
			//value = Math.round(value*100)/100.0;
			if(adminFlag || apTargetFlag) {
				data[row][18] = ((Double)(Math.round(value3*100)/100.0)).toString();
				row++; 
			}
			payTotal += value3;
		}
		//making the last row
		data[row][0] = "Total ->";
		data[row][1] = ((Double)(Math.round(mpTotal*100)/100.0)).toString();
		data[row][2] = peopleNoTotal.toString();
		data[row][3] = ((Double)(Math.round(repairFTotal*100)/100.0)).toString();
		data[row][4] = ((Double)(Math.round(rulFTotal*100)/100.0)).toString();
		data[row][5] = ((Double)(Math.round(updateFTotal*100)/100.0)).toString();
		data[row][6] = ((Double)(Math.round(electBillTotal*100)/100.0)).toString();
		data[row][7] = ((Double)(Math.round(salariesTotal*100)/100.0)).toString();
		data[row][8] = ((Double)(Math.round(wWaterTotal*100)/100.0)).toString();
		data[row][9] = ((Double)(Math.round(cWaterTotal*100)/100.0)).toString();
		data[row][10] = ((Double)(Math.round(wWaterPriceTotal*100)/100.0)).toString();
		data[row][11] = ((Double)(Math.round(cWaterPriceTotal*100)/100.0)).toString();
		data[row][12] = ((Double)(Math.round(gasTotal*100)/100.0)).toString();
		data[row][13] = ((Double)(Math.round(trashTotal*100)/100.0)).toString();
		data[row][14] = ((Double)(Math.round(warmingTotal*100)/100.0)).toString();
		data[row][15] = ((Double)(Math.round(monthTotal*100)/100.0)).toString();
		data[row][16] = ((Double)(Math.round(overduePenaltiesTotal*100)/100.0)).toString();
		data[row][17] = ((Double)(Math.round(overdueTotal*100)/100.0)).toString();
		data[row][18] = ((Double)(Math.round(payTotal*100)/100.0)).toString();
		return data;
	}
	
	/**
	 * Returns the total month pay value for apartment.
	 * @param apartment the apartment we are interested in
	 * @return the total pay amont for current month
	 */
	public String getTotalPay(Apartment apartment) {
		return this.columnData[1][15];
	}
	
	/**
	 * Calculate the penalties addiding 0.2% from overdue per day
	 * @param overdue the overdue amount
	 * @return the penalties amount
	 */
	public static Double calculatePenalties(Double overdue,Association association) {
		Double overduePenalties = 0.0;
		
		long days = getDays(association.getnBLastUpdate());
		for(long x=0;x<days;x++) {
			overduePenalties += 0.002*overdue;
		}
		overduePenalties = Math.round(overduePenalties*100)/100.0;
		return overduePenalties;
	}
	
	/**
	 * Return the days between current date and date
	 * @param date the date till we need to know the days
	 * @return the days between current date and date
	 */
	public static long getDays(Date date) {
        LocalDate currentDate = LocalDate.now();
        java.util.Date utilDate = new java.util.Date(date.getTime());
        LocalDate d = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long diff = ChronoUnit.DAYS.between(d, currentDate);
        return diff;
    }

	public JTableHeader getDummyHeader() {
		return dummyHeader;
	}

	public void setDummyHeader(JTableHeader dummyHeader) {
		this.dummyHeader = dummyHeader;
	}

	public JTableHeader getHeader() {
		return header;
	}

	public void setHeader(JTableHeader header) {
		this.header = header;
	}

	public JScrollPane getPane() {
		return pane;
	}

	public void setPane(JScrollPane pane) {
		this.pane = pane;
	}
}
