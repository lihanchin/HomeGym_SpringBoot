package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Coach;

public interface CoachService {

    String apply(Coach coach);
    String edit(Coach coach, Integer coachId);
}
