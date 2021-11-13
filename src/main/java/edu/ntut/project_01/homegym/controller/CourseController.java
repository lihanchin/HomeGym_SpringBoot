package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.*;
import edu.ntut.project_01.homegym.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseCommentService courseCommentService;
    @Autowired
    CourseService courseService;
    @Autowired
    FQAService fqaService;
    @Autowired
    FQAReplyService fqaReplyService;
    @Autowired
    MemberService memberService;


    @Value("${jwt.header}")
    private String authorization;

    //算星星
    public Map<String,Object> countStar(){
        Integer star = courseCommentService.countStar();
        Map<String,Object> map = new HashMap<>();
        map.put("star",star);
        return map;
    }



    @PostMapping ("/addComment/{courseId}")
    public void addComment(@RequestBody CourseComment courseComment,@PathVariable Integer courseId,HttpServletRequest request) {
        String header= request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Course course = courseService.findById(courseId).get();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String commentTime = sdf.format(new Date());
        courseComment.setCourse(course);
        courseComment.setMember(member);
        courseComment.setCommentCreateTime(commentTime);
        courseCommentService.save(courseComment);

    }


    @PostMapping ("/addFQA/{courseId}")
    public void addFQA(@RequestBody FQA fqaInput , @PathVariable Integer courseId, HttpServletRequest request) {
        String header= request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Course course = courseService.findById(courseId).get();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String fqaTime = sdf.format(new Date());
        fqaInput.setFqaCreateTime(fqaTime);
        fqaInput.setCourse(course);
        fqaInput.setMember(member);
        fqaService.save(fqaInput);

    }

    @PostMapping ("/addFQAReply/{fqaId}")
    public void addFQAReply(@RequestBody FQAReply fqaReplyInput, @PathVariable Integer fqaId,HttpServletRequest request) {
        String header= request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        FQA fqa = fqaService.findById(fqaId).get();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String fqaReplyTime = sdf.format(new Date());
        fqaReplyInput.setFqa(fqa);
        fqaReplyInput.setMember(member);
        fqaReplyInput.setFqaReplyCreateTime(fqaReplyTime);
        fqaReplyService.save(fqaReplyInput);

    }



    @GetMapping ("/{id}")
    public  Map<String,Object> showFQA(@PathVariable Integer id,HttpServletRequest request) {
        String header= request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Map<String,Object> map = new HashMap<>();
        List<FQA> fqaList= fqaService.showFQA(id);

        for (FQA fqa: fqaList){
            String name = fqa.getMember().getName();
            byte[] memberImage = fqa.getMember().getMemberImage();
            String fqaMimeType = fqa.getMember().getMimeType();
            fqa.setMemberName(name);
            fqa.setMemberImage(memberImage);
            fqa.setMineType(fqaMimeType);
            for (FQAReply fqaReply: fqa.getFqaReplies()){
                String replyName = fqaReply.getMember().getName();
                byte[] replyImage = fqaReply.getMember().getMemberImage();
                String replyMimeType = fqaReply.getMember().getMimeType();
                fqaReply.setMemberName(replyName);
                fqaReply.setMemberImage(replyImage);
                fqaReply.setMineType(replyMimeType);
            }
        }


        map.put("fqaList",fqaList);
        map.put("name",member.getName());
        map.put("memberImage",member.getMemberImage());
        map.put("mimeType",member.getMimeType());

        return map;
    }


}
