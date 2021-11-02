package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Video;
import org.springframework.data.domain.Page;


public interface CourseService {
    Page<Video> findAllCourse(Integer page, Integer size);
    Integer getAllCoursesTotalPage(Integer size);
}
