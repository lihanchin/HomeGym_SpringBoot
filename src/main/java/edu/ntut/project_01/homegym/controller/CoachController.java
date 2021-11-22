package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.GlobalService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CoachController {

    @Value("${jwt.header}")
    private String authorization;
    private final MemberService memberService;

    @Autowired
    public CoachController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/coachApply")
    public String applyForCoach(HttpServletRequest request, @RequestBody Coach coach) {
        Member member = memberService.findMemberByToken(request.getHeader(authorization));
        String applyTime = GlobalService.getNowDatetime();
        coach.setApplyTime(applyTime);
        coach.setChecked("0");
        member.setCoach(coach);
        member.setRole("ROLE_COACH");
        memberService.update(member);
        log.info(coach.getCoachId() + " =====> 存取結束");

        return "waitForApplyingForCoach";
    }
}
