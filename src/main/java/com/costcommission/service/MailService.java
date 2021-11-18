package com.costcommission.service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.costcommission.config.EnvConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private EnvConfiguration envConfiguration;

    private Properties getProperties(){
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.port", envConfiguration.getSmtpPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        return props;
    }

    private MimeMessage getMimeMessage(Session session, String fromMail, String to, String subject, String htmlText) throws AddressException, MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromMail));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(htmlText, "text/html; charset=utf-8");
        return msg;
    }

    private void sendViaTransport(Session session, MimeMessage msg) throws MessagingException {
        Transport transport = session.getTransport();
        transport.connect(envConfiguration.getEmailHost(), envConfiguration.getEmailUserName(),
                envConfiguration.getEmailPassword());
        transport.sendMessage(msg, msg.getAllRecipients());
    }

    @Async
    public void sendMail(String to, String subject, String htmlText) {
        try {
            Properties props = getProperties();
            Session session = Session.getDefaultInstance(props);
            String fromMail = envConfiguration.getFromMail();
            MimeMessage msg = getMimeMessage(session, fromMail, to, subject, htmlText);
            sendViaTransport(session, msg);
        }catch(Exception e){
        }

    }

}
