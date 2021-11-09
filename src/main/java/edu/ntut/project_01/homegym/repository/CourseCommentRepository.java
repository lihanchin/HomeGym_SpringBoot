package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseCommentRepository  extends JpaRepository<CourseComment,Integer> {

    @Query(value = "SELECT AVG(star) FROM CourseComment")
    Double countStar();
}
