package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.FQA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FQARepository extends JpaRepository<FQA,Integer> {

     List<FQA> findFQAByCourseCourseId(int courseId);
}
