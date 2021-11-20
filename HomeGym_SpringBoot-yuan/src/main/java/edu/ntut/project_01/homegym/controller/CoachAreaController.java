package edu.ntut.project_01.homegym.controller;


import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.CoachRepository;
import edu.ntut.project_01.homegym.service.CoachService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@PreAuthorize("hasRole('COACH')")
@RequestMapping("/coachArea")
public class CoachAreaController {

    @Value("${course.countsPerPage}")
    private Integer size;
    @Value("${jwt.header}")
    private String authorization;
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

    //載入教練資料
    @GetMapping("/")
    public ResponseEntity<Coach> showCoachInfo(HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(authorization);
        Member member = memberService.findMemberByToken(authorizationHeader);
        Coach coach = member.getCoach();
        return ResponseEntity.ok().body(coach);
    }

    //顯示課程
    @GetMapping("/mycourse")
    ResponseEntity<Map<String, Object>> coachmycourse(@RequestParam(required = false) Integer page, HttpServletRequest httpServletRequest) {

        String header = httpServletRequest.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Integer coachId = member.getCoach().getCoachId();
        System.out.println(coachId);
        Page<Course> showCourse;
        Map<String, Object> storeDetail;

        if (page != null) {
            showCourse = courseService.findCourseByCoachArea(coachId, page-1, size);
        } else {
            showCourse = courseService.findCourseByCoachArea(coachId, 0, size);
        }

        System.out.println(showCourse);
        storeDetail = new HashMap<>();
        storeDetail.put("currentPage", showCourse.getContent());
        storeDetail.put("totalPage", showCourse.getTotalPages());
        return ResponseEntity.ok().body(storeDetail);
    }

    //我的課程關鍵字搜尋
    @GetMapping("/keyword")
    public ResponseEntity<Map<String, Object>> keyword(@RequestParam String keyword, @RequestParam(required = false) Integer page, HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Integer coachId = member.getCoach().getCoachId();
        Map<String, Object> response = new HashMap<>();

        if (page != null) {
            response.put("courseList", courseService.findCoursesByCoachAndName(coachId, keyword, page - 1, size).getContent());
            response.put("totalPage", courseService.findCoursesByCoachAndName(coachId, keyword, page - 1, size).getTotalPages());
        } else {
            response.put("courseList", courseService.findCoursesByCoachAndName(coachId, keyword, 0, size).getContent());
            response.put("totalPage", courseService.findCoursesByCoachAndName(coachId, keyword, 0, size).getTotalPages());
        }

        return ResponseEntity.ok().body(response);
    }

    //編輯教練資料
    @PutMapping("/editInfo")
    public ResponseEntity<Map<String, Object>> editCoachInfo(@RequestBody Coach coach, HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(authorization);
        coachId = memberService.findMemberByToken(authorizationHeader).getCoach().getCoachId();
        Map<String, Object> editResponse = new HashMap<>();

        String message = coachService.edit(coach, coachId);

        editResponse.put("coachInfo", coachRepository.findById(coachId));
        editResponse.put("message", message);

        return ResponseEntity.ok().body(editResponse);
    }

}
