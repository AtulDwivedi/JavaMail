package com.atuldwivedi.javamail.send;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleMailThruTLS {

	public static void main(String[] args) {

		ResourceBundle rb = ResourceBundle.getBundle("com//atuldwivedi//javamail//send//email", Locale.US);
		final String userName = rb.getString("username");
		final String password = rb.getString("password");
		String fromAddr = rb.getString("from");
		String toAddr = rb.getString("to");
		String subject = rb.getString("subject");
		String text = rb.getString("text");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(fromAddr));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddr));

			// Set Subject: header field
			message.setSubject(subject+" - SimpleMailThruTLS");

			// Now set the actual message
			message.setText(text);

			// Send message
			Transport.send(message);

			System.out.println("Sent!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
