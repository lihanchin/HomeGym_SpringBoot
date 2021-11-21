package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.exception.category.JwtException;
import edu.ntut.project_01.homegym.exception.category.LoginException;
import edu.ntut.project_01.homegym.exception.category.MemberNotExistException;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHeader;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> changePassword(String authorizationHeader, String oldPassword, String newPassword, String newPasswordCheck) {
        Map<String, Object> response;
        if (authorizationHeader != null && authorizationHeader.startsWith(tokenHeader)) {
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            log.info("UserName: " + username);
            Optional<Member> member = memberRepository.findMemberByEmail(username);
            if (member.isPresent()) {
                if (passwordEncoder.matches(oldPassword, member.get().getPassword())) {
                    if (newPassword.equals(newPasswordCheck)) {
                        member.get().setPassword(passwordEncoder.encode(newPassword));
                        memberRepository.save(member.get());
                        response = new HashMap<>();
                        response.put("message", "密碼更改成功");
                        return response;
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
            log.info("checking authentication : " + memberEmail);
            return memberRepository.findMemberByEmail(memberEmail).orElseThrow();
        }
        throw new JwtException("用戶尚未登入取得驗證");
    }


    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public void update(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Map<String, Object> updateMemberInfo(Integer memberId, String name, String memberImage, String phone) {
        Map<String, Object> updateMemberInfoResponse = new HashMap<>();
        Map<String, String> errorMessage = new HashMap<>();
        Member member = memberRepository.findById(memberId).orElseThrow();

        if (memberImage != null) {
            String dataToBase64 = memberImage.substring(memberImage.indexOf(",") + 1);
            String mimeType = memberImage.substring(5, memberImage.indexOf(";"));
            byte[] imageBytes = Base64.getDecoder().decode(dataToBase64);
            member.setMemberImage(imageBytes);
            member.setMimeType(mimeType);
        }
        if (!phone.equals(member.getPhone())) {
            if (phone.matches("^09[0-9]{8}$")) {
                member.setPhone(phone);
            }
            errorMessage.put("phoneErrorMessage", "手機號碼格式有誤");
        }
        if (!name.equals(member.getName())) {
            member.setName(name);
        }
        memberRepository.save(member);
        updateMemberInfoResponse.put("memberImage", member.getMemberImage());
        updateMemberInfoResponse.put("mimeType", member.getMimeType());
        updateMemberInfoResponse.put("name", member.getName());
        updateMemberInfoResponse.put("email", member.getEmail());
        updateMemberInfoResponse.put("birthday", member.getBirthday());
        updateMemberInfoResponse.put("phone", member.getPhone());
        updateMemberInfoResponse.put("errorMessage", errorMessage);

        return updateMemberInfoResponse;
    }
}
