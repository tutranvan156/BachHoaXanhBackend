package ptit.example.bachhoaxanhbackend.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/30/2022 11:08 PM
 * Desc:
 */
public class JavaMailSender {

    public static void sendMail(String otp, String userEmail) throws AddressException, MessagingException, IOException {
        final String MAIL_ADDRESS = "tutranvan154@gmail.com";
        final String MAIL_PASSWORD = "laptrinhandroid";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_ADDRESS, MAIL_PASSWORD);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new
                InternetAddress(MAIL_ADDRESS, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
        msg.setSubject("OTP to change password");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Here is your OTP number: ", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(otp);
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        Transport.send(msg);
    }
}
