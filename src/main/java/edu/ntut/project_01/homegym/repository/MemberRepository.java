package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member,Integer> {
}
