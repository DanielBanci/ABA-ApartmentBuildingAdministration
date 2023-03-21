package mainClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import chat.Chat;

/**
	The Databases class contains methods for interacting with a database.
	@author Ciprian Banci
	@version 1.0
*/
public class Database {
	
	private String url;
	private String userName;
	private String password;
	private Connection connection;
	private Statement statement;
	private ResultSet result;
	private String sqlCommand;
	
	private static Database singleInstance = null; 
	
	/**
	Constructs a new Database instance with default authentication information
	for a MySQL database running locally on port 3306 with a database name of "aba_project",
	a user name of "root", and a password of "#db2002". This constructor also checks
	if the required tables exist in the database and creates them if they do not exist.
	*/
	public Database() {
		this.url = "jdbc:mysql://localhost:3306/aba_project";
		this.userName = "root";
		this.password = "#db2002";
		this.sqlCommand = "";
		
		checkIfTablesExists();
	}
	
	/**
	Returns a singleton instance of the Database class.
	@return the singleton instance of the Database class
	*/
	public static Database getInstance() {
		if(singleInstance == null)
			singleInstance = new Database();
		return singleInstance;
	}
	
	/**
	Updates the pay status of an apartment in the database.
	@param apartment the apartment to update
	*/
	public void refreshPayStatusDB(Apartment apartment) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "UPDATE apartments SET " +
					"payed_status = " + apartment.getPayedStatus() +
					" WHERE id = " + apartment.getId();
			statement.executeUpdate(sqlCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Inserts paying dates for an association into the database.
	@param association the association to update
	*/
	public void insertDates(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//paying_dates
			//delete the old paying dates from databases
			sqlCommand = "DELETE FROM paying_dates WHERE association_id = " + association.getId();
			statement.executeUpdate(sqlCommand);
			
			//write the new dates
			for(Date date : association.getPayingDates()) {
				sqlCommand = "INSERT INTO paying_dates (association_id, date) VALUES "
						+ "(" + association.getId() + ", '" + date + "')";
				statement.executeUpdate(sqlCommand);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Returns the last admin ID from the "admins" table in the database.
	@return the last admin ID or 1 if there is no admin in the database.
	*/
	public Integer getLastAdminId() {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT MAX(id) AS max FROM admins";
			ResultSet result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return result.getInt("max");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	Returns the last association ID from the "associations_new" table in the database.
	@return the last association ID or 1 if there is no association in the database.
	*/
	public int getLastAssociationId() {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT MAX(id) AS max FROM associations_new";
			ResultSet result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return result.getInt("max");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	Checks if an email is unique in the "users" table in the database.
	@param email the email to check.
	@return true if the email is unique, false otherwise
	*/
	public boolean isUniqueEmail(String email) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT email FROM users WHERE email = '" + email + "';";
			ResultSet result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return false;
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Conected to databases: ERROR!");
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	Returns the last apartment ID from the "apartments" table in the database.
	@return the last apartment ID or 1 if there is no apartment in the database.
	*/
	public int getLastIdApartment() {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT MAX(id) AS max FROM apartments";
			result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return result.getInt("max");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
		
	}
	
	/**

	Returns the last message ID from the "message" table in the database.
	@return the last message ID or 1 if there is no message in the database.
	*/
	public int getMessLastId() {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			sqlCommand = "SELECT MAX(id) AS max FROM message";
			Statement statement = connection.createStatement();
			result = statement.executeQuery(sqlCommand);
			
			if(result.next())
				return result.getInt("max");
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	Inserts a new message to the database.
	@param mes the Message object to be inserted
	*/
	public void insertMessage(Message mes) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			sqlCommand = "INSERT INTO message (association_id, name, email, message, date, id) VALUES (" + mes.getAssociationId() + 
					", '" + mes.getName() + "', '" + mes.getEmail() + "', '" + mes.getMessage() + "', '" + Chat.formatDateString(mes.getDate())
					+ "', " 
					+ mes.getId() + ");";
			statement = connection.createStatement();
			statement.executeUpdate(sqlCommand);		
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Retrieves all messages from the database that belong to a given Association.
	@param association the Association object to which the messages belong
	@return a List containing all Message objects that belong to the given Association
	*/
	public List<Message> getMessagesFromDB(Association association) {
		List<Message> messages = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(url, userName, password);
			sqlCommand = "SELECT date, id, association_id, name, email, message "
					+ "FROM message WHERE association_id = " + association.getId();
			statement = connection.createStatement();
			result = statement.executeQuery(sqlCommand);

			while(result.next()) {
				messages.add(new Message(result.getInt("id"),result.getInt("association_id"),result.getString("name"),
						result.getString("email"),result.getString("message"),result.getTimestamp("date")));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return messages;
	}
	
	/**
	Inserts all the data related to an Association object into the database by calling the corresponding methods.
	@param association the Association object whose data will be inserted into the database.
	*/
	public void insertAllData(Association association) {
		insertNewPrices(association);
		insertNewAssociation(association);
		insertNewAdmin(association.getAdmin());
		insertNewApartments(association);
	}
	
	/**
	Inserts data related to apartments of association into the database.
	@param association the Association object whose apartments' data will be inserted into the database.
	*/
	public void insertNewApartments(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//apartments
			for(Apartment apartment : association.getApartments()) {
				sqlCommand = "INSERT INTO apartments (id, association_id, email, password, number, people_no, area, c_w_contor_old, "
						+ "w_w_contor_old, overdue, register_date, c_w_contor_new, w_w_contor_new, central_heating, gas_contor,"
						+ " contor_update_date, phone_no, individual_w_contor, payed_status, c_w_c_old_k, w_w_c_old_k, c_w_c_new_k,"
						+ "w_w_c_new_k) "
						+ "VALUES (" + apartment.getId() + ", " + association.getId() + ", '" + apartment.getEmail() + "', '" + 
						apartment.getPasswd() + "', " + apartment.getNo() + ", " + apartment.getPeopleNo() + ", " + apartment.getArea() + ", " +
						apartment.getcWContorOld() + ", " + apartment.getwWContorOld() + ", " + apartment.getOverdue() + ", '" +
						apartment.getRegisterDate() + "', " + apartment.getcWContorNew() + ", " + apartment.getwWContorNew() + ", " +
						apartment.getCentralHeating() +", " + apartment.getGasContor() + ", '" + apartment.getContorUpdateDate() + 
						"', '" + apartment.getPhoneNo() + "', " + apartment.getIndividualWContors() + ", " + apartment.getPayedStatus() + ", " +
						apartment.getcWCOldK() + ", " + apartment.getwWCOldK() + ", " + apartment.getcWCNewK() + ", " +
						apartment.getwWCNewK() + ");";
						
				statement.executeUpdate(sqlCommand);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Conected to databases: ERROR!");
			e.printStackTrace();
		}
	}
	
	/**
	Inserts data related to an Admin object into the database.
	@param admin the Admin object whose data will be inserted into the database.
	*/
	public void insertNewAdmin(Admin admin) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "INSERT INTO admins (id, association_id, name, email, password, phone_no, register_date) VALUES (" +
					admin.getId() + ", " + admin.getId() + ", '" + admin.getName() + "', '" +
					admin.getEmail() + "', '" + admin.getPasswd() + "', '" + 
					admin.getPhoneNo() + "', '" + admin.getRegisterDate() + "');";
			statement.executeUpdate(sqlCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Conected to databases: ERROR!");
			e.printStackTrace();
		}
	}
	
	/**
	Inserts a new association into the database.
	@param association the association to be inserted into the database
	*/
	public void insertNewAssociation(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//associations
			sqlCommand = "INSERT INTO associations_new (district, locality, street, number, building, stair, id, admin_id, "
					+ "n_b_last_update) VALUES ('" + 
					association.getDistrict() + "', '" + association.getLocality() + "', '" + association.getStreet() + "', '" + 
					association.getNumber() + "', '" + association.getBuilding() + "', '" + association.getStair() + "', " + 
					association.getId() + ", " + association.getAdmin().getId() + ", '" + association.getnBLastUpdate() + "');";
			statement.executeUpdate(sqlCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Conected to databases: ERROR!");
			e.printStackTrace();
		}
	}
	
	/**
	Inserts new prices for an association into the database.
	@param association the association for which prices are being updated
	*/
	public void insertNewPrices(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//prices
			sqlCommand = "INSERT INTO prices_new (warm_water, cold_water, repair_fund, rul_fund, update_fund, "
					+ "elect_bill, salaries, warming_bill, trash, gas_bill, association_id) VALUES (" + association.getwWaterPrice() 
					+", " + association.getcWaterPrice() + ", " + association.getRepairFund() + ", " + association.getRulFund() + ", "+
					association.getUpdateFund() + ", " + association.getElectBill() + ", " + association.getSalaries() + ", "+
					association.getWarmingBill() + ", " + association.getTrashPrice() + ", " + association.getGasBill() + 
					", " + association.getId()  + ");";
			statement.executeUpdate(sqlCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Conected to databases: ERROR!");
			e.printStackTrace();
		}
	}
	
	/**
	Refreshes the phone number of an admin in the database.
	@param admin the admin whose phone number is being updated
	*/
	public void refreshAdminNumberDB(Admin admin) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "UPDATE admins SET phone_no = '" + admin.getPhoneNo() +
					"' WHERE id = " + admin.getId();
			statement.executeUpdate(sqlCommand);
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Refreshes the password of an admin in the database.
	@param admin the admin whose password is being updated
	*/
	public void refreshAdminPasswordDB(Admin admin) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "UPDATE admins SET password = '" + admin.getPasswd() +
					"' WHERE id = " + admin.getId();
			
			statement.executeUpdate(sqlCommand);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Update the information of an apartment in the database
	@param apartment the apartment to be updated
	*/
	public void updateApartment(Apartment apartment) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = //doar ce se schimba
					"UPDATE apartments SET " +
					"people_no = " + apartment.getPeopleNo() + 
					", email = '" + apartment.getEmail() +
					"', password = '" + apartment.getPasswd() +
					"', area = " + apartment.getArea() + 
					", c_w_contor_old = " + apartment.getcWContorOld() +
					", c_w_contor_new = " + apartment.getcWContorNew() + 
					", overdue = " + apartment.getOverdue() + 
					", w_w_contor_old = " + apartment.getwWContorOld() + 
					", w_w_contor_new = " + apartment.getwWContorNew() +
					", central_heating = " + apartment.getCentralHeating() + 
					", gas_contor = " + apartment.getGasContor() + 
					", contor_update_date = '" + apartment.getContorUpdateDate() + 
					"', phone_no = '" + apartment.getPhoneNo() +
					"', individual_w_contor = " + apartment.getIndividualWContors() + 
					", payed_status = " + apartment.getPayedStatus() +
					" WHERE id = " + apartment.getId();
			statement.executeUpdate(sqlCommand);
			
			if(apartment.getIndividualWContors()) {
				sqlCommand = "UPDATE apartments SET c_w_c_old_k = " + apartment.getcWCOldK() + 
						", w_w_c_old_k	= " + apartment.getwWCOldK() + ", c_w_c_new_k = " + apartment.getcWCNewK() +
						", w_w_c_new_k = " + apartment.getwWCNewK() + " WHERE id = " + apartment.getId();
				statement.executeUpdate(sqlCommand);
			}
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Get the association related to the given user
	@param user the user to get the association for
	@return the association object related to the user
	*/
	public Association getAssociation(User user) {
		
		Association association = new Association();
		//getting the admin
		Admin admin = null;
		if(user instanceof Admin)
			admin = (Admin) user;
		else {
			admin = getAdmin((Apartment)user);
		}
		
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT * FROM associations_new WHERE admin_id = " + admin.getId();
			ResultSet result = statement.executeQuery(sqlCommand);
			if(result.next()) { 
				//getting data for association
				int id = result.getInt("id");	
				String street = result.getString("street");
				String number = result.getString("number");
				String building = result.getString("building");
				String stair = result.getString("stair");
				String district = result.getString("district");
				String locality = result.getString("locality");
				Date nBLastUpdate = result.getDate("n_b_last_update");
				
				//set data
				association.setId(id);
				association.setStreet(street);
				association.setNumber(number);
				association.setBuilding(building);
				association.setStair(stair);
				association.setDistrict(district);
				association.setLocality(locality);
				association.setnBLastUpdate(nBLastUpdate);
				association.setAdmin(admin);
				
				getPrices(association);

				association.setPayingDates(getPayingDates(association));
				association.setApartments(getApartments(association));
				association.setTotalArea(getTotalArea(association));
				association.setPeopleNoTotal(getTotalPeople(association));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return association;
	}
	
	/**
	Returns the total number of people living in the apartments of the given association.
	@param association The association to retrieve information for.
	@return The total number of people living in the apartments of the association.
	*/
	public Integer getTotalPeople(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT SUM(people_no) AS sum FROM apartments WHERE association_id = " + association.getId();
			result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return result.getInt("sum");
			}
			statement.close();
			connection.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	Returns the total area of all apartments belonging to the given association.
	@param association The association to retrieve information for.
	@return The total area of all apartments belonging to the association.
	*/
	public Double getTotalArea(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT SUM(area) AS sum FROM apartments WHERE association_id = " + association.getId();
			result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return result.getDouble("sum");
			}
			statement.close();
			connection.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}

	/**
	Returns a list of all apartments belonging to the given association.
	@param association The association to retrieve information for.
	@return A list of all apartments belonging to the association, sorted by apartment number.
	*/
	public List<Apartment> getApartments(Association association) {
		List<Apartment> apartments = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT * FROM apartments WHERE association_id = " + association.getId();
			result = statement.executeQuery(sqlCommand);
			
			while(result.next()) {
				apartments.add(new Apartment("Apartamentul " + result.getInt("number"),result.getString("email"),
						result.getString("password"),result.getString("phone_no"), result.getDate("register_date"), 
						result.getInt("number"), result.getInt("id"), result.getInt("association_id"), result.getInt("people_no"), 
						result.getDouble("area"), result.getBoolean("central_heating"), result.getBoolean("gas_contor"), result.getDouble("c_w_contor_old"),
						result.getDouble("w_w_contor_old"), result.getDouble("overdue"), result.getDouble("c_w_contor_new"),
						result.getDouble("w_w_contor_new"), result.getDate("contor_update_date"), result.getBoolean("individual_w_contor"),
						result.getDouble("c_w_c_old_k"), result.getDouble("w_w_c_old_k"), result.getDouble("c_w_c_new_k"), 
						result.getDouble("w_w_c_new_k"),result.getBoolean("payed_status")));
			}
			//sort the apartments over number
			apartments.sort(new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Apartment)o1).getNo() - ((Apartment)o2).getNo();
				}
			});
			statement.close();
			connection.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return apartments;
	}

	/**
	Returns a list of dates representing the paying dates for the specified association.
	If any dates are from the previous month, they are deleted from the database.
	@param association the association for which to retrieve the paying dates
	@return a list of Date objects representing the paying dates for the specified association
	*/
	public List<Date> getPayingDates(Association association) {
		List<Date> payingDates = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			sqlCommand = "SELECT date from paying_dates WHERE association_id = " + association.getId();
			result = statement.executeQuery(sqlCommand);
			
			while(result.next()) {
				Date date = result.getDate("date");
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				//if the dates are from previous month delete them
				if(c.get(Calendar.MONTH) + 1 != LocalDate.now().getMonthValue() ) {
					sqlCommand = "DELETE FROM paying_dates WHERE association_id = " + association.getId();
					statement.executeUpdate(sqlCommand);
					break;
				}
				payingDates.add(date);
			}
			statement.close();
			connection.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payingDates;
	}
	
	/**
	Retrieves the prices for the specified association from the database and updates the association object.
	@param association the association for which to retrieve the prices
	*/
	public void getPrices(Association association) {
		//getting association prices
		try {
			if(connection == null)connection = DriverManager.getConnection(url, userName, password);
			if(statement == null)statement = connection.createStatement();
			sqlCommand = "SELECT * FROM prices_new WHERE association_id = " + association.getId();
			result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				Double wWaterPrice = result.getDouble("warm_water");
				Double cWaterPrice = result.getDouble("cold_water");
				Double repairFund = result.getDouble("repair_fund");
				Double rulFund = result.getDouble("rul_fund");
				Double updateFund = result.getDouble("update_fund");
				Double salaries = result.getDouble("salaries");
				Double warmingBill = result.getDouble("warming_bill");
				Double trashPrice = result.getDouble("trash");
				Double gasBill = result.getDouble("gas_bill");
				Double electBill = result.getDouble("elect_bill");
				
				association.setwWaterPrice(wWaterPrice);
				association.setcWaterPrice(cWaterPrice);
				association.setRepairFund(repairFund);
				association.setRulFund(rulFund);
				association.setUpdateFund(updateFund);
				association.setSalaries(salaries);
				association.setWarmingBill(warmingBill);
				association.setTrashPrice(trashPrice);
				association.setGasBill(gasBill);
				association.setElectBill(electBill);
			}
			statement.close();
			connection.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	Retrieves the admin for the specified apartment's association from the database and returns an Admin object.
	@param apartment the apartment for which to retrieve the admin's association
	@return an Admin object representing the admin of the specified apartment's association, or null if not found
	*/
	public Admin getAdmin(Apartment apartment) {
		sqlCommand = "SELECT association_id FROM apartments WHERE id = " + apartment.getId();
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				int associationId = result.getInt("association_id");
				sqlCommand = "SELECT * FROM admins WHERE association_id = " + associationId;
				result = statement.executeQuery(sqlCommand);
				if(result.next()) {
					return new Admin(result.getString("name"), result.getString("email"), result.getString("password"),
							result.getString("phone_no"), result.getDate("register_date"), result.getInt("id"), 
							result.getInt("association_id"));
				}
			}
			statement.close();
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	Returns an Admin object from the database if the email and password match those in the 'admins' table.
	@param email the email of the admin to search for
	@param password the password of the admin to search for
	@return an Admin object if the email and password match those in the 'admins' table, or null otherwise
	*/
	public Admin getAdmin(String email,String password) {
		sqlCommand = "SELECT * FROM admins WHERE email = '" + email +
				"' AND password = '" + password + "'";
		try {
			connection = DriverManager.getConnection(url, userName, this.password);
			statement = connection.createStatement();
			result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return new Admin(result.getString("name"),result.getString("email"),result.getString("password"),
						result.getString("phone_no"),result.getDate("register_date"),result.getInt("id"),
						result.getInt("association_id"));
			}
			statement.close();
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	Returns an Apartment object from the database if the email and password match those in the 'apartments' table.
	@param email the email of the apartment to search for
	@param password the password of the apartment to search for
	@return an Apartment object if the email and password match those in the 'apartments' table, or null otherwise
	*/
	public Apartment getApartment(String email,String password) {
		sqlCommand = "SELECT * FROM apartments WHERE email = '" + email +
				"' AND password = '" + password + "'";
		try {
			connection = DriverManager.getConnection(url, userName, this.password);
			statement = connection.createStatement();
			result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return new Apartment("Apartamentul " + result.getString("number"),result.getString("email"),
						result.getString("password"),result.getString("phone_no"),result.getDate("register_date"), 
						result.getInt("number"),result.getInt("id"), result.getInt("association_id"), result.getInt("people_no"),
						result.getDouble("area"),result.getBoolean("central_heating"), result.getBoolean("gas_contor"), result.getDouble("c_w_contor_old"),
						result.getDouble("w_w_contor_old"),result.getDouble("overdue"), result.getDouble("c_w_contor_new"),
						result.getDouble("w_w_contor_new"), result.getDate("contor_update_date"), result.getBoolean("individual_w_contor"),
						result.getDouble("c_w_c_old_k"), result.getDouble("w_w_c_old_k"), result.getDouble("c_w_c_new_k"), 
						result.getDouble("w_w_c_new_k"),result.getBoolean("payed_status"));
			}
			statement.close();
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	Searches the 'users' table in the database for a user with the specified email and password.
	@param email the email of the user to search for
	@param password the password of the user to search for
	@return true if the user is found in the 'users' table, or false otherwise
	*/
	public boolean findUser(String email,String password) {
		sqlCommand = "SELECT * FROM users WHERE email = '" + email +
				"' AND password = '" + password +"'";
		try {
			connection = DriverManager.getConnection(url, userName, this.password);
			statement = connection.createStatement();
			result = statement.executeQuery(sqlCommand);
			if(result.next()) {
				return true;
			}
			statement.close();
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	Updates all data related to an association including:
	apartments, admin, paying dates, association, and prices.
	@param association the association object whose data will be updated
	*/
	public void UpdateAllData(Association association) {
		updatePrices(association);
		updateAssociation(association);
		updateAdmin(association.getAdmin());
		updatePayingDates(association);
		updateApartments(association);

	}
	
	/**
	Updates apartment information for the given association in the database.
	@param association the association whose apartments data will be updated
	*/
	public void updateApartments(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//apartments
			for(Apartment apartment : association.getApartments()) {
				sqlCommand = "UPDATE apartments SET "
						+ "password = '" + apartment.getPasswd() + 
						"', email = '" + apartment.getEmail() +
						"', people_no = " + apartment.getPeopleNo() +
						", area = " + apartment.getArea() + 
						", c_w_contor_old = " + apartment.getcWContorOld() + 
						", w_w_contor_old = " + apartment.getwWContorOld() + 
						", overdue = " + apartment.getOverdue() + 
						", c_w_contor_new = " + apartment.getcWContorNew() +
						", w_w_contor_new = " + apartment.getwWContorNew() + 
						", central_heating = " + apartment.getCentralHeating() + 
						", contor_update_date = '" + apartment.getContorUpdateDate() + 
						"', phone_no = '" + apartment.getPhoneNo() +
						"', gas_contor = " + apartment.getGasContor() +
						", payed_status = " + apartment.getPayedStatus() +
						", individual_w_contor = " + apartment.getIndividualWContors() + 
						", c_w_c_old_k = " + apartment.getcWCOldK() +
						", w_w_c_old_k = " + apartment.getwWCOldK() + 
						", c_w_c_new_k = " + apartment.getcWCNewK() + 
						", w_w_c_new_k = " + apartment.getwWCNewK() +
						" WHERE id = " + apartment.getId();
				statement.executeUpdate(sqlCommand);
			}
			statement.close();
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Updates admin information for the given admin object in the database.
	@param admin the admin object whose data will be updated
	*/
	public void updateAdmin(Admin admin) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//admins
			sqlCommand = "UPDATE admins SET "
					+ "name = '" + admin.getName() + 
					"', email = '" + admin.getEmail() +
					"', password = '" + admin.getPasswd() + 
					"', phone_no = '" + admin.getPhoneNo() + 
					"' WHERE id = " + admin.getId();
			statement.executeUpdate(sqlCommand);
			statement.close();
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Updates paying dates information for the given association in the database.
	@param association the association whose paying dates data will be updated
	*/
	public void updatePayingDates(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//paying_dates
			//delete the old paying dates from databases
			sqlCommand = "DELETE FROM paying_dates WHERE association_id = " + association.getId();
			statement.executeUpdate(sqlCommand);

			//write the new dates
			for(Date date : association.getPayingDates()) {
				sqlCommand = "INSERT INTO paying_dates (association_id, date) VALUES "
						+ "(" + association.getId() + ", '" + date + "')";
				statement.executeUpdate(sqlCommand);
			}
			statement.close();
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Updates the association data in the database.
	@param association the association object containing the updated data
	*/
	public void updateAssociation(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//association
			sqlCommand = "UPDATE associations_new SET "
					+ "district = '" + association.getDistrict() + 
					"', locality = '" + association.getLocality() + 
					"', street = '" + association.getStreet() + 
					"', number = '" + association.getNumber() + 
					"', building = '" + association.getBuilding() + 
					"', stair = '" + association.getStair() + 
					"', n_b_last_update = '" + association.getnBLastUpdate() +
					"' WHERE id = " + association.getId();
			statement.executeUpdate(sqlCommand);
			statement.close();
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	Updates the prices data in the database.
	@param association the association object containing the updated prices data
	*/
	public void updatePrices(Association association) {
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//prices
			sqlCommand = "UPDATE prices_new SET warm_water = " + association.getwWaterPrice() + ", cold_water = " +
					association.getcWaterPrice() + ", repair_fund = " + association.getRepairFund() + ", rul_fund = " +
					association.getRulFund() + ", update_fund = " + association.getUpdateFund() + ", elect_bill = " + 
					association.getElectBill() + ", salaries = " + association.getSalaries() + ", warming_bill = " +
					association.getWarmingBill() + ", trash = " + association.getTrashPrice() + ", gas_bill = " +
					association.getGasBill() + " WHERE association_id = " + association.getId();
			statement.executeUpdate(sqlCommand);
			statement.close();
			connection.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Eroare la scrierea in baza de date!","Eroare",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	Checks if the necessary tables exist in the database and creates them if they don't.
	*/
	public void checkIfTablesExists() {

		//tables that we use in this application
		String t1 = "admins";
		String t2 = "apartments";
		String t3 = "associations_new";
		String t4 = "message";
		String t5 = "paying_dates";
		String t6 = "prices_new";
		String v1 = "users";
		
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			//admins
			boolean t1Exists = connection.getMetaData().getTables(null, null, t1, null).next();
			if (!t1Exists) {
				// Create the table if it doesn't exist
				sqlCommand = "CREATE TABLE " + t1 + " ("
						+ "id INT NOT NULL AUTO_INCREMENT,"
						+ "association_id INT NOT NULL ,"
						+ "name VARCHAR(255) NOT NULL ,"
						+ "email VARCHAR(255) NOT NULL ,"
						+ "password VARCHAR(255) NOT NULL ,"
						+ "phone_no VARCHAR(255) NOT NULL ,"
						+ "register_date DATE NOT NULL ,"
						+ "PRIMARY KEY (id))";
				statement.executeUpdate(sqlCommand);
			}
			
			//apartments
			boolean t2Exists = connection.getMetaData().getTables(null, null, t2, null).next();
			if (!t2Exists) {
				// Create the table if it doesn't exist
				sqlCommand = "CREATE TABLE " + t2 + " ("
						+ "id INT NOT NULL AUTO_INCREMENT,"
						+ "association_id INT NOT NULL ,"
						+ "name VARCHAR(255) NOT NULL ,"
						+ "email VARCHAR(255) NOT NULL ,"
						+ "password VARCHAR(255) NOT NULL ,"
						+ "number INT NOT NULL ,"
						+ "area DOUBLE NOT NULL ,"
						+ "register_date DATE NOT NULL ,"
						+ "c_w_contor_old DOUBLE NOT NULL ,"
						+ "w_w_contor_old DOUBLE NOT NULL ,"
						+ "overdue DOUBLE NOT NULL ,"
						+ "c_w_contor_new DOUBLE NOT NULL ,"
						+ "w_w_contor_new DOUBLE NOT NULL ,"
						+ "central_heating BOOLEAN NOT NULL ,"
						+ "contor_update_date BOOLEAN NOT NULL ,"
						+ "individual_w_contor BOOLEAN NOT NULL ,"
						+ "payed_status BOOLEAN NOT NULL ,"
						+ "gas_contor BOOLEAN NOT NULL ,"
						+ "contor_update_date DATE NOT NULL ,"
						+ "phone_no VARCHAR(255) NOT NULL ,"
						+ "c_w_c_old_k DOUBLE NOT NULL ,"
						+ "w_w_c_old_k DOUBLE NOT NULL ,"
						+ "c_w_c_new_k DOUBLE NOT NULL ,"
						+ "w_w_c_new_k DOUBLE NOT NULL ,"
						+ "PRIMARY KEY (id))";
				statement.executeUpdate(sqlCommand);
			}
			
			//association
			boolean t3Exists = connection.getMetaData().getTables(null, null, t3, null).next();
			if (!t3Exists) {
				// Create the table if it doesn't exist
				sqlCommand = "CREATE TABLE " + t3 + " ("
						+ "id INT NOT NULL AUTO_INCREMENT,"
						+ "admin_id INT NOT NULL ,"
						+ "district VARCHAR(255) NOT NULL ,"
						+ "locality VARCHAR(255) NOT NULL ,"
						+ "street VARCHAR(255) NOT NULL ,"
						+ "number INT NOT NULL ,"
						+ "building VARCHAR(255) NOT NULL ,"
						+ "stair VARCHAR(255) NOT NULL ,"
						+ "n_b_last_update DATE NOT NULL ,"
						+ "PRIMARY KEY (id))";
				statement.executeUpdate(sqlCommand);
			}
			
			//message
			boolean t4Exists = connection.getMetaData().getTables(null, null, t4, null).next();
			if (!t4Exists) {
				// Create the table if it doesn't exist
				sqlCommand = "CREATE TABLE " + t4 + " ("
						+ "id INT NOT NULL AUTO_INCREMENT,"
						+ "association_id INT NOT NULL,"
						+ "name VARCHAR(255) NOT NULL ,"
						+ "email VARCHAR(255) NOT NULL ,"
						+ "message VARCHAR(255) NOT NULL ,"
						+ "date DATE NOT NULL ,"
						+ "PRIMARY KEY (id))";
				statement.executeUpdate(sqlCommand);
			}
			
			//paying_dates
			boolean t5Exists = connection.getMetaData().getTables(null, null, t5, null).next();
			if (!t5Exists) {
				// Create the table if it doesn't exist
				sqlCommand = "CREATE TABLE " + t5 + " ("
						+ "association_id INT NOT NULL,"
						+ "date DATE NOT NULL ,";
				statement.executeUpdate(sqlCommand);
			}
			
			//prices
			boolean t6Exists = connection.getMetaData().getTables(null, null, t6, null).next();
			if (!t6Exists) {
				// Create the table if it doesn't exist
				sqlCommand = "CREATE TABLE " + t6 + " ("
						+ "id INT NOT NULL AUTO_INCREMENT,"
						+ "association_id INT NOT NULL,"
						+ "warm_water DOUBLE NOT NULL ,"
						+ "cold_water DOUBLE NOT NULL ,"
						+ "repair_fund DOUBLE NOT NULL ,"
						+ "rul_fund DOUBLE NOT NULL ,"
						+ "update_fund DOUBLE NOT NULL ,"
						+ "elect_bill DOUBLE NOT NULL ,"
						+ "salaries DOUBLE NOT NULL ,"
						+ "warming_bill DOUBLE NOT NULL ,"
						+ "trash DOUBLE NOT NULL ,"
						+ "gas_bill DOUBLE NOT NULL ,";
				statement.executeUpdate(sqlCommand);
			}
			
			//users -> view
			boolean v1Exists = connection.getMetaData().getTables(null, null, v1, null).next();
			if (!v1Exists) {
				// Create the table if it doesn't exist
				sqlCommand = "CREATE VIEW " + v1 + " AS "
						+ "SELECT email,password "
						+ "FROM admins "
						+ "UNION ALL "
						+ "SELECT email,password "
						+ "FROM apartments";
				statement.executeUpdate(sqlCommand);
			}
			
			statement.close();
			connection.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


}
