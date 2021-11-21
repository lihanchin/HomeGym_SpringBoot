package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.CourseComment;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CourseCommentService {

    void save(CourseComment curseComment);

    Page<CourseComment> findCourseComment(Integer courseId, Integer pageNo, Integer size);

    Map<String, Object> countStarAndComment(Integer courseId);
}
