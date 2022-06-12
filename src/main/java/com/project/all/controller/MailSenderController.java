package com.project.all.controller;

import com.project.all.domain.fileList;
import com.project.all.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.Oneway;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:MailSendorController
 * @Author:lxx
 * @Date 2022/5/7 20:48
 **/
@RestController
@RequestMapping("mail")
public class MailSenderController {
    @Resource
    MailService mailService;
    @PostMapping("sender")
    public Map<String, Object> SenderMail(String email,String title,String message){
        return mailService.sendMail(email,title,message);
    }

    @PostMapping("senderAttachFile")
    public Map<String, Object> SenderMailComplex(String email, String title, String message) {
        List<String> op=new fileList().userFileList();
        Map<String, Object> ret= mailService.complexEmail(email,title,message,op);
        op.clear();
        return ret;

    }

}
