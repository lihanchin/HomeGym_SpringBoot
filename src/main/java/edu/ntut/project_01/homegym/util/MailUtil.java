package edu.ntut.project_01.homegym.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class MailUtil {

    private SimpleMailMessage message;
    private String verifiedPath;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${hg.url}")
    private String ourUrl;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String code, String memberEmail){

        verifiedPath = ourUrl+"/memberVerification?code="+code;

        message = new SimpleMailMessage();
        message.setFrom("homegym_ntut_pj01@outlook.com");
        message.setTo(memberEmail);
        message.setSubject("主旨：HomeGym註冊驗證信");
        message.setText(verifiedPath);
        mailSender.send(message);
        logger.info("已寄信");
    }

}
