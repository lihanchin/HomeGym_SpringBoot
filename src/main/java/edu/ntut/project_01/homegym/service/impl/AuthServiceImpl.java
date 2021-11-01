package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.exception.category.RegistrationException;
import edu.ntut.project_01.homegym.exception.category.VerificationMailException;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.AuthService;
import edu.ntut.project_01.homegym.util.JwtUtil;
import edu.ntut.project_01.homegym.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private MailUtil mailUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public ResponseEntity<String> register(Member member) {
        if (!memberRepository.existsMemberByEmail(member.getEmail())) {
            final String rawPassword = member.getPassword();
            member.setPassword(passwordEncoder.encode(rawPassword));
            member.setRole("ROLE_MEMBER");
            member.setCode(UUID.randomUUID().toString());
            memberRepository.save(member);
            mailUtil.sendMail(member.getCode(), member.getEmail());
            return ResponseEntity.ok().body("註冊成功");
        }
        throw new RegistrationException("此帳號已被使用");
    }

    @Override
    public ResponseEntity<String> login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails.isEnabled()){
            final String jwt = jwtUtil.generateToken(userDetails);
            logger.info("JWT: " + jwt);
            return ResponseEntity.ok().body(jwt);
        }
        throw new VerificationMailException("您的帳號尚未驗證");
    }
}
