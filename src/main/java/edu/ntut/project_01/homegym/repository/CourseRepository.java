package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.CourseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course,Integer> {
    Optional<List<Course>> findCourseByPartOfBody(String partOfBody);
    Page<Course> findByPartOfBody(String partOfBody, Pageable pageable);
    //從課程的Table抓出教練的Id，然後做分頁(這樣抓一定要加底線)
    Page<Course> findCourseByCoach_CoachId(Integer coachId, Pageable pageable);
    //從課程Table中抓，利用課程名稱做關鍵字查詢在首頁和教練專區都有使用到
    Optional<List<Course>> findCoursesByCourseNameContaining(String keyword);   //一個interface

}
