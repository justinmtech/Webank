package com.justinmtech.webank.email;

import com.justinmtech.webank.exceptions.email.EmailError;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Enable sending and receiving emails
 */
@Service
public class EmailService implements EmailSender {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("spring.mail.properties.mail.smtp.starttls.enable")
    private String tls;

    @Autowired
    private Environment environment;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void send(String to, String email) throws EmailError {
        try {
            MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setText(email, true);
            helper.setSubject("Confirm your email");
            getJavaMailSender().send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new EmailError(to, e);
        }

    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(getProperties());
        mailSender.setUsername(username.equals("email") ? environment.getProperty("SPRING_MAIL_USERNAME") : username);
        mailSender.setPassword(password.equals("password") ? environment.getProperty("SPRING_MAIL_PASSWORD") : password);
        mailSender.setDefaultEncoding("UTF-8");

        return mailSender;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", getHost());
        props.put("mail.smtp.auth", isAuth());
        props.put("mail.smtp.port", getPort());
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", getPort());
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        return props;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String isAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String isTls() {
        return tls;
    }

    public void setTls(String tls) {
        this.tls = tls;
    }
}
