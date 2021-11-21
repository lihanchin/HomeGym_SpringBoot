package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;

import javax.mail.MessagingException;
import java.util.Map;

public interface AuthService {
    //註冊
    Map<String, Object> register(Member member) throws MessagingException;
    //登入
    Map<String,Object> login(String username, String password);
    //驗證
    String updateStatus(String code);
    //重寄驗證信
    String resendMail(Integer memberId) throws MessagingException;
}
