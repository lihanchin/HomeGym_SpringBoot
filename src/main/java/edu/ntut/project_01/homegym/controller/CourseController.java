package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.*;
import edu.ntut.project_01.homegym.service.*;
import edu.ntut.project_01.homegym.util.GlobalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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


    //輸入留言
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

    //輸入FQA
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

    //輸入FQAReply
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

    //顯示已買課程詳細頁(包含FQA、FQAReply)
    @GetMapping ("/{id}")
    public  Map<String,Object> showFQA(@PathVariable Integer id,HttpServletRequest request) {
        String header= request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Map<String,Object> map = new HashMap<>();
        List<FQA> fqaList= fqaService.showFQA(id);

        for (FQA fqa : fqaList) {
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

    //上傳影片
    @PostMapping("/upload")
    public void Coach(@RequestBody Map<String, Object> coachInfo,HttpServletRequest request) {
        String header= request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Coach coach = member.getCoach();
        Integer coachId = coach.getCoachId();

        String coursePath = coachInfo.get("coursePath").toString();//這
        String courseName = coachInfo.get("courseName").toString();
        String partOfBody = coachInfo.get("partOfBody").toString();
        Integer price = Integer.valueOf(coachInfo.get("price").toString());
        String equipment = coachInfo.get("equipment").toString();
        String level = coachInfo.get("level").toString();
        String courseInfo = coachInfo.get("courseInfo").toString();

        //存影片到
        File videoFolder = new File("src/main/resources/static/video");
        System.out.println(videoFolder);
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }

        String videoCoachPath = GlobalService.imageSaveToFile(coursePath, videoFolder,coachId,".mp4");

        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String uploadTime = sdf.format(new Date());

        Course course = new Course();
        course.setCourseName(courseName);
        course.setPartOfBody(partOfBody);
        course.setPrice(price);
        course.setEquipment(equipment);
        course.setLevel(level);
        course.setCourseInfo(courseInfo);
        course.setCoach(coach);
        course.setUploadTime(uploadTime);
        course.setCoursePath(videoCoachPath);
        course.setChecked(0);
        course.setPass(0);
        courseService.save(course);
    }

    //顯示留言
    @GetMapping("/{id}/showComment")
    public Map<String,Object> showComment(@PathVariable Integer id, @RequestParam(required = false) Integer pageNo){
        Optional<Course> course  = courseService.findById(id);
        Page<CourseComment> courseComments;
        Map<String,Object> map = new HashMap<>();

        //如果點頁數
        if(pageNo!=null){
            courseComments = courseCommentService.findCourseComment(id,pageNo,5);
            if(course.isPresent()){
                if(courseComments != null){
                    for (CourseComment comment : courseComments.getContent()){
                        String name = comment.getMember().getName();
                        byte[] memberImage = comment.getMember().getMemberImage();
                        String mimeType = comment.getMember().getMimeType();
                        comment.setMemberImage(memberImage);
                        comment.setMemberName(name);
                        comment.setMineType(mimeType);
                    }
                }
            }
            map.put("courseComment",courseComments.getContent());
            map.put("totalPage",courseComments.getTotalPages());
            return map;
        }

        //第一頁
        courseComments = courseCommentService.findCourseComment(id,0,5);
        if(course.isPresent()){
            if(courseComments != null){
                for (CourseComment comment : courseComments.getContent()){
                    String name = comment.getMember().getName();
                    byte[] memberImage = comment.getMember().getMemberImage();
                    String mimeType = comment.getMember().getMimeType();
                    comment.setMemberImage(memberImage);
                    comment.setMemberName(name);
                    comment.setMineType(mimeType);
                }
            }
        }
        map.put("courseComment",courseComments.getContent());
        map.put("totalPage",courseComments.getTotalPages());
        return map;
    }

}
