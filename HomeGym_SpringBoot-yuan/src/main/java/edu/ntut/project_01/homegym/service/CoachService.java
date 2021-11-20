package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Coach;
import org.springframework.web.bind.annotation.RequestBody;

public interface CoachService {

    String apply(Coach coach);
    String edit(Coach coach, Integer coachId);
}
