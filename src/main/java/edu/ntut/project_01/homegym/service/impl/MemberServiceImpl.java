package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.exception.category.JwtException;
import edu.ntut.project_01.homegym.exception.category.LoginException;
import edu.ntut.project_01.homegym.exception.category.MemberNotExistException;
import edu.ntut.project_01.homegym.exception.category.RegistrationException;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.JwtUtil;
import edu.ntut.project_01.homegym.util.MailUtil;
import org.hibernate.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private MailUtil mailUtil;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${jwt.tokenHead}")
    private String tokenHeader;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MailUtil mailUtil, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.mailUtil = mailUtil;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<Map<String, Object>> changePassword(String authorizationHeader, String oldPassword, String newPassword, String newPasswordCheck) {
        Map<String, Object> response;
        if (authorizationHeader != null && authorizationHeader.startsWith(tokenHeader)) {
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            logger.info("UserName: " + username);
            Optional<Member> member = memberRepository.findMemberByEmail(username);
            if (member.isPresent()) {
                if (passwordEncoder.matches(oldPassword, member.get().getPassword())) {
                    if (newPassword.equals(newPasswordCheck)) {
                        member.get().setPassword(passwordEncoder.encode(newPassword));
                        memberRepository.save(member.get());
                        response = new HashMap<>();
//                        response.put("member", memberRepository.findMemberByEmail(username).get());
                        response.put("message", "密碼更改成功");
                        return ResponseEntity.ok().body(response);
                    }
                    throw new IllegalArgumentException("密碼與確認密碼不相符");
                }
                throw new QueryException("原密碼輸入錯誤，請重新輸入原密碼");
            }
            throw new MemberNotExistException("會員不存在");
        }
        throw new LoginException("尚未登入取得授權");
    }

    @Override
    public Member findMemberByToken(String authorizationHeader) {
        String jwt;
        String memberEmail;
        if (authorizationHeader != null && authorizationHeader.startsWith(tokenHeader)) {
            jwt = authorizationHeader.substring(7);
            memberEmail = jwtUtil.extractUsername(jwt);
            logger.info("checking authentication " + memberEmail);
            return memberRepository.findMemberByEmail(memberEmail).orElseThrow();
        }
        throw new JwtException("用戶尚未登入取得驗證");
    }


    public Optional<Member> findMemberByEmail(String email) {

        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public Optional<Member> findMemberByName(String name) {

        return memberRepository.findMemberByName(name);
    }

    @Override
    public void update(Member member) {
        Optional<Member> member1 = memberRepository.findMemberByMemberId(member.getMemberId());
        if (member1.isPresent()) {
            memberRepository.save(member);
        }
    }

    @Override
    public Map <String,Object> updateMemberInfo(Integer memberId, String name, String memberImage, String phone) {
        Map <String,Object> updateMemberInfoResponse = new HashMap<>();
        Map <String,String> errorMessage = new HashMap<>();
        Member member = memberRepository.findById(memberId).orElseThrow();

        if (memberImage != null) {
            String dataToBase64 = memberImage.substring(memberImage.indexOf(",") + 1);
            String mimeType = memberImage.substring(5,memberImage.indexOf(";"));
            byte[] imageBytes = Base64.getDecoder().decode(dataToBase64);
            member.setMemberImage(imageBytes);
            member.setMimeType(mimeType);
        }
        if(!phone.equals(member.getPhone())){
            if(phone.matches("^09[0-9]{8}$")){
                member.setPhone(phone);
            }
            errorMessage.put("phoneErrorMessage", "手機號碼格式有誤");
        }
        if(!name.equals(member.getName())){
            member.setName(name);
        }
        memberRepository.save(member);
        updateMemberInfoResponse.put("memberImage" ,member.getMemberImage());
        updateMemberInfoResponse.put("mimeType" ,member.getMimeType());
        updateMemberInfoResponse.put("name" ,member.getName());
        updateMemberInfoResponse.put("email" ,member.getEmail());
        updateMemberInfoResponse.put("birthday" ,member.getBirthday());
        updateMemberInfoResponse.put("phone" ,member.getPhone());
        updateMemberInfoResponse.put("errorMessage" ,errorMessage);

        return updateMemberInfoResponse;
    }
}
