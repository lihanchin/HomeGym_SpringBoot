package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.FQA;
import edu.ntut.project_01.homegym.model.FQAReply;
import edu.ntut.project_01.homegym.service.CourseCommentService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.FQAReplyService;
import edu.ntut.project_01.homegym.service.FQAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    //算星星
    public Map<String,Object> countStar(){
        Integer star = courseCommentService.countStar();
        Map<String,Object> map = new HashMap<>();
        map.put("star",star);
        return map;
    }



    @PostMapping ("/addComment/{courseId}")
    public void addComment(@RequestBody CourseComment courseComment,@PathVariable Integer courseId) {
        Course course = courseService.findById(courseId).get();
        courseComment.setCourse(course);
        courseCommentService.save(courseComment);

    }


    @PostMapping ("/addFQA/{courseId}")
    public void addFQA(@RequestBody FQA fqaInput ,@PathVariable Integer courseId) {

        Course course = courseService.findById(courseId).get();

        fqaInput.setCourse(course);
        fqaService.save(fqaInput);

    }

    @PostMapping ("/addFQAReply/{fqaId}")
    public void addFQAReply(@RequestBody FQAReply fqaReplyInput, @PathVariable Integer fqaId) {
        FQA fqa = fqaService.findById(fqaId).get();
        fqaReplyInput.setFqa(fqa);
        fqaReplyService.save(fqaReplyInput);

    }



    @GetMapping ("/{id}")
    public List<FQA> showFQA(@PathVariable Integer id) {
        List<FQA> fqaList= fqaService.showFQA(id);

        for (FQA fqa: fqaList){
            String name = fqa.getMember().getName();
            byte[] memberImage = fqa.getMember().getMemberImage();
            fqa.setMemberName(name);
            fqa.setMemberImge(memberImage);
            for (FQAReply fqaReply: fqa.getFqaReplies()){
                String replyName = fqaReply.getMember().getName();
                byte[] replyImage = fqaReply.getMember().getMemberImage();
                fqaReply.setMemberName(replyName);
                fqaReply.setMemberImge(replyImage);
            }
        }

        return fqaList;
    }


}
