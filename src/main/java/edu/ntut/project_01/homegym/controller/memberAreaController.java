package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class memberAreaController {

    @Value("${jwt.header}")
    private String HEADER;

    @Autowired
    private MemberService memberService;

    //更改密碼
    @PostMapping("memberArea/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> oldAndNewPassword, HttpServletRequest httpServletRequest){
        String authorizationHeader = httpServletRequest.getHeader(HEADER);
        String oldPassword = oldAndNewPassword.get("oldPassword");
        String newPassword = oldAndNewPassword.get("newPassword");
        String newPasswordCheck = oldAndNewPassword.get("newPasswordCheck");

        return memberService.changePassword(authorizationHeader,oldPassword,newPassword,newPasswordCheck);
    }
}
