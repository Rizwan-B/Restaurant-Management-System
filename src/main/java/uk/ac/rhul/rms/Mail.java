package uk.ac.rhul.rms;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

  private String fromEmail;
  private String toEmail;
  private String host;
  private Properties properties;
  private Session session;
  private String password;



  public Mail(String toEmail) {
    this.toEmail = toEmail;
    this.fromEmail = "oaxacarestaurant8@gmail.com";
    this.host = "smtp.gmail.com";
    this.properties = System.getProperties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");
    this.password = "Password962";

    this.session = Session.getInstance(properties, new javax.mail.Authenticator() {

      protected PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(fromEmail, password);

      }

    });
  }

  public void sendConfirmationEmail(String loginUsername, String loginPassword) {
    this.session.setDebug(true);

    try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(this.session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(this.fromEmail));

      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.toEmail));

      // Set Subject: header field
      message.setSubject("Welcome To Oaxaca");

      // Now set the actual message
      message.setText("Dear " + loginPassword
          + ",\n\nThe admin at Oaxaca has created an account for you. Please find the necessary login details below: \n\n\nUsername: "
          + loginUsername + "\nPassword: " + loginPassword);

      System.out.println("sending...");
      // Send message
      Transport.send(message);
      System.out.println("Sent message successfully....");
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }

}
