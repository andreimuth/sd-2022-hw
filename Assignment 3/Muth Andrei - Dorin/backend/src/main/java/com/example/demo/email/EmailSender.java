package com.example.demo.email;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Component
public class EmailSender {

    private final String email = "sdproj792@gmail.com";//
    private final String password = "parolarandom";
    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    public void sendEmail(String toEmail, String subject, String message) {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, password);
                        }});

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(email));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail, false));
            msg.setSubject(subject);
            msg.setText(message);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        }catch (MessagingException e){
            throw new RuntimeException("Error sending email", e);
        }
    }

}
