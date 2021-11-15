package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseCommentRepository  extends JpaRepository<CourseComment,Integer> {

    @Query(value = "SELECT AVG(star) FROM CourseComment  WHERE course_id = :courseId")
    Double countStar(@Param(value= "courseId") Integer courseId);

    Page<CourseComment> findCourseCommentByCourse_CourseId(Integer courseId, Pageable pageable);
}
