package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.service.CourseCommentService;
import edu.ntut.project_01.homegym.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {

    @Value("${course.countsPerPage}")
    private Integer size;
    private Map<String, Object> response;

    private final CourseService courseService;
    private final CourseCommentService courseCommentService;

    @Autowired
    public StoreController(CourseService courseService, CourseCommentService courseCommentService) {
        this.courseService = courseService;
        this.courseCommentService = courseCommentService;
    }

    //進入商城(OK)
    @GetMapping("/")
    ResponseEntity<Map<String, Object>> showAllCourse() throws NullPointerException {
        final Integer page = 0;
        Page<Course> showCourse = courseService.findAllCourse(page, size);
        //noinspection DuplicatedCode
        for (Course course : showCourse.getContent()) {
            String name = course.getCoach().getMember().getName();
            if (!course.getCourseComments().isEmpty()) {
                Map<String, Object> amount = courseCommentService.countStarAndComment(course.getCourseId());
                course.setStarAndComment(amount);
            }
            course.setCoachName(name);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("firstPage", showCourse.getContent());
        response.put("totalPage", showCourse.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    //商城分頁(OK)
    @GetMapping("/allCourse")
    ResponseEntity<Map<String, Object>> showOtherCourse(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false) String partOfBody) {
        log.info("page ====>" + page);
        log.info("partOfBody ====>" + partOfBody);
        Integer totalPage;
        Page<Course> showCourse;

        if (page == null && partOfBody == null) {
            showCourse = courseService.findAllCourse(0, size);
            return ResponseEntity.ok().body(courseService.responsePageDetail(showCourse));
        }

        if (page != null && page > 0) {
            if (partOfBody == null) {
                totalPage = courseService.getAllCoursesTotalPage(size);
                if (page <= totalPage) {
                    showCourse = courseService.findAllCourse(page - 1, size);
                    return ResponseEntity.ok().body(courseService.responsePageDetail(showCourse));
                }
            } else {
                totalPage = courseService.getCoursesTotalPageByFilter(partOfBody, size);
                if (page <= totalPage) {
                    showCourse = courseService.findCourseByFilter(partOfBody, page - 1, size);
                    return ResponseEntity.ok().body(courseService.responsePageDetail(showCourse));
                }
            }
            throw new QueryException("查無此頁面");
        } else {
            showCourse = courseService.findCourseByFilter(partOfBody, 0, size);
            return ResponseEntity.ok().body(courseService.responsePageDetail(showCourse));
        }
    }

    //未購買課程詳細頁(包含教練資訊)
    @GetMapping("/{id}")
    Map<String, Object> showCourseDeatail(@PathVariable Integer id) {
        response = new HashMap<>();
        Optional<Course> course = courseService.findById(id);
        if (course.isPresent()) {
            if (!course.get().getCourseComments().isEmpty() && course.get().getCourseComments() != null) {
                Map<String, Object> amount = courseCommentService.countStarAndComment(course.get().getCourseId());
                course.get().setStarAndComment(amount);
            }
            Coach coach = course.get().getCoach();
            String coachName = coach.getMember().getName();

            response.put("course", course.get());
            response.put("coach", coach);
            response.put("coachName", coachName);
        } else {
            System.out.println("無資料");
        }

        return response;
    }

    //關鍵字查詢
    @GetMapping("/keyword")
    public ResponseEntity<Map<String, Object>> keyword(@RequestParam String keyword, @RequestParam(required = false) Integer page) {
        Map<String, Object> response = new HashMap<>();
        if (page != null && page != 0) {
            response.put("courseList", courseService.findCoursesByKeyword(keyword, page - 1, size).getContent());
            response.put("totalPage", courseService.findCoursesByKeyword(keyword, page - 1, size).getTotalPages());
        } else {
            response.put("courseList", courseService.findCoursesByKeyword(keyword, 0, size).getContent());
            response.put("totalPage", courseService.findCoursesByKeyword(keyword, 0, size).getTotalPages());
        }
        return ResponseEntity.ok().body(response);
    }

}
