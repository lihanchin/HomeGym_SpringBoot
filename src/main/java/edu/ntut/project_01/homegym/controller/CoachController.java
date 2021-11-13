package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.service.CoachService;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.GlobalService;
import edu.ntut.project_01.homegym.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Controller
public class CoachController {

    @Autowired
    CoachService coachService;

    @Autowired
    MemberService memberService;


    @Value("${jwt.header}")
    private String authorization;


    @PostMapping("/apply")
    public String  applyForCoach(@RequestBody Coach coach, HttpServletRequest request){

        String header= request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Integer memberId = member.getMemberId();

        //存到coachImages
        File imageFolder = new File("src/main/resources/static/coachImages");
        System.out.println(imageFolder);
        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        String coachImagePath = GlobalService.imageSaveToFile(coach.getCoachImage(),imageFolder);

        //存到certification
        File certificationFolder = new File("src/main/resources/static/certification");
        System.out.println(certificationFolder);
        if(!certificationFolder.exists()){
            certificationFolder.mkdirs();
        }

        String certificationPath = GlobalService.imageSaveToFile(coach.getCertification(),certificationFolder);

        coach.setChecked("0");
        coach.setSuspension(0);

        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String applyTime = sdf.format(new Timestamp(System.currentTimeMillis()));

        coach.setApplyTime(applyTime);
        coach.setCertification(certificationPath);
        coach.setCoachImage(coachImagePath);
        member.setCoach(coach);
        memberService.update(member);
        System.out.println("存取結束");
        return "waitForApplyingForCoach";
    }
}
