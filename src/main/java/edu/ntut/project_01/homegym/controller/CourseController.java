package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.*;
import edu.ntut.project_01.homegym.service.*;
import edu.ntut.project_01.homegym.util.GlobalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Value("${jwt.header}")
    private String authorization;
    @Value("5")
    Integer size;

    private final CourseCommentService courseCommentService;
    private final CourseService courseService;
    private final FQAService fqaService;
    private final FQAReplyService fqaReplyService;
    private final MemberService memberService;

    @Autowired
    public CourseController(CourseCommentService courseCommentService, CourseService courseService, FQAService fqaService, FQAReplyService fqaReplyService, MemberService memberService) {
        this.courseCommentService = courseCommentService;
        this.courseService = courseService;
        this.fqaService = fqaService;
        this.fqaReplyService = fqaReplyService;
        this.memberService = memberService;
    }

    //輸入留言
    @PostMapping("/addComment/{courseId}")
    public void addComment(HttpServletRequest request, @RequestBody CourseComment courseComment, @PathVariable Integer courseId) {
        courseComment.setCourse(courseService.findById(courseId).orElseThrow());
        courseComment.setMember(memberService.findMemberByToken(request.getHeader(authorization)));
        courseComment.setCommentCreateTime(GlobalService.getNowDatetime());
        courseCommentService.save(courseComment);

    }

    //輸入FQA
    @PostMapping("/addFQA/{courseId}")
    public void addFQA(HttpServletRequest request, @RequestBody FQA fqaInput, @PathVariable Integer courseId) {
        fqaInput.setFqaCreateTime(GlobalService.getNowDatetime());
        fqaInput.setCourse(courseService.findById(courseId).orElseThrow());
        fqaInput.setMember(memberService.findMemberByToken(request.getHeader(authorization)));
        fqaService.save(fqaInput);
    }

    //輸入FQAReply
    @PostMapping("/addFQAReply/{fqaId}")
    public void addFQAReply(HttpServletRequest request, @RequestBody FQAReply fqaReplyInput, @PathVariable Integer fqaId) {
        fqaReplyInput.setFqa(fqaService.findById(fqaId).orElseThrow());
        fqaReplyInput.setMember(memberService.findMemberByToken(request.getHeader(authorization)));
        fqaReplyInput.setFqaReplyCreateTime(GlobalService.getNowDatetime());
        fqaReplyService.save(fqaReplyInput);
    }

    //顯示已買課程詳細頁(包含FQA、FQAReply)
    @GetMapping("/{id}")
    public Map<String, Object> showFQA(HttpServletRequest request, @PathVariable Integer id) {
        Member member = memberService.findMemberByToken(request.getHeader(authorization));
        Map<String, Object> map = new HashMap<>();
        List<FQA> fqaList = fqaService.showFQA(id);

        for (FQA fqa : fqaList) {
            fqa.setMemberName(fqa.getMember().getName());
            fqa.setMemberImage(fqa.getMember().getMemberImage());
            fqa.setMineType(fqa.getMember().getMimeType());
            for (FQAReply fqaReply : fqa.getFqaReplies()) {
                fqaReply.setMemberName(fqaReply.getMember().getName());
                fqaReply.setMemberImage(fqaReply.getMember().getMemberImage());
                fqaReply.setMineType(fqaReply.getMember().getMimeType());
            }
        }

        map.put("fqaList", fqaList);
        map.put("name", member.getName());
        map.put("memberImage", member.getMemberImage());
        map.put("mimeType", member.getMimeType());

        return map;
    }

    //上傳影片
    @PostMapping("/upload")
    public void Coach(HttpServletRequest request, @RequestBody Map<String, Object> coachInfo) {
        Coach coach = memberService.findMemberByToken(request.getHeader(authorization)).getCoach();
        Integer coachId = coach.getCoachId();

        String coursePath = coachInfo.get("coursePath").toString();
        String courseName = coachInfo.get("courseName").toString();
        String partOfBody = coachInfo.get("partOfBody").toString();
        Integer price = Integer.valueOf(coachInfo.get("price").toString());
        String equipment = coachInfo.get("equipment").toString();
        String level = coachInfo.get("level").toString();
        String courseInfo = coachInfo.get("courseInfo").toString();
        String courseImage = coachInfo.get("courseImage").toString();

        //存影片到
        File videoFolder = new File("src/main/resources/static/video");
        log.info("資料夾 : " + videoFolder);
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }

        String videoCoachPath = GlobalService.videoSaveToFile(coursePath, videoFolder, coachId, ".mp4");
        String uploadTime = GlobalService.getNowDatetime();
        Course course = new Course(videoCoachPath, courseName, courseInfo, partOfBody, courseImage, uploadTime, price, equipment, level, coach);
        courseService.save(course);
    }

    //顯示留言
    @GetMapping("/{id}/showComment")
    public Map<String, Object> showComment(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1") Integer pageNo) {
        courseService.findById(id).orElseThrow();
        Page<CourseComment> courseComments;
        Map<String, Object> map = new HashMap<>();
        log.info("pageNo : " + pageNo);
        if (pageNo <= 0) {
            pageNo = 1;
        }
        courseComments = courseCommentService.findCourseComment(id, pageNo - 1, size);
        if (courseComments != null) {
            for (CourseComment comment : courseComments.getContent()) {
                log.info("name==============================" + comment.getMember().getName());
                comment.setMemberImage(comment.getMember().getMemberImage());
                comment.setMemberName(comment.getMember().getName());
                comment.setMineType(comment.getMember().getMimeType());
                map.put("courseComment", courseComments.getContent());
                map.put("totalPage", courseComments.getTotalPages());
            }
        }
        return map;
    }

}
