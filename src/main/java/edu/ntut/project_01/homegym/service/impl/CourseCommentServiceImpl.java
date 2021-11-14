package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.repository.CoachRepository;
import edu.ntut.project_01.homegym.repository.CourseCommentRepository;
import edu.ntut.project_01.homegym.service.CourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseCommentServiceImpl implements CourseCommentService {

    @Autowired
    private CourseCommentRepository courseCommentRepository;


    public CourseCommentServiceImpl(CourseCommentRepository courseCommentRepository) {
        this.courseCommentRepository = courseCommentRepository;
    }

    @Override
    public void save( CourseComment curseComment) {

        courseCommentRepository.save(curseComment);
    }

    @Override
    public Page<CourseComment> findCourseComment(Integer courseId, Integer pageNo, Integer size) {
        PageRequest pageRequest = PageRequest.of(pageNo, size);
        return courseCommentRepository.findCourseCommentByCourse_CourseId(courseId,pageRequest);
    }


}
