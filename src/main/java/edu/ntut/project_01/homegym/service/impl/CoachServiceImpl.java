package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.repository.CoachRepository;
import edu.ntut.project_01.homegym.service.CoachService;
import edu.ntut.project_01.homegym.util.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.Optional;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    private CoachRepository coachRepository;
    @Autowired
    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public String apply(@RequestBody Coach coach){
        coachRepository.save(coach);
        return "申請成功";
    }

    @Override
    public String edit(Coach coach, Integer coachId) {
        Coach theCoach = coachRepository.findById(coachId).orElseThrow();

        theCoach.setSkill(coach.getSkill());
        theCoach.setExperience(coach.getExperience());
        theCoach.setCoachInfo(coach.getCoachInfo());

        if(coach.getCoachImage() != null){
            File imageFolder = new File("src/main/resources/static/coachImages");
            System.out.println(imageFolder);
            if(!imageFolder.exists()){
                imageFolder.mkdirs();
            }
            String coachImagePath = GlobalService.imageSaveToFile(coach.getCoachImage(),imageFolder);
            theCoach.setCoachImage(coachImagePath);
        }

        coachRepository.save(theCoach);
        return "修改成功";
    }


}
