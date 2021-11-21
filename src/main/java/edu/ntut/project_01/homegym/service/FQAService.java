package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.FQA;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FQAService {

    void save(FQA fqa);
    List<FQA> showFQA(int courseId);
    Optional<FQA> findById(int id);
    Page<FQA> findAllFQA(Integer page, Integer size);
}
