package com.atuldwivedi.javamail.receive;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

public class ReadMail {

	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle
				.getBundle("com//atuldwivedi//javamail//send//email",
				Locale.US);
		String userName = rb.getString("username");
		String password = rb.getString("password");

		try {
			// Get session object by providing properties field
			Properties properties = new Properties();
			properties.put("mail.pop3.host", "pop.gmail.com");
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// Create the POP3 store object
			Store store = emailSession.getStore("pop3s");

			// Connect with the pop server
			store.connect("pop.gmail.com", userName, password);

			// Create the folder object
			Folder emailFolder = store.getFolder("INBOX");

			// Open the folder
			emailFolder.open(Folder.READ_ONLY);

			// Retrieve the messages from the folder in an array 
			// and print at most 10
			// messages
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);

			for (int i = 0, n = (messages.length > 10 ? 10 : messages.length); i < n; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + ((MimeMultipart) message.getContent()).getBodyPart(0).getContent());
			}

			// close the store and folder objects
			emailFolder.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}