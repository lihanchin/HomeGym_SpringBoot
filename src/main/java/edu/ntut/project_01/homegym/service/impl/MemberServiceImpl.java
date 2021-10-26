package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.exception.category.LoginException;
import edu.ntut.project_01.homegym.exception.category.RegistrationException;
import edu.ntut.project_01.homegym.exception.category.VerificationMailException;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.RegistrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RegistrationUtil registrationUtil;


    @Transactional
    @Override
    public ResponseEntity<String> createMember(Member member) {
        if (memberRepository.existsMemberByEmail(member.getEmail())) {
            throw new RegistrationException("帳號已存在");
        } else {
            member.setCode(UUID.randomUUID().toString());
            memberRepository.save(member);
            registrationUtil.sendMail(member.getCode(), member.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> updateStatus(String code) {
        Member member = memberRepository.findMemberByCode(code);
        if (member.getStatus() == 0) {
            member.setStatus(1);
            memberRepository.save(member);
            return ResponseEntity.status(HttpStatus.OK).body("驗證通過，歡迎使用HomeGym");
        } else {
            throw new VerificationMailException("此帳號已驗證通過");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Member> login(String mail, String password) {
        Member member = memberRepository.findMemberByEmail(mail);
        if (member != null) {
            if(member.getStatus()==0){
                throw new LoginException("帳號尚未驗證");
            }
            if (password.equals(member.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(member);
            } else {
                throw new LoginException("帳號、密碼有誤");
            }
        } else {
            throw new LoginException("帳號、密碼有誤");
        }

    }


}
