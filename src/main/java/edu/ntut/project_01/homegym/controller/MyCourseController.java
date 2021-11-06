package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MyCourseController {

    @Value("${course.countsPerPage}")
    private Integer pageSize;

    private MemberService memberService;

    @Autowired
    public MyCourseController(MemberService memberService, MemberRepository memberRepository, CourseRepository courseRepository) {
        this.memberService = memberService;
    }

    //已買課程分頁
    @GetMapping("/myCourse/allCourse/{memberId}")
    ResponseEntity<Map<String, Object>> myCourse(@PathVariable Integer memberId, @RequestParam(required = false) Integer page) {
        if (page != null) {
            if (page > 0) {
                return memberService.findMyCourses(memberId, page, pageSize);
            }
            throw new IllegalArgumentException("頁數不得小於、等於0");
        }
        return memberService.findMyCourses(memberId, 1, pageSize);

    }
}
