package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.service.CourseService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.QueryMethodParameterConversionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Course> findAllCourse(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return courseRepository.findAll(pageRequest);
    }

    @Override
    public Integer getAllCoursesTotalPage(Integer size) {
        return (int)Math.ceil(courseRepository.findAll().size()/(double)size);
    }

    @Override
    public Page<Course> findCourseByFilter(String partOfBody, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return courseRepository.findByPartOfBody(partOfBody, pageRequest);
    }

    @Override
    public Integer getCoursesTotalPageByFilter(String partOfBody, Integer size) {
        if(courseRepository.findCourseByPartOfBody(partOfBody).isPresent()){
            return (int)Math.ceil(courseRepository.findCourseByPartOfBody(partOfBody).get().size()/(double)size);
        }
        throw new QueryException("查無此部位的相關影片");
    }
}
