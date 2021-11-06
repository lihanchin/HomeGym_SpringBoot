package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Course;
import org.springframework.data.domain.Page;


public interface CourseService {
    //撈出所有課程
    Page<Course> findAllCourse(Integer page, Integer size);

    //所有課程總共可以分幾頁
    Integer getAllCoursesTotalPage(Integer size);

    //撈出有經過篩選的課程
    Page<Course> findCourseByFilter(String partOfBody, Integer page, Integer size);

    //有篩選過的課程總共可以分幾頁
    Integer getCoursesTotalPageByFilter(String partOfBody, Integer size);
}
