package edu.ntut.project_01.homegym.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationUtil {

    private SimpleMailMessage message;
    private String verifiedPath;
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String code, String memberEmail){

        verifiedPath = "http://localhost:8080/memberSave/memberVerification?code="+code;

        message = new SimpleMailMessage();
        message.setFrom("homegym_ntut_pj01@outlook.com");
        message.setTo(memberEmail);
        message.setSubject("主旨：HomeGym註冊驗證信");
        message.setText(verifiedPath);
        mailSender.send(message);
        System.out.println("已寄信");
    }

}
