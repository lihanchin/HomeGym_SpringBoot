package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach,Integer> {

}
