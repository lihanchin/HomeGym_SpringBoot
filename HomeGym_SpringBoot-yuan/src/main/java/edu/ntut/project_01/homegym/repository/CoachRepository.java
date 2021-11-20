package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CoachRepository extends JpaRepository<Coach,Integer> {

}
