package edu.ntut.project_01.homegym.controller;


import edu.ntut.project_01.homegym.model.Member;

import edu.ntut.project_01.homegym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/memberSave")
    public ResponseEntity<String> createMember(@RequestBody Member member){
        return memberService.createMember(member);
    }

    @GetMapping("/memberSave/memberVerification")
    public ResponseEntity<String>  updateMemberStatus(@RequestParam String code){
        return memberService.updateStatus(code);
    }

    @PostMapping("/login")
    public ResponseEntity<Member> memberLogin(@RequestBody Member member){
        String email = member.getEmail();
        String password = member.getPassword();
        return memberService.login(email,password);
    }
    //註冊資料傳來非Json值
//    @PostMapping("/memberSave")
//    public Member insert(@RequestParam Map<String, String> param, Model model){
//        Member member = new Member();
//        member.setName(param.get("name"));
//        member.setEmail(param.get("email"));
//        member.setPassword(param.get("password"));
//        SimpleDateFormat sdf  = new  SimpleDateFormat("yyyy-MM-dd");
//        try {
//            member.setBirthday(new java.sql.Date(sdf.parse(param.get("birthday")).getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        member.setPhone(param.get("phone"));
//        memberRepository.save(member);
//        model.addAttribute("member",member);
//        return member;
//    }


}
