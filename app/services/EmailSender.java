package services;

import play.libs.Akka;
import scala.concurrent.duration.Duration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * It's really important that you have a email.properties in
 * the conf folder, otherwise, this will be a mess.
 *
 * There's a email_example.properties to guide you, because
 * once you rename it, it wont be a part of the repo.
 * I'm sure you understand why (I hope so).
 */
public class EmailSender {

    private static final String BUNDLE_NAME = "email"; // $NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    private static final String MAIL_FROM = getString("MAIL_FROM");
    private static final String MAIL_USERNAME = getString("MAIL_USERNAME");
    private static final String MAIL_PASSWORD = getString("MAIL_PASSWORD");

    private static final String MAIL_TRANSPORT_PROTOCOL = getString("MAIL_TRANSPORT_PROTOCOL");
    private static final String MAIL_SMTP_HOST = getString("MAIL_SMTP_HOST");
    private static final String MAIL_SMTP_AUTH = getString("MAIL_SMTP_AUTH");
    private static final String MAIL_SMTP_PORT = getString("MAIL_SMTP_PORT");

    private static final Boolean MAIL_SMTP_SSL_ENABLE = getString("MAIL_SMTP_SSL_ENABLE").equals("true");

    private static final String MAIL_SMTP_SOCKETFACTORY_PORT = getString("MAIL_SMTP_SOCKETFACTORY_PORT");
    private static final String MAIL_SMTP_SOCKETFACTORY_CLASS = getString("MAIL_SMTP_SOCKETFACTORY_CLASS");
    private static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK = getString("MAIL_SMTP_SOCKETFACTORY_FALLBACK");

    private static final Boolean MAIL_SMTP_STARTTLS_ENABLE = getString("MAIL_SMTP_STARTTLS_ENABLE").equals("true");


    public static void asyncSend(final String to, final String subject, final String body) {
        Akka.system().scheduler().scheduleOnce(
                Duration.create(10, TimeUnit.MILLISECONDS),
                () -> {
                    send(to,subject,body);
                },
                Akka.system().dispatcher()
        );
    }

    public static void send(final String to, final String subject, final String body) {

        try {
            internalSend(to,subject,body);
        } catch (MessagingException e) {
            Akka.system().scheduler().scheduleOnce(
                    Duration.create(5, TimeUnit.MINUTES),
                    () -> {
                        try{
                            internalSend(to,subject,body);
                        } catch (MessagingException me) {
                            throw new RuntimeException(me);
                        }
                    },
                    Akka.system().dispatcher()
            );

        }

    }

    private static void internalSend(String to, String subject, String body) throws MessagingException {

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
        properties.setProperty("mail.smtp.host", MAIL_SMTP_HOST);
        properties.put("mail.smtp.auth", MAIL_SMTP_AUTH);
        properties.put("mail.smtp.port", MAIL_SMTP_PORT);

        // Uncomment the following line for testing purposes
        // properties.put("mail.debug", "true");

        // For SSL
        if(MAIL_SMTP_SSL_ENABLE){
            properties.put("mail.smtp.ssl.enable", MAIL_SMTP_SSL_ENABLE);
            properties.put("mail.smtp.socketFactory.port", MAIL_SMTP_SOCKETFACTORY_PORT);
            properties.put("mail.smtp.socketFactory.class",MAIL_SMTP_SOCKETFACTORY_CLASS);
            properties.put("mail.smtp.socketFactory.fallback", MAIL_SMTP_SOCKETFACTORY_FALLBACK);
        }

        // For TLS
        if(MAIL_SMTP_STARTTLS_ENABLE){
            properties.put("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
        }

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MAIL_USERNAME,
                                MAIL_PASSWORD);
                    }
                });

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(MAIL_FROM));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                to));
        message.setSubject(subject);
        message.setContent(body, "text/html");

        // Send the Message
        Transport.send(message);


    }
}

