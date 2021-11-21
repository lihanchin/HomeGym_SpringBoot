package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;


public interface CourseService {
    //撈出所有課程
    Page<Course> findAllCourse(Integer page, Integer size);

    //所有課程總共可以分幾頁
    Integer getAllCoursesTotalPage(Integer size);

    //撈出有經過篩選的課程
    Page<Course> findCourseByFilter(String partOfBody, Integer page, Integer size);

    //有篩選過的課程總共可以分幾頁
    Integer getCoursesTotalPageByFilter(String partOfBody, Integer size);

    //儲存課程影片相關資訊
    String upload(@RequestBody Course course);

    //透過關鍵字篩選課程列表
    Page<Course> findCoursesByKeyword(String keyword, Integer page, Integer size);

    //在教練專區利用Id抓出來的課程，進行分頁還有總頁數
    Page<Course> findCourseByCoachArea (Integer coachId, Integer page, Integer size);

    Optional<Course> findById(Integer id);

    void save(Course course);

    Integer countStar(Integer courseId);

    Page<Course> findCoursesByCoachAndName(Integer coachId, String keyword, Integer page, Integer size);

    Map<String,Object> responsePageDetail(Page<Course> page);
}
