package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.repository.CourseCommentRepository;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.QueryException;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Optional;

import java.util.Optional;


@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseCommentRepository courseCommentRepository;


    public Optional<Course> findById(Integer id) {
        return courseRepository.findById(id);
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Page<Course> findAllCourse(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return courseRepository.findAll(pageRequest);
    }

    @Override
    public Integer getAllCoursesTotalPage(Integer size) {
        return (int) Math.ceil(courseRepository.findAll().size() / (double) size);
    }

    @Override
    public Page<Course> findCourseByFilter(String partOfBody, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return courseRepository.findByPartOfBody(partOfBody, pageRequest);
    }

    @Override
    public Integer getCoursesTotalPageByFilter(String partOfBody, Integer size) {
        if (courseRepository.findCourseByPartOfBody(partOfBody).isPresent()) {
            return (int) Math.ceil(courseRepository.findCourseByPartOfBody(partOfBody).get().size() / (double) size);
        }
        throw new QueryException("查無此部位的相關影片");
    }

    @Override
    public Page<Course> findCoursesByKeyword(String keyword, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return courseRepository.findCoursesByCourseNameContaining(keyword, pageRequest);
    }

    @Override
    public Page<Course> findCourseByCoachArea(Integer coachId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        System.out.println();
        return courseRepository.findCourseByCoach_CoachId(coachId, pageRequest);
    }

    @Override
    public String upload(@RequestBody Course course){
        courseRepository.save(course);
        return "上傳成功";
    }

    @Override
    public Integer countStar(Integer courseId) {
        return courseCommentRepository.countStar(courseId).intValue();
    }

    @Override
    public Page<Course> findCoursesByCoachAndName(Integer coachId, String keyword, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return courseRepository.findCoursesByCoach_CoachIdAndCourseNameContaining(coachId,keyword,pageRequest);
    }

}
