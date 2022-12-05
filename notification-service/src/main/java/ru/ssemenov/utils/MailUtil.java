package ru.ssemenov.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Component
@PropertySource("mail.properties")
public class MailUtil {

    @Value("${mail.sender}")
    private String fromEmail; //requires valid gmail id
    @Value("${mail.password}")
    private String password; // correct password for gmail id

    public void sendEmailsToRecipients(List<String> emails, String title, String body, UUID trace) {
        log.info("SSL Email start, trace={}", trace);
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
        log.info("Session created, trace={}", trace);
        emails.forEach(s -> sendEmail(session, s, title, body, trace));
        log.info("Mailing done, trace={}", trace);
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body, UUID trace) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@ssemenov.com", "NoReply-SSemenov"));
            msg.setReplyTo(InternetAddress.parse("no_reply@ssemenov.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(String.format("<h1>%s</h1>", body), "text/html");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            log.info("Message for email={} is ready, trace={}", toEmail, trace);
            Transport.send(msg);
            log.info("Mail for email={} sent successfully, trace={}", toEmail, trace);
        } catch (UnsupportedEncodingException | MessagingException e) {
            log.error("Error send mail to email={}, error={} trace={}", toEmail, e.getMessage(), trace);
            e.printStackTrace();
        }
    }
}
