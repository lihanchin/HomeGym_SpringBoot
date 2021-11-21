package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.repository.CoachRepository;
import edu.ntut.project_01.homegym.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {


    private final CoachRepository coachRepository;

    @Autowired
    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public String apply(Coach coach){
        coachRepository.save(coach);
        return "申請成功";
    }

    @Override
    public String edit(Coach coach, Integer coachId) {
        Coach theCoach = coachRepository.findById(coachId).orElseThrow();
        theCoach.setSkill(coach.getSkill());
        theCoach.setExperience(coach.getExperience());
        theCoach.setCoachInfo(coach.getCoachInfo());

        if(coach.getCoachImage() != null&& !coach.getCoachImage().equals(theCoach.getCoachImage())){
            theCoach.setCoachImage(coach.getCoachImage());
        }

        coachRepository.save(theCoach);
        return "修改成功";
    }


}
