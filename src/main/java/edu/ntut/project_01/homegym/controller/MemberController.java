package edu.ntut.project_01.homegym.controller;


import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

//    @PostMapping("/memberSave")
//    public String insert(@RequestBody Member member){
//
//        memberRepository.save(member);
//        return "新增成功一筆資料";
//    }

    @PostMapping("/memberSave")
    public String insert(@RequestParam Map<String, String> param, Model model){
        Member member = new Member();
        member.setName(param.get("name"));
        member.setEmail(param.get("email"));
        member.setPassword(param.get("password"));
        member.setBirthday(param.get("birthday"));
        member.setPhone(param.get("phone"));
        memberRepository.save(member);
        model.addAttribute("member",member);
        return "Show";
    }
    @GetMapping("/{Id}")
    public String read(@PathVariable Integer Id, Model model){
        Optional<Member> memberOptional= memberRepository.findById(Id);
        memberOptional.ifPresent(member -> {
            model.addAttribute("member",member);
        });
//        if(member.isPresent()){
//            model.addAttribute("member",member.get());
//        }
        return "Show";
    }

//    @PutMapping
//    public String uqdate(){
//
//    }

}
