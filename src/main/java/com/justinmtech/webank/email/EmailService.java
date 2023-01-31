package com.justinmtech.webank.email;

import com.justinmtech.webank.exceptions.email.EmailError;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

//TODO PUT PROPERTIES IN LOCAL FILE
@Service
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void send(String to, String email) throws EmailError {
        try {
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.debug", "true");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setJavaMailProperties(props);
            mailSender.setUsername("justin.mitchell09@gmail.com");
            //TEMP PASS
            mailSender.setPassword("baurtazphhxwkimw");
            mailSender.setDefaultEncoding("UTF-8");


            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setText(email, true);
            helper.setSubject("Confirm your email");
            helper.setFrom("business@conspiracycraft.net");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }

    }


}
