package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    //註冊
    ResponseEntity<String> register(Member member);
    //登入
    ResponseEntity<String> login(String username, String password);
}
