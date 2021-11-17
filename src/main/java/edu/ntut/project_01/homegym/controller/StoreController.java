package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.service.CourseCommentService;
import edu.ntut.project_01.homegym.service.CourseService;
import org.hibernate.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/store")
public class StoreController {


    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseCommentService courseCommentService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${course.countsPerPage}")
    Integer size;

    //進入商城(OK)
    @GetMapping("/")
    ResponseEntity<Map<String, Object>> showAllCourse() throws NullPointerException {

        final Integer page = 0;
        Page<Course> showCourse = courseService.findAllCourse(page, size);
        for(Course course:showCourse.getContent()){
            String name = course.getCoach().getMember().getName();
            if(!course.getCourseComments().isEmpty()){
                Map<String,Object> amount = courseCommentService.counntStarAndComment(course.getCourseId());
                course.setStarAndComment(amount);
            }
            course.setCoachName(name);
        }
        Map<String, Object> storeDetail = new HashMap<>();
        storeDetail.put("firstPage", showCourse.getContent());
        storeDetail.put("totalPage", showCourse.getTotalPages());
        return ResponseEntity.ok().body(storeDetail);

    }

    //商城分頁(OK)
    @GetMapping("/allCourse")
    ResponseEntity<Map<String, Object>> showOtherCourse(@RequestParam(required = false) Integer page, @RequestParam(required = false) String partOfBody) {
        System.out.println("page+partOfBody======================================="+page+partOfBody);
        Integer totalPage;
        Page<Course> showCourse;
        Map<String, Object> storeDetail;


        if( page == null && partOfBody == null){
            showCourse = courseService.findAllCourse(0, size);
            for(Course course:showCourse.getContent()){
                String name = course.getCoach().getMember().getName();
                if(!course.getCourseComments().isEmpty()){
                    Map<String,Object> amount = courseCommentService.counntStarAndComment(course.getCourseId());
                    course.setStarAndComment(amount);
                }
                course.setCoachName(name);
            }
            storeDetail = new HashMap<>();
            storeDetail.put("currentPage", showCourse.getContent());
            storeDetail.put("totalPage", showCourse.getTotalPages());
            return ResponseEntity.ok().body(storeDetail);
        }

        if (page != null && page > 0) {
            if (partOfBody == null) {
                totalPage = courseService.getAllCoursesTotalPage(size);
                if (page <= totalPage) {
                    showCourse = courseService.findAllCourse(page - 1, size);
                    for(Course course:showCourse.getContent()){
                        String name = course.getCoach().getMember().getName();
                        if(!course.getCourseComments().isEmpty()){
                            Map<String,Object> amount = courseCommentService.counntStarAndComment(course.getCourseId());
                            course.setStarAndComment(amount);
                        }
                        course.setCoachName(name);
                    }
                    storeDetail = new HashMap<>();
                    storeDetail.put("currentPage", showCourse.getContent());
                    storeDetail.put("totalPage", showCourse.getTotalPages());
                    return ResponseEntity.ok().body(storeDetail);
                }
            } else {
                totalPage = courseService.getCoursesTotalPageByFilter(partOfBody, size);
                if (page <= totalPage) {
                    showCourse = courseService.findCourseByFilter(partOfBody, page-1, size);
                    for(Course course:showCourse.getContent()){
                        String name = course.getCoach().getMember().getName();
                        if(!course.getCourseComments().isEmpty()){
                            Map<String,Object> amount = courseCommentService.counntStarAndComment(course.getCourseId());
                            course.setStarAndComment(amount);
                        }
                        course.setCoachName(name);
                    }
                    storeDetail = new HashMap<>();
                    storeDetail.put("currentPage", showCourse.getContent());
                    storeDetail.put("totalPage", showCourse.getTotalPages());
                    return ResponseEntity.ok().body(storeDetail);
                }
            }
            throw new QueryException("查無此頁面");
        } else {
            showCourse = courseService.findCourseByFilter(partOfBody, 0, size);
            for(Course course:showCourse.getContent()){
                String name = course.getCoach().getMember().getName();
                if(!course.getCourseComments().isEmpty()){
                    Map<String,Object> amount = courseCommentService.counntStarAndComment(course.getCourseId());
                    course.setStarAndComment(amount);
                }
                course.setCoachName(name);
            }
            storeDetail = new HashMap<>();
            storeDetail.put("currentPage", showCourse.getContent());
            storeDetail.put("totalPage", showCourse.getTotalPages());
            return ResponseEntity.ok().body(storeDetail);
        }
    }

    //未購買課程詳細頁(包含教練資訊)
    @GetMapping("/{id}")
    Map<String,Object> showCourseDeatail(@PathVariable Integer id) {


        Map<String,Object> map = new HashMap<>();
        Optional<Course> course  = courseService.findById(id);
        if(course.isPresent()){
            if(!course.get().getCourseComments().isEmpty()&&course.get().getCourseComments()!=null){
                Map<String,Object> amount = courseCommentService.counntStarAndComment(course.get().getCourseId());
                course.get().setStarAndComment(amount);
            }
            Coach coach = course.get().getCoach();
            String coachName = coach.getMember().getName();

            map.put("course",course.get());
            map.put("coach",coach);
            map.put("coachName",coachName);
        }else {
            System.out.println("無資料");
        }

        return map;

    }


}
