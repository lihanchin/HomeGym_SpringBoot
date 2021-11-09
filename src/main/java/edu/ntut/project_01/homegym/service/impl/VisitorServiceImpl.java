package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Visitor;
import edu.ntut.project_01.homegym.repository.VisitorRepository;
import edu.ntut.project_01.homegym.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    VisitorRepository visitorRepository;

    @Override
    public void addMessage(Visitor visitor) {
        visitorRepository.save(visitor);

    }
}
