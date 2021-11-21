package edu.ntut.project_01.homegym.controller;


import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.service.CoachService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@PreAuthorize("hasRole('COACH')")
@RequestMapping("/coachArea")
public class CoachAreaController {

    @Value("${course.countsPerPage}")
    private Integer size;
    @Value("${jwt.header}")
    private String authorization;
    private Map<String, Object> response;

    private final CourseService courseService;
    private final CoachService coachService;
    private final MemberService memberService;

    @Autowired
    public CoachAreaController(CourseService courseService, CoachService coachService, MemberService memberService) {
        this.courseService = courseService;
        this.coachService = coachService;
        this.memberService = memberService;
    }

    //載入教練資料
    @GetMapping("/")
    public ResponseEntity<Coach> showCoachInfo(HttpServletRequest httpServletRequest) {
        Coach coach = memberService.findMemberByToken(httpServletRequest.getHeader(authorization)).getCoach();

        return ResponseEntity.ok().body(coach);
    }

    //顯示課程
    @GetMapping("/mycourse")
    ResponseEntity<Map<String, Object>> myCourse(HttpServletRequest httpServletRequest, @RequestParam(required = false, defaultValue = "1") Integer page) {
        response = new HashMap<>();
        Integer coachId = memberService.findMemberByToken(httpServletRequest.getHeader(authorization)).getCoach().getCoachId();

        if (page <= 0) {
            page = 1;
        }

        Page<Course> showCourse = courseService.findCourseByCoachArea(coachId, page - 1, size);
        response.put("currentPage", showCourse.getContent());
        response.put("totalPage", showCourse.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    //我的課程關鍵字搜尋
    @GetMapping("/keyword")
    public ResponseEntity<Map<String, Object>> keyword(HttpServletRequest httpServletRequest, @RequestParam String keyword, @RequestParam(required = false, defaultValue = "1") Integer page) {
        response = new HashMap<>();
        Integer coachId = memberService.findMemberByToken(httpServletRequest.getHeader(authorization)).getCoach().getCoachId();

        if (page <= 0) {
            page = 1;
        }

        response.put("courseList", courseService.findCoursesByCoachAndName(coachId, keyword, page - 1, size).getContent());
        response.put("totalPage", courseService.findCoursesByCoachAndName(coachId, keyword, page - 1, size).getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    //編輯教練資料
    @PutMapping("/editInfo")
    public ResponseEntity<Map<String, Object>> editCoachInfo(HttpServletRequest httpServletRequest, @RequestBody Coach coach) {
        response = new HashMap<>();
        Integer coachId = memberService.findMemberByToken(httpServletRequest.getHeader(authorization)).getCoach().getCoachId();

        String message = coachService.edit(coach, coachId);
        response.put("coachInfo", courseService.findById(coachId).orElseThrow());
        response.put("message", message);

        return ResponseEntity.ok().body(response);
    }

}
