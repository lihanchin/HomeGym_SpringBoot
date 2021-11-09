package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.FQA;
import edu.ntut.project_01.homegym.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FQARepository extends JpaRepository<FQA,Integer> {

     List<FQA> findFQAByCourseCourseId(int courseId);
}
