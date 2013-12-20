package kloSpringServer.service;


import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private static final Logger logger = Logger.getLogger(EmailSender.class);

    private String recipientAddress;
    private String senderAddress;
    private final String SMTPServer;
    private final Session session;

    public EmailSender(String SMTPServerHostname, String senderAddress) {
        this.SMTPServer = SMTPServerHostname;
        this.senderAddress = senderAddress;
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", SMTPServer);
        session = Session.getDefaultInstance(properties);
    }

    public void sendEmail(String to, String subject, String messageBody) {
        sendEmail(to, senderAddress, subject, messageBody);
    }

    public void sendEmail(String to, String from, String subject, String messageBody) {
        logger.info(String.format("Server: Trying to send email from %s to %s", from, to));
        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(messageBody);
            Transport.send(mimeMessage);
            logger.info(String.format("Email sent from %s to %s using %s", from, to, SMTPServer));
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
