package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.FQA;
import edu.ntut.project_01.homegym.model.FQAReply;
import edu.ntut.project_01.homegym.service.CourseCommentService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.FQAReplyService;
import edu.ntut.project_01.homegym.service.FQAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    //存留言並返回資料
    @PostMapping ("/addComment")
    public Map<String,Object> addCommentAndCountStar(@RequestBody CourseComment courseComment) {
        System.out.println(courseComment.getCommentContent());
        Map<String,Object> map = new HashMap<>();
        //先存進資料庫
        courseCommentService.save(courseComment);

        //再抓取資料庫資料運算回傳
        Integer star = courseCommentService.countStar();

        map.put("star",star);
        return map;
    }


    @PostMapping ("/addFQA/{courseId}")
    public Integer addFQA(@RequestBody FQA fqaInput ,@PathVariable Integer courseId) {
        //找到課程
        Course course = courseService.findById(courseId).get();
        course.getFqas().add(fqaInput);
        //先存進資料庫
        courseService.save(course);

        //再抓取資料庫資料運算回傳
        Integer star = courseCommentService.countStar();

        return star;
    }

    @PostMapping ("/addFQAReply/{fqaId}")
    public void addFQAReply(@RequestBody FQAReply fqaReplyInput, @PathVariable Integer fqaId) {
        FQA fqa = fqaService.findById(fqaId).get();
        fqa.getFqaReplies().add(fqaReplyInput);
        fqaService.save(fqa);

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
