package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.http.ResponseEntity;


public interface MemberService {
    //註冊(舊版)
    ResponseEntity<String> createMember(Member member);

    //驗證
    ResponseEntity<String> updateStatus(String code);

    //登入(舊版)
    ResponseEntity<Member> login(String mail, String password);
}
