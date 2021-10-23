package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;


    @Transactional
    @Override
    public ResponseEntity<String> createMember(Member member) {
        if(memberRepository.existsMemberByEmail(member.getEmail())){

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("UserAlreadyExist");
        }else{
            memberRepository.save(member);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
    }
}
