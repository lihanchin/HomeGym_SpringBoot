package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Course;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface CourseService {
    Page<Course> findAllCourse(Integer page, Integer size);
    Integer getAllCoursesTotalPage(Integer size);
    Optional<Course> findById(Integer id);
    void save(Course course);
}