package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.http.ResponseEntity;


public interface MemberService {
    //註冊
    ResponseEntity<String> createMember(Member member);
    //登入


}
