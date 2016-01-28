package hr.foi.air.t18.chatup.Mail;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Created by JurmanLap on 29.12.2015..
 * public clas GmailSender used for sendig mail in ForgotPasswordActivity
 */
public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    //adding JsseProvider
    static {
        Security.addProvider(new JSSEProvider());
    }
    /**
     * Constructor for mail sender
     * @param user sender email adres
     * @param password sender password
     */
    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;

        Properties props = new Properties();
        //set transport protocol
        props.setProperty("mail.transport.protocol", "smtp");
        //set host
        props.setProperty("mail.host", mailhost);
        //put other specs
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }

    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(user, password);
    }
    /**
     * public sendMail method for sending mail message with seting properties
     * @param subject Mail subject
     * @param body Mail body
     * @param sender Mail sender
     * @param recipients Mail recipients
     */
    public synchronized void sendMail(String subject, String body,
                                      String sender, String recipients) throws Exception {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
        //set sender
        message.setSender(new InternetAddress(sender));
        //set subject
        message.setSubject(subject);
        //set datahandler
        message.setDataHandler(handler);
        //if has more then 1 recipients
        if (recipients.indexOf(',') > 0)
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(recipients));
            //else if 1 recipient
        else
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipients));
        //send mail
        Transport.send(message);
    }
}