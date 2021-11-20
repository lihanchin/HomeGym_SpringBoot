package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.FQAReply;
import edu.ntut.project_01.homegym.repository.FQAReplyRepository;
import edu.ntut.project_01.homegym.repository.FQARepository;
import edu.ntut.project_01.homegym.service.FQAReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FQAReplyServiceImpl implements FQAReplyService{

    @Autowired
    private FQAReplyRepository fqaReplyRepository;

    @Override
    public void save(FQAReply fqaReply) {
        fqaReplyRepository.save(fqaReply);

    }
}
