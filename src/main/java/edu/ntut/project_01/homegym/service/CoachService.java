package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Coach;
import org.springframework.web.bind.annotation.RequestBody;

public interface CoachService {

    public String apply( Coach coach);
}
