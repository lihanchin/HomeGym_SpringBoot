package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private CourseService courseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${course.countsPerPage}")
    Integer size;

    //進入商城
    @GetMapping("/")
    ResponseEntity<Map> showAllCourse() throws NullPointerException {

        final Integer page = 0;
        Page showCourse = courseService.findAllCourse(page, size);
        Map<String,Object> storeDetail = new HashMap<>();

        List<Course> courseList = showCourse.getContent();
        for(Course course:courseList){
            String coachName = course.getCoach().getMember().getName();
            course.setCoachName(coachName);
        }

        storeDetail.put("shoppingCourse", showCourse.getContent());
        storeDetail.put("totalPage", showCourse.getTotalPages());
        return ResponseEntity.ok().body(storeDetail);

    }

    //商城分頁
    @GetMapping("/allCourse")
    ResponseEntity<Map> showOtherCourse(@RequestParam Integer page) {
        final Integer totalPage = courseService.getAllCoursesTotalPage(size);
        if (page != null && page > 0) {
            if (page <= totalPage) {
                Page showCourse = courseService.findAllCourse(page-1, size);
                Map<String,Object> storeDetail = new HashMap<>();
                storeDetail.put("currentPage", showCourse.getContent());
                storeDetail.put("totalPage", showCourse.getTotalPages());
                return ResponseEntity.ok().body(storeDetail);
            }
            throw new NullPointerException("查無此頁面");
        }
        throw new NullPointerException("查無此頁面");
    }

//課程詳細頁(包含評價、教練資訊)
    @GetMapping("/{id}")
    Map<String,Object> showCourseDeatail(@PathVariable Integer id) {

        Map<String,Object> map = new HashMap<>();

        Optional<Course> course  = courseService.findById(id);
        Coach coach = course.get().getCoach();
        String coachName = coach.getMember().getName();
        List<CourseComment> commentlist = new ArrayList<>(course.get().getCourseComments());
        for (CourseComment comment:commentlist){
            String name = comment.getMember().getName();
            byte[] memberImage = comment.getMember().getMemberImage();
            comment.setMemberImge(memberImage);
            comment.setMemberName(name);

        }
        map.put("course",course.get());
        map.put("coach",coach);
        map.put("coachName",coachName);
        map.put("commentlist",commentlist);
        return map;

    }


}
