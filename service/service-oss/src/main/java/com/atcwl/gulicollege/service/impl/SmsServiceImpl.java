package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.service.SmsService;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean send(String phone, String code) {
        try {
            HtmlEmail email = new HtmlEmail();
            /*
            端口465用于smtps
            SSL加密在任何SMTP级别通信之前自动启动.
            端口587用于msa
            它几乎像标准SMTP端口. MSA应在验证后接受电子邮件(例如,在SMTP AUTH之后).它有助于阻止传出垃圾邮件时的netmasters DUL范围可以阻止传出到SMTP端口(端口25)连接.如果服务器支持
            SSL 并且您的ISP不过滤服务器的EHLO回复(2014年报告),则可以通过SMTP级别的STARTTLS命令启动SSL加密(这个命令现在过时了)
             */
            email.setSmtpPort(587);
            /*发送邮件的服务器 126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com*/
            email.setHostName("smtp.qq.com");
            /*不设置发送的消息有可能是乱码*/
            email.setCharset("utf-8");
            /*IMAP/SMTP服务的密码*/
            email.setAuthentication("2200876452@qq.com", "twwfuoyxhrugebjj");
            /*发送邮件的邮箱和发件人*/
            email.setFrom("2200876452@qq.com", "cwl");
            /*使用安全链接*/
            email.setSSLOnConnect(true);
            /*接收的邮箱*/
            email.addTo("atcwl202039@outlook.com");
            /*设置邮件的主题*/
            email.setSubject("验证码");
            /*设置邮件的内容*/
            email.setMsg("尊敬的用户:你好! 注册验证码为:" + code + "(有效期为两分钟)");

            email.send();

            //将验证码存入到redis中，便于登录时判断
            redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);

            //Properties props = new Properties();                    // 参数配置
            //props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
            //props.setProperty("mail.smtp.host", "smtp.qq.com");   // 发件人的邮箱的 SMTP 服务器地址
            //props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
            //final String smtpPort = "587";
            //props.setProperty("mail.smtp.port", smtpPort);
            //props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //props.setProperty("mail.smtp.socketFactory.port", smtpPort);
            //
            //Session session = Session.getDefaultInstance(props);
            //session.setDebug(true);
            ////// 3. 创建一封邮件
            //MimeMessage message = createMessage(session, "2200876452@qq.com", "2379893379@qq.com", code);
            //
            //Transport transport = session.getTransport();
            //transport.connect("2200876452@qq.com", "arvuiaendioydhia");
            //transport.sendMessage(message, message.getAllRecipients());
            //transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public MimeMessage createMessage(Session session, String sendMail, String receiveMail, String info) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "邮箱验证码测试", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "xx用户", "UTF-8"));
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("CSU主页登录验证码", "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent("您好，您的验证码为: " + info, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}
