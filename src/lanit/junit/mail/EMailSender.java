package lanit.junit.mail;

import lanit.junit.variables.GlobalVariables;
import org.apache.log4j.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EMailSender {

    private static Logger log = Logger.getLogger(EMailSender.class);

    public static void send() {
        try {
            log.info("Подготовка письма к отправке");
            String fromMail = GlobalVariables.MAIL_AUTOTEST_USER;
            String password = GlobalVariables.MAIL_AUTOTEST_PASSWORD;
            String[] toMail = GlobalVariables.MAIL_RECIPIENTS.split(",");
            InternetAddress from = new InternetAddress(fromMail, fromMail);
            InternetAddress[] to = new InternetAddress[toMail.length];
            for (int i = 0; i < toMail.length; i++) {
                if (!toMail[i].equals("")) {
                    to[i] = new InternetAddress(toMail[i], toMail[i]);
                }
            }
            Properties props = new Properties();
            props.put("mail.transport.protocol", GlobalVariables.MAIL_TRANSPORT_PROTOCOL);
            props.put("mail.smtp.host", GlobalVariables.MAIL_SMTP_HOST);
            props.put("mail.smtp.port", GlobalVariables.MAIL_SMTP_PORT);
            Session session = Session.getDefaultInstance(props);
            Transport transport = session.getTransport();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject("Итоговый отчет о ходе выполнения автотеста", "UTF-8");
            Multipart multiPart = new MimeMultipart();
            //Message
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("ZIP архив с отчетом смотрите в аттачменте к письму!", "UTF-8");
            multiPart.addBodyPart(messageBodyPart);
            //Attachment
            messageBodyPart = new MimeBodyPart();
            DataSource attachment = new FileDataSource(GlobalVariables.AUTOTEST_REPORT_ZIP);
            messageBodyPart.setDataHandler(new DataHandler(attachment));
            messageBodyPart.setFileName(attachment.getName());
            multiPart.addBodyPart(messageBodyPart);
            message.setContent(multiPart);
            transport.connect(fromMail, password);
            transport.sendMessage(message, to);
            transport.close();
            log.info("Отчет о результате выполнения автотеста отправлен адресатам: " + GlobalVariables.MAIL_RECIPIENTS);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchProviderException e) {
            log.error(e.getMessage(), e);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
