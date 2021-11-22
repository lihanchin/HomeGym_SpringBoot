package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.CourseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseCommentRepository extends JpaRepository<CourseComment, Integer> {

    @Query(value = "SELECT ROUND(AVG(star), 0) FROM course_comment  WHERE course_id = :courseId", nativeQuery = true)
    Double countStar(@Param(value = "courseId") Integer courseId);

    @Query(value = " SELECT COUNT(*) FROM course_comment WHERE course_id = :courseId", nativeQuery = true)
    Integer countComment(@Param(value = "courseId") Integer courseId);

    Optional<List<CourseComment>> findCourseCommentByCourse_CourseId(Integer courseId);

    Page<CourseComment> findCourseCommentByCourse_CourseId(Integer courseId, Pageable pageable);
}
