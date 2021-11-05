package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.http.ResponseEntity;


public interface MemberService {
    //註冊(舊版)
    ResponseEntity<String> createMember(Member member);

    //登入(舊版)
    ResponseEntity<Member> login(String mail, String password);

    //更改密碼
    ResponseEntity<String> changePassword(String authorizationHeader, String oldPassword, String newPassword, String newPasswordCheck);
}
