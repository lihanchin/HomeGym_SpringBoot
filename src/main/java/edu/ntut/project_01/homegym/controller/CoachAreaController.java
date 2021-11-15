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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/coachArea")
public class CoachAreaController {

    @Value("${course.countsPerPage}")
    private Integer size;
    @Value("${jwt.header}")
    private String HEADER;
    private String authorizationHeader;
    private Integer coachId;

    @Autowired
    private CourseService courseService;
    @Autowired
    private CoachService coachService;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private MemberService memberService;

    @Value("${jwt.header}")
    private String authorization;

    //載入教練資料
    @GetMapping("/")
    public ResponseEntity<Coach> showCoachInfo(HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        Member member = memberService.findMemberByToken(authorizationHeader);
        Coach coach = member.getCoach();
        return ResponseEntity.ok().body(coach);
    }

    //顯示課程
    @GetMapping("/mycourse")
    ResponseEntity<Map<String, Object>> coachmycourse(@RequestParam(required = false) Integer page,HttpServletRequest httpServletRequest){

        String header= httpServletRequest.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Integer coachId = member.getCoach().getCoachId();
        System.out.println(coachId);
        Page<Course> showCourse;
        Map<String, Object> storeDetail;


        showCourse = courseService.findCourseByCoachArea(coachId, 0, size);
        storeDetail = new HashMap<>();
        storeDetail.put("currentPage", showCourse.getContent());
        storeDetail.put("totalPage", showCourse.getTotalPages());
        return ResponseEntity.ok().body(storeDetail);
    }

    //我的課程關鍵字搜尋
    @GetMapping("/keyword")
    public ResponseEntity<List<Course>> keyword(@RequestParam(required = false) String keyword, HttpServletRequest httpServletRequest){
        String header= httpServletRequest.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        Integer coachId = member.getCoach().getCoachId();

        return courseService.findCoursesByKeyword(keyword);
    }

    //編輯教練資料
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
