package com.example.projeto1.email;

import java.security.Security;
import java.util.Properties;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GmailSender extends Authenticator {

    private static final String MAIL_HOST = "smtp.gmail.com";
    private static final String MAIL_PROTOCOL = "smtp";

    private static final String USER = "schooldiary00@gmail.com";
    private static final String PASSWORD = "projetopap2021";

    private final String subject;
    private final String body;
    private final String mailTo;

    private final Session session;

    private static final Properties properties;


    static {

        Security.addProvider(new JSSEProvider());


        properties = new Properties();

        properties.setProperty("mail.transport.protocol", MAIL_PROTOCOL);
        properties.setProperty("mail.host", MAIL_HOST);

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        properties.setProperty("mail.smtp.quitwait", "false");

    }

    public GmailSender(String subject, String body, String mailTo) {

        this.subject = subject;
        this.body = body;
        this.mailTo = mailTo;
        this.session = Session.getDefaultInstance(properties, this);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USER, PASSWORD);
    }

    public void sendMail() throws Exception {

        MimeMessage message = new MimeMessage(session);

        message.setSender(new InternetAddress(USER));
        message.setSubject(subject);

        message.setText(body);

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));


        Transport.send(message);

    }

}
