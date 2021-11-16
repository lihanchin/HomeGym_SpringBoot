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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    @Value("${hg.ImgMimeType}")
    private String presetImgMimeType;

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
    public ResponseEntity<Map<String, Object>> register(Member member) {
        System.out.println("近來註冊");
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
            System.out.println("id媽啊啊啊啊啊啊錒啊啊啊啊啊啊啊啊啊啊"+memberRepository.findMemberByEmail(member.getEmail()).orElseThrow().getMemberId());
            return ResponseEntity.ok().body(memberInfo);
        }
        throw new RegistrationException("此帳號已被使用");
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
                return ResponseEntity.status(HttpStatus.OK).body("已驗證過");
            }
        } else {
            throw new MemberNotExistException("用戶不存在");
        }
    }

    @Override
    public ResponseEntity<Map<String,Object>> login(String username, String password) {
        Map<String,Object> response = new HashMap<>();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails.isEnabled()){
            final String jwt = jwtUtil.generateToken(userDetails);
            logger.info("JWT: " + jwt);
            response.put("JWT", jwt);
            response.put("loginResponse", jwtUtil.extractLoginResponse(jwt));
            return ResponseEntity.ok().body(response);
        }
        throw new VerificationMailException("您的帳號尚未驗證");
    }

    @Override
    public ResponseEntity<String> resendMail(Integer memberId) {
        Optional<Member> member = memberRepository.findMemberByMemberId(memberId);
        if(member.isPresent()){
            if(member.get().getStatus() != 1) {
                mailUtil.sendMail(member.get().getCode(), member.get().getEmail());
                return ResponseEntity.ok().body("驗證信已重新寄出");
            }
            throw  new VerificationMailException("您信箱已驗證通過，無需再次驗證");
        }
        throw new MemberNotExistException("查無此會員，故無法重寄驗證信");
    }
}
