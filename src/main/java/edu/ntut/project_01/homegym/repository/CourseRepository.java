package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course,Integer> {
    Optional<List<Course>> findCourseByPartOfBody(String partOfBody);
    Page<Course> findByPartOfBody(String partOfBody, Pageable pageable);
}
