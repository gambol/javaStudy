package gambol.examples.qqmail;

import com.google.common.collect.Lists;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zhenbao.zhou on 17/7/25.
 */
@Slf4j
public class QQMail {

    private static Properties properties()  {
        Properties sessionProp = new Properties();
        sessionProp.setProperty("mail.transport.protocol", "smtp");
        sessionProp.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        sessionProp.setProperty("mail.smtp.port", "465");
        sessionProp.setProperty("mail.smtp.auth", "true");
        // 开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            log.error("error in mail ssl socket factory.", e1);
        }
        sessionProp.put("mail.smtp.starttls.enable", "true");
        sessionProp.put("mail.smtp.ssl.socketFactory", sf);
        sessionProp.put("mail.smtp.ssl.enable", "true");

        return sessionProp;
    }

    private static MimeMessage generateMessage(Session session, MailBean mailBean) {
        MimeMessage message = new MimeMessage(session);
        // message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
        try {
            message.setSentDate(new Date()); // 设置邮件发送日期
            Address address = new InternetAddress(mailBean.getMailFrom());
            message.setFrom(address); // 设置邮件发送者的地址

            // 设置接收方地址
            if (CollectionUtils.isNotEmpty(mailBean.getMailTo())) {
                Address[] receivers = new Address[mailBean.getMailTo().size()];
                for (int i = 0; i < mailBean.getMailTo().size(); i++) {
                    receivers[i] = new InternetAddress(mailBean.getMailTo().get(i));
                }
                message.setRecipients(Message.RecipientType.TO, receivers);
            }

            if (CollectionUtils.isNotEmpty(mailBean.getMailCC())) {
                Address[] ccReceivers = new Address[mailBean.getMailCC().size()];
                for (int i = 0; i < mailBean.getMailTo().size(); i++) {
                    ccReceivers[i] = new InternetAddress(mailBean.getMailCC().get(i));
                }
                message.setRecipients(Message.RecipientType.CC, ccReceivers);
            }

            message.setSubject(mailBean.getSubject(), "utf8"); // 设置邮件主题

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(mailBean.getContent(), "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 添加附件的内容
            if (CollectionUtils.isNotEmpty(mailBean.getAttachmentList())) {
                for (int j = 0; j < mailBean.getAttachmentList().size(); j++) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(mailBean.getAttachmentList().get(j));
                    attachmentBodyPart.setDataHandler(new DataHandler(source));

                    // MimeUtility.encodeWord可以避免文件名乱码
                    attachmentBodyPart
                            .setFileName(MimeUtility.encodeWord(mailBean.getAttachmentList().get(j).getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            // 将multipart对象放到message中
            message.setContent(multipart);
            message.saveChanges();

        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException", e);
        } catch (AddressException e) {
            log.error("AddressException", e);
        } catch (MessagingException e) {
            log.error("MessagingException", e);
        }

        return message;
    }

    // @Override
    public static boolean sendSyncMailByJavaMail1(MailBean mailBean) {
        Authenticator auth = new LoginAuthenticator("ya.ma@alphayee.com", "qianqiQQ77"); // 进行邮件服务器用户认证

        Properties sessionProp = properties();

        Session session = Session.getDefaultInstance(sessionProp, auth);
        MimeMessage message = generateMessage(session, mailBean);

        log.info("send----");
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("MessagingException", e);

        }
        return false;
    }

    private static class LoginAuthenticator extends Authenticator {

        String username;
        String password;

        LoginAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.username, this.password);
        }
    }

    public static void main(String[] args) {
        MailBean mailBean = new MailBean();
        mailBean.setMailFrom("ya.ma@alphayee.com");
        mailBean.setMailTo(Lists.newArrayList("zhenbao.zhou@alphayee.com"));
        mailBean.setSubject("test");
        mailBean.setContent("testtest");

        log.info("begin----");
        sendSyncMailByJavaMail1(mailBean);
        log.info("end----");

    }
}
