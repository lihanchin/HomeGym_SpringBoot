package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.exception.category.LoginException;
import edu.ntut.project_01.homegym.exception.category.MemberNotExistException;
import edu.ntut.project_01.homegym.exception.category.RegistrationException;
import edu.ntut.project_01.homegym.exception.category.VerificationMailException;
import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private MailUtil mailUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MailUtil mailUtil) {
        this.memberRepository = memberRepository;
        this.mailUtil = mailUtil;
    }



    @Override
    public ResponseEntity<String> createMember(Member member) {

        if (memberRepository.existsMemberByEmail(member.getEmail())) {
            throw new RegistrationException("帳號已存在");
        } else {
            member.setCode(UUID.randomUUID().toString());
            memberRepository.save(member);
            mailUtil.sendMail(member.getCode(), member.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
    }

    @Override
    public ResponseEntity<String> updateStatus(String code) {
        Optional<Member> member = memberRepository.findMemberByCode(code);

        if (member.isPresent()) {
            if (member.get().getStatus() == 0) {
                member.get().setStatus(1);
                memberRepository.save(member.get());
                logger.info("會員帳號驗證通過！");
                return ResponseEntity.status(HttpStatus.OK).body("驗證通過，歡迎使用HomeGym");
            } else {
                throw new VerificationMailException("此帳號已驗證通過");
            }
        } else {
            throw new MemberNotExistException("用戶不存在");
        }
    }

    @Override
    public ResponseEntity<Member> login(String mail, String password) {
        Optional<Member> member = memberRepository.findMemberByEmail(mail);
        if (member.isPresent()) {
            if (member.get().getStatus() == 0) {
                throw new LoginException("帳號尚未驗證");
            }
            if (password.equals(member.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(member.get());
            } else {
                throw new LoginException("帳號、密碼有誤");
            }
        } else {
            throw new LoginException("帳號、密碼有誤");
        }
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {

        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public Optional<Member> findMemberByName(String name) {

        return memberRepository.findMemberByName(name);
    }

    @Override
    public void update(Member member) {
        Optional<Member> member1= memberRepository.findMemberByMemberId(member.getMemberId());
        if(member1.isPresent()){
            memberRepository.save(member);
        }

    }

}
