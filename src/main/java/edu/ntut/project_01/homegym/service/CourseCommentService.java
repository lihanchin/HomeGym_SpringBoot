package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CourseCommentService {
    void save( CourseComment curseComment);
    Page<CourseComment> findCourseComment(Integer courseId, Integer pageNo, Integer size);
}
