package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor,Integer> {
}
