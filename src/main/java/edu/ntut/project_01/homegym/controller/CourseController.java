package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.FQA;
import edu.ntut.project_01.homegym.model.FQAReply;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.service.CourseCommentService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.FQAReplyService;
import edu.ntut.project_01.homegym.service.FQAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseRepository courseRepository;
    private CourseCommentService courseCommentService;
    private CourseService courseService;
    private FQAService fqaService;
    private FQAReplyService fqaReplyService;

    @Autowired
    public CourseController(CourseRepository courseRepository, CourseCommentService courseCommentService, CourseService courseService, FQAService fqaService, FQAReplyService fqaReplyService) {
        this.courseRepository = courseRepository;
        this.courseCommentService = courseCommentService;
        this.courseService = courseService;
        this.fqaService = fqaService;
        this.fqaReplyService = fqaReplyService;
    }

    //算星星
    public Map<String, Object> countStar() {
        Integer star = courseCommentService.countStar();
        Map<String, Object> map = new HashMap<>();
        map.put("star", star);
        return map;
    }


    @PostMapping("/addComment/{courseId}")
    public void addComment(@RequestBody CourseComment courseComment, @PathVariable Integer courseId) {
        Course course = courseService.findById(courseId).orElseThrow();
        courseComment.setCourse(course);
        courseCommentService.save(courseComment);

    }


    @PostMapping("/addFQA/{courseId}")
    public void addFQA(@RequestBody FQA fqaInput, @PathVariable Integer courseId) {

        Course course = courseService.findById(courseId).orElseThrow();

        fqaInput.setCourse(course);
        fqaService.save(fqaInput);

    }

    @PostMapping("/addFQAReply/{fqaId}")
    public void addFQAReply(@RequestBody FQAReply fqaReplyInput, @PathVariable Integer fqaId) {
        FQA fqa = fqaService.findById(fqaId).orElseThrow();
        fqaReplyInput.setFqa(fqa);
        fqaReplyService.save(fqaReplyInput);

    }


    @GetMapping("/{id}")
    public List<FQA> showFQA(@PathVariable Integer id) {
        List<FQA> fqaList = fqaService.showFQA(id);

        for (FQA fqa : fqaList) {
            String name = fqa.getMember().getName();
            byte[] memberImage = fqa.getMember().getMemberImage();
            fqa.setMemberName(name);
            fqa.setMemberImge(memberImage);
            for (FQAReply fqaReply : fqa.getFqaReplies()) {
                String replyName = fqaReply.getMember().getName();
                byte[] replyImage = fqaReply.getMember().getMemberImage();
                fqaReply.setMemberName(replyName);
                fqaReply.setMemberImge(replyImage);
            }
        }

        return fqaList;
    }

    @PostMapping("/coachingAreasIntroduction")
    public void Coach(@RequestBody Map<String, Object> coachInfo) {

        String coursePath = coachInfo.get("coursePath").toString();//這
        String courseName = coachInfo.get("courseName").toString();
        String partOfBody = coachInfo.get("partOfBody").toString();
        Integer price = Integer.valueOf(coachInfo.get("price").toString());
        String equipment = coachInfo.get("equipment").toString();
        String level = coachInfo.get("level").toString();
        String courseInfo = coachInfo.get("courseInfo").toString();

        Course course = new Course();
        course.setCourseName(courseName);
        course.setPartOfBody(partOfBody);
        course.setPrice(price);
        course.setEquipment(equipment);
        course.setLevel(level);
        course.setCourseInfo(courseInfo);
        courseRepository.save(course);

        //存影片到
        File videoFolder = new File("src/main/resources/static/video");
        System.out.println(videoFolder);
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }
        String videoCoachPath = videoSaveToFile(coursePath, videoFolder);
//        course.setChecked(0);
        course.setUploadTime(new Timestamp(System.currentTimeMillis()));
        course.setCoursePath(videoCoachPath);
        courseService.upload(course);
    }


    //寫進資料夾的方法
    public String videoSaveToFile(String date, File folder) {
        //取名用
//        int startIndex = date.indexOf(",")+100;
//        int endIndex =startIndex + 6;
//        String name = date.substring(startIndex,endIndex);

        UUID uuid = UUID.randomUUID();
        String name = uuid.toString().replace("-", "").substring(0, 6);
        //base64轉byte陣列
        String dateToBase64 = date.substring(date.indexOf(",") + 1);
        byte[] bytes = Base64.getDecoder().decode(dateToBase64);
        File file = new File(folder, name + ".mp4");
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
