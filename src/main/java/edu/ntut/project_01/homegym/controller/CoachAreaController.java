package edu.ntut.project_01.homegym.controller;


import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.repository.CoachRepository;
import edu.ntut.project_01.homegym.service.CoachService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/coacharea")
public class CoachAreaController {

    @Value("${course.countsPerPage}")
    private Integer size;
    @Value("${jwt.header}")
    private String HEADER;
    private String authorizationHeader;
    private Integer coachId;

    private CourseService courseService;
    private CoachService coachService;
    private CoachRepository coachRepository;
    private MemberService memberService;

    @Autowired
    public CoachAreaController(CourseService courseService, CoachService coachService, CoachRepository coachRepository, MemberService memberService) {
        this.courseService = courseService;
        this.coachService = coachService;
        this.coachRepository = coachRepository;
        this.memberService = memberService;
    }

    @GetMapping("/mycourse")
    ResponseEntity<Map<String, Object>> coachmycourse(@RequestParam(required = false) Integer page, @RequestParam Integer coachId){

        Page<Course> showCourse;
        Map<String, Object> storeDetail;


        showCourse = courseService.findCourseByCoachArea(coachId, 0, size);
        storeDetail = new HashMap<>();
        storeDetail.put("currentPage", showCourse.getContent());
        storeDetail.put("totalPage", showCourse.getTotalPages());
        return ResponseEntity.ok().body(storeDetail);
    }



    @GetMapping("/keyword")
    public ResponseEntity<List<Course>> keyword(@RequestParam(required = false) String keyword){
        return courseService.findCoursesByKeyword(keyword);
    }

    @PutMapping("/editInfo")
    public ResponseEntity<Map<String,Object>> editCoachInfo(@RequestBody Coach coach, HttpServletRequest httpServletRequest){
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        coachId = memberService.findMemberByToken(authorizationHeader).getCoach().getCoachId();
        Map<String,Object> editResponse = new HashMap<>();
        String message = coachService.edit(coach,coachId);

        editResponse.put("coachInfo", coachRepository.findById(coachId));
        editResponse.put("message",message);

        return ResponseEntity.ok().body(editResponse);
    }


}
