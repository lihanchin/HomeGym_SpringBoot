package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StoreController {

    @Autowired
    private CourseService courseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${course.countsPerPage}")
    Integer size;

    //進入商城
    @GetMapping("/store")
    ResponseEntity<Map> showAllCourse() throws NullPointerException {

        final Integer page = 0;
        Page showCourse = courseService.findAllCourse(page, size);
        Map<String, Object> storeDetail = new HashMap<>();
        storeDetail.put("firstPage", showCourse.getContent());
        storeDetail.put("totalPage", showCourse.getTotalPages());
        return ResponseEntity.ok().body(storeDetail);

    }

    //商城分頁
    @GetMapping("/store/allCourse/")
    ResponseEntity<Map> showOtherCourse(@RequestParam(required = false) Integer page, @RequestParam(required = false) String partOfBody) {

        final Integer totalPage;
        Page<Course> showCourse;
        Map<String, Object> storeDetail;

        if (page != null && page > 0) {
            if (partOfBody == null) {
                totalPage = courseService.getAllCoursesTotalPage(size);
                if (page <= totalPage) {
                    showCourse = courseService.findAllCourse(page - 1, size);
                    storeDetail = new HashMap<>();
                    storeDetail.put("currentPage", showCourse.getContent());
                    storeDetail.put("totalPage", showCourse.getTotalPages());
                    return ResponseEntity.ok().body(storeDetail);
                }
            } else {
                totalPage = courseService.getCoursesTotalPageByFilter(partOfBody, size);
                if (page <= totalPage) {
                    showCourse = courseService.findCourseByFilter(partOfBody, page-1, size);
                    storeDetail = new HashMap<>();
                    storeDetail.put("currentPage", showCourse.getContent());
                    storeDetail.put("totalPage", showCourse.getTotalPages());
                    return ResponseEntity.ok().body(storeDetail);
                }
            }
            throw new NullPointerException("查無此頁面");
        } else {
            showCourse = courseService.findCourseByFilter(partOfBody, 0, size);
            storeDetail = new HashMap<>();
            storeDetail.put("currentPage", showCourse.getContent());
            storeDetail.put("totalPage", showCourse.getTotalPages());
            return ResponseEntity.ok().body(storeDetail);
        }
    }

}
