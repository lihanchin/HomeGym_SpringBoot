package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.AuthRequest;
import edu.ntut.project_01.homegym.model.Member;

import edu.ntut.project_01.homegym.model.Visitor;
import edu.ntut.project_01.homegym.service.AuthService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.VisitorService;
import edu.ntut.project_01.homegym.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class IndexController {

    @Value("${jwt.header}")
    private String authorization;
    @Value("${course.countsPerPage}")
    private Integer SIZE;
    @Value("${regex.password}")
    private String regex;
    private Map<String, Object> response;

    private final AuthService authService;
    private final MemberService memberService;
    private final VisitorService visitorService;
    private final CourseService courseService;
    private final MailUtil mailUtil;

    @Autowired
    public IndexController(AuthService authService, MemberService memberService, VisitorService visitorService, CourseService courseService, MailUtil mailUtil) {
        this.authService = authService;
        this.memberService = memberService;
        this.visitorService = visitorService;
        this.courseService = courseService;
        this.mailUtil = mailUtil;
    }

    //檢查JWT
    @GetMapping("/checkStatus")
    public Map<String, Object> checkStatus(HttpServletRequest request) {
        response = new HashMap<>();
        Member member = memberService.findMemberByToken(request.getHeader(authorization));
        response.put("name", member.getName());
        response.put("mimeType", member.getMimeType());
        response.put("memberImage", member.getMemberImage());
        return response;
    }

    //註冊(加入Security)(OK)
    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> registrations(@RequestBody Member member) throws MessagingException {
        return ResponseEntity.ok().body(authService.register(member));
    }

    //重寄驗證信(OK)
    @GetMapping("/registrations/memberVerification/sendAgain/{memberId}")
    public ResponseEntity<String> sendVerificationAgain(@PathVariable Integer memberId) throws MessagingException {
        return ResponseEntity.ok().body(authService.resendMail(memberId));
    }

    //登入(加入Security)(OK)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws AuthenticationException {
        log.info("用戶登入====> 帳號： " + authRequest.getUsername() + "密碼： " + authRequest.getPassword());

        return ResponseEntity.ok().body(authService.login(authRequest.getUsername(), authRequest.getPassword()));
    }

    //關鍵字查詢
    @GetMapping("/keyword")
    public ResponseEntity<Map<String, Object>> keyword(@RequestParam String keyword, @RequestParam(required = false) Integer page) {
        response = new HashMap<>();
        if (page == null || page < 1) {
            page = 1;
        }
        response.put("courseList", courseService.findCoursesByKeyword(keyword, page - 1, SIZE).getContent());
        response.put("totalPage", courseService.findCoursesByKeyword(keyword, page - 1, SIZE).getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/addMessage")
    public void addMessage(@RequestBody Visitor visitor) {
        visitorService.addMessage(visitor);
    }

    //忘記密碼-驗證帳號、寄信
    @PostMapping("/forget/checkMail")
    public ResponseEntity<String> checkMailAndSend(@RequestBody String memberEmail) throws MessagingException {
        if (memberService.findMemberByEmail(memberEmail).isPresent()) {
            mailUtil.sendResetPassword(memberEmail);
            return ResponseEntity.ok().body("已寄信");
        }
        return ResponseEntity.ok().body("您尚未成為我們的會員");
    }

    //新密碼設定
    @PostMapping("/forget/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> password) {
        String newPassword = password.get("newPassword");
        if (newPassword.equals(password.get("newPasswordCheck"))) {
            if (newPassword.matches(regex)) {
                Member member = memberService.findMemberByEmail(password.get("memberEmail")).orElseThrow();
                member.setPassword(newPassword);
                memberService.update(member);
                return ResponseEntity.ok().body("修改成功");
            }
            return ResponseEntity.ok().body("您的密碼必須超過5個字元且包含英文字母大小寫和數字");
        }
        return ResponseEntity.ok().body("您輸入的新密碼和確認密碼不相符");
    }

}
