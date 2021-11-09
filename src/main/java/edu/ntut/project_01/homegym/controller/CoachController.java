package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.service.CoachService;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.JwtUtil;
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

@Controller
public class CoachController {

    @Autowired
    CoachService coachService;

    @Autowired
    MemberService memberService;

    @Autowired
    JwtUtil jwtUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${jwt.header}")
    private String authorization;

    @Value("${jwt.tokenHead}")
    private String tokenHeader;

    @PostMapping("/apply")
    public String  applyForCoach(@RequestBody Coach coach, HttpServletRequest request){

//        String header= request.getHeader(authorization);
//        String jwt;
//        String username = null;
//
//        if (header != null && header.startsWith(tokenHeader)) {
//
//            jwt = header.substring(7);
//            username = jwtUtil.extractUsername(jwt);
//            logger.info("checking authentication " + username);
//        }
//
//        System.out.println("進方法");
//        //帳號抓會員
//        Optional<Member> member = memberService.findMemberByName(username);


        //存到coachImages
        File imageFolder = new File("\\coachImages");
        System.out.println(imageFolder);
        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        String coachImagePath = imageSaveToFile(coach.getCoachImage(),imageFolder);

        //存到certification
        File certificationFolder = new File("\\certification");
        System.out.println(certificationFolder);
        if(!certificationFolder.exists()){
            certificationFolder.mkdirs();
        }

        String certificationPath = imageSaveToFile(coach.getCertification(),certificationFolder);

        coach.setChecked("0");
        coach.setSuspension(0);

        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String applyTime = sdf.format(new Timestamp(System.currentTimeMillis()));

        coach.setApplyTime(applyTime);
        coach.setCertification(certificationPath);
        coach.setCoachImage(coachImagePath);
//        member.get().setCoach(coach);
//        memberService.update(member.get());
        coachService.apply(coach);
        System.out.println("存取結束");
        return "waitForApplyingForCoach";
    }


    //寫進資料夾的方法
    public String imageSaveToFile(String data, File folder) {

        //取名用
        int startIndex = data.indexOf(",")+80;
        int endIndex = startIndex + 6;

        //base64轉byte陣列
        String dataToBase64 = data.substring(data.indexOf(",") + 1);
        byte[] bytes = Base64.getDecoder().decode(dataToBase64);

        String name = data.substring(startIndex, endIndex);
        File file = new File(folder,name+".jpg");

        try {
            OutputStream out = new FileOutputStream(file);
            out.write(bytes);
            System.out.println("讀取完畢");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("失敗");
        }
        return file.toString();
    }


}
