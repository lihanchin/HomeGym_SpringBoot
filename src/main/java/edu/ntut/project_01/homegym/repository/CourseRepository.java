package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
}