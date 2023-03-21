package mainClasses;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
The EmailSender class represents an object that is responsible for sending emails to recipients.
@author Ciprian Banci
@version 1.0
*/
public class EmailSender {
	private String from;
	private String password;
	
	/**
	Constructs a new EmailSender object with default values for sender email address and password.
	Sets the email address to "abaappservice@gmail.com" and password to "ldusseljcibuhgjj".
	*/
	public EmailSender() { 
		from = "abaappservice@gmail.com";
		password = "ldusseljcibuhgjj";
	}

	/**
	Asynchronously sends an email to a recipient with the given email address and password.
	The email is sent on a new thread to avoid blocking the current thread.
	@param to The email address of the recipient.
	@param password The password of the recipient.
	*/
	public void sendToAsync(String to, String password) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				sendTo(to, password);
			}
		});
		thread.start();
	}

	/**
	Asynchronously sends a notification email to a recipient with the given email address.
	The email is sent on a new thread to avoid blocking the current thread.
	@param to The email address of the recipient.
	*/
	public void sendChangesToAsync(String to) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				sendChangesTo(to);
			}
		});
		thread.start();
	}

	/**
	Sends an email to a user with the given email address and password.
	The email contains the autentification data for the new account
	@param to The email address of the recipient.
	@param password The password of the recipient.
	*/
	public void sendTo(String to, String password) {
		String from = "abaappservice@gmail.com";

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		EmailSender f = this;

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, f.password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Informatii de conectare ABA");
			message.setText("Buna ziua,\n"
					+ "\tAdministratorul dumneavoastra tocmai v-a inregistrat apartamentul in aplicatia noastra."
					+ "Acum puteti vedea detaliile de plata direct din aplicatie.\n"
					+ "Detalii conectare:\n"
					+ "Email: " + to + "\n"
					+ "Parola: " + password + "\n\n"
					+ "Din motive de securitate, va rugam sa va schimbati parola!\n"
					+ "Odata conectati, selectati Mai multe -> Schimba parola.\n\n"
					+ "O zi buna,\n"
					+ "Aba service");

			Transport.send(message);
			//System.out.println("Email sent successfully");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	/**
	Sends an email to a user with the given email address and password.
	The email contains a notification about account deleted
	@param to The email address of the recipient.
	*/
	public void sendChangesTo(String to) {
		String from = "abaappservice@gmail.com";

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		EmailSender f = this;

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, f.password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Informatii de conectare ABA");
			message.setText("Buna ziua,\n"
					+ "\tAcest e-mail a fost sters din baza noastra de date de catre administratorul "
					+ "asociatiei din care a-ti facut parte.\n"
					+ "Pentru alte informatii va rugam sa va contactati administartorul!\n\n"
					+ "O zi buna,\n"
					+ "Aba service");

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
