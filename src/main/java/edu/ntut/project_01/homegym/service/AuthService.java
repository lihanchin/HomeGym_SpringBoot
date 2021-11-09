package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    //註冊
    ResponseEntity<Map<String, Object>> register(Member member);
    //登入
    ResponseEntity<Map<String,Object>> login(String username, String password);
    //驗證
    ResponseEntity<String> updateStatus(String code);
    //重寄驗證信
    ResponseEntity<String> resendMail(Integer memberId);
}
