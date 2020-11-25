package com.zq.smallTool.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @program: admin
 * @description: 发送信息工具类
 * @author: zhouQ
 * @create: 2020-11-23 11:46
 **/
public class SendMsmUtils {

    public static void sendEmail(String email,String title,String content) throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host","smtp.163.com");// smtp服务器地址

        Session session = Session.getInstance(props);
        session.setDebug(true);

        Message msg = new MimeMessage(session);
        msg.setSubject(title);
        msg.setText(content);
        //发件人邮箱(我的163邮箱)
        msg.setFrom(new InternetAddress("182***@163.com"));
        //收件人邮箱(我的QQ邮箱)
        msg.setRecipient(Message.RecipientType.TO,
                new InternetAddress(email));
        msg.saveChanges();

        Transport transport = session.getTransport();
        //发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)
        transport.connect("182***@163.com","****");

        transport.sendMessage(msg, msg.getAllRecipients());

        System.out.println("邮件发送成功...");
        transport.close();
    }
}
