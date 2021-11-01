package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    //註冊
    ResponseEntity<Member> register(Member member);
    //登入
    ResponseEntity<String> login(String username, String password);
    //重寄驗證信
    ResponseEntity<String> resendMail(Integer memberId);
}
