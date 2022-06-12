package com.project.all.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.*;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String mailUsername;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;

    /**
     * 激活账号邮件发送
     *
     * @param activationUrl 激活 url 链接
     * @param email         收件人邮箱
     */
    public void sendMailForActivationAccount(String activationUrl, String email) {
        // 创建邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            message.setSubject(" 个人账号激活");
            // 设置邮件发送者
            message.setFrom(mailUsername);
            // 设置邮件接受者，可以多个
            message.setTo(email);
            // 设置邮件抄送人，可以多个
            // message.setCc();
            // 设置隐秘抄送人，可以多个
            // message.setBcc();
            // 设置邮件发送日期
            message.setSentDate(new Date());
            // 创建上下文环境
            Context context = new Context();
            context.setVariable("activationUrl", activationUrl);
            String text = templateEngine.process("activation-account.html", context);
            // 设置邮件正文
            message.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 邮件发送
        javaMailSender.send(mimeMessage);
    }

    public Map<String, Object> sendMail(String email, String Title, String Message) {
        // 创建邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            message.setSubject(Title);
            // 设置邮件发送者
            message.setFrom(mailUsername);
            // 设置邮件接受者，可以多个
            message.setTo(email);
            // 设置邮件抄送人，可以多个
            // message.setCc();
            // 设置隐秘抄送人，可以多个
            // message.setBcc();
            // 设置邮件发送日期
            message.setSentDate(new Date());
            // 创建上下文环境
            message.setText(Message);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            resultMap.put("message", "发送失败");
            e.printStackTrace();
            return resultMap;
        }
        resultMap.put("message", "发送成功");


        return resultMap;
    }


    public Map<String, Object> complexEmail(String email, String Title, String Message, List<String> filePathList) {
        Map<String, Object> resultMap = new HashMap<>();
        //消息的固定信息

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setSubject(Title);
            // 设置邮件发送者
            message.setFrom(mailUsername);
            // 设置邮件接受者，可以多个
            message.setTo(email);
            message.setSentDate(new Date());
            // 创建上下文环境
            message.setText(Message,true);

            //邮件内容
            //准备图片数据
            for (String item : filePathList) {
                FileSystemResource file = new FileSystemResource(new File(item));
                String fileName = item.substring(item.lastIndexOf(File.separator));
                message.addAttachment(fileName, file);
            }

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            resultMap.put("message", "发送失败");
            e.printStackTrace();
            return resultMap;
        }
        resultMap.put("message", "发送成功");
        //放到Message消息中
        return resultMap;

    }
}

