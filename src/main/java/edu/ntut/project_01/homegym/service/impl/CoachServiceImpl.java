package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Coach;
import edu.ntut.project_01.homegym.repository.CoachRepository;
import edu.ntut.project_01.homegym.service.CoachService;
import edu.ntut.project_01.homegym.util.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachRepository coachRepository;


    @Override
    public String apply(Coach coach){
        coachRepository.save(coach);
        return "申請成功";
    }

    @Override
    public String edit(Coach coach, Integer coachId) {
        Coach theCoach = coachRepository.findById(coachId).orElseThrow();
        System.out.println("coach.getSkill()============================"+coach.getSkill());
        theCoach.setSkill(coach.getSkill());
        theCoach.setExperience(coach.getExperience());
        theCoach.setCoachInfo(coach.getCoachInfo());

        if(coach.getCoachImage() != null&& !coach.getCoachImage().equals(theCoach.getCoachImage())){
            File imageFolder = new File("/src/main/resources/static/coachImages");
            System.out.println(imageFolder);
            if(!imageFolder.exists()){
                imageFolder.mkdirs();
            }
            String coachImagePath = GlobalService.imageSaveToFile(coach.getCoachImage(),imageFolder,coachId,".jpg");
            System.out.println("coachImagePath======="+coachImagePath);
            theCoach.setCoachImage(coachImagePath);
        }

        coachRepository.save(theCoach);
        return "修改成功";
    }


}
