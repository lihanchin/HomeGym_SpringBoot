package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Member;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public interface MemberService {
    //註冊(舊版)
    ResponseEntity<String> createMember(Member member);

    //驗證(舊版)
    ResponseEntity<String> updateStatus(String code);

    //登入(舊版)
    ResponseEntity<Member> login(String mail, String password);

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByName(String name);

    void update(Member member);
}
