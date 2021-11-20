package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.FQA;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.FQARepository;
import edu.ntut.project_01.homegym.service.FQAService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FQAServiceImpl implements FQAService {

    @Autowired
    private FQARepository fQARepository;

    @Override
    public void save(FQA fqa) {
        fQARepository.save(fqa);
    }

    @Override
    public List<FQA> showFQA(int courseId){

        return fQARepository.findFQAByCourseCourseId(courseId);
    }

    @Override
    public Optional<FQA> findById(int courseId){

        return fQARepository.findById(courseId);
    }

    @Override
    public Page<FQA> findAllFQA(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return fQARepository.findAll(pageRequest);
    }

}
