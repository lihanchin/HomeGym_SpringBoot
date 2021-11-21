package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.exception.category.MemberNotExistException;
import edu.ntut.project_01.homegym.exception.category.RegistrationException;
import edu.ntut.project_01.homegym.exception.category.VerificationMailException;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.AuthService;
import edu.ntut.project_01.homegym.util.GlobalService;
import edu.ntut.project_01.homegym.util.JwtUtil;
import edu.ntut.project_01.homegym.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${hg.ImgMimeType}")
    private String presetImgMimeType;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailUtil mailUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, MemberRepository memberRepository, PasswordEncoder passwordEncoder, MailUtil mailUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailUtil = mailUtil;
    }

    @Override
    public Map<String, Object> register(Member member) throws MessagingException {
        System.out.println("進來註冊");
        Map<String, Object> memberInfo = new HashMap<>();
        if (!memberRepository.existsMemberByEmail(member.getEmail())) {
            final String rawPassword = member.getPassword();
            member.setPassword(passwordEncoder.encode(rawPassword));
            member.setRole("ROLE_MEMBER");
            member.setCode(UUID.randomUUID().toString());
            member.setMimeType(presetImgMimeType);
            member.setMemberImage(GlobalService.hgImg());
            member.setStatus(0);
            memberRepository.save(member);
            mailUtil.sendMail(member.getCode(), member.getEmail());
            memberInfo.put("memberId", memberRepository.findMemberByEmail(member.getEmail()).orElseThrow().getMemberId());
            log.info("memberID ======>" + memberRepository.findMemberByEmail(member.getEmail()).orElseThrow().getMemberId());
            return memberInfo;
        }
        throw new RegistrationException("此帳號已被使用");
    }

    @Override
    public String updateStatus(String code) {
        Optional<Member> member = memberRepository.findMemberByCode(code);

        if (member.isPresent()) {
            if (member.get().getStatus() == 0) {
                member.get().setStatus(1);
                memberRepository.save(member.get());
                log.info("會員 : " + member.get().getName() + "帳號驗證通過！");
                return "驗證通過，歡迎使用HomeGym";
            } else {
                return "已驗證過";
            }
        } else {
            throw new MemberNotExistException("用戶不存在");
        }
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> response = new HashMap<>();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails.isEnabled()) {
            final String jwt = jwtUtil.generateToken(userDetails);
            log.info("JWT: " + jwt);
            response.put("JWT", jwt);
            response.put("loginResponse", jwtUtil.extractLoginResponse(jwt));
            return response;
        }
        throw new VerificationMailException("您的帳號尚未驗證");
    }

    @Override
    public String resendMail(Integer memberId) throws MessagingException {
        Optional<Member> member = memberRepository.findMemberByMemberId(memberId);
        if (member.isPresent()) {
            if (member.get().getStatus() != 1) {
                mailUtil.sendMail(member.get().getCode(), member.get().getEmail());
                return "驗證信已重新寄出";
            }
            throw new VerificationMailException("您信箱已驗證通過，無需再次驗證");
        }
        throw new MemberNotExistException("查無此會員，故無法重寄驗證信");
    }
}
