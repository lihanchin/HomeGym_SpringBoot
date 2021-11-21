package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.repository.CourseCommentRepository;
import edu.ntut.project_01.homegym.service.CourseCommentService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class CourseCommentServiceImpl implements CourseCommentService {

    private final CourseCommentRepository courseCommentRepository;

    @Autowired
    public CourseCommentServiceImpl(CourseCommentRepository courseCommentRepository) {
        this.courseCommentRepository = courseCommentRepository;
    }

    @Override
    public void save(CourseComment curseComment) {

        courseCommentRepository.save(curseComment);
    }

    @Override
    public Page<CourseComment> findCourseComment(Integer courseId, Integer pageNo, Integer size) {
        PageRequest pageRequest = PageRequest.of(pageNo, size);
        return courseCommentRepository.findCourseCommentByCourse_CourseId(courseId, pageRequest);
    }

    @Override
    public Map<String, Object> countStarAndComment(Integer courseId) {
        Map<String, Object> map = new HashMap<>();
        map.put("commentAmount", courseCommentRepository.countComment(courseId));
        map.put("star", courseCommentRepository.countStar(courseId));
        return map;
    }


}
