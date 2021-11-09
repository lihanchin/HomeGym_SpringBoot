package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.FQA;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface FQAService {

    void save(FQA fqa);
    public List<FQA> showFQA(int courseId);
    Optional<FQA> findById(int id);
}
