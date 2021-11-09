package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.CourseComment;
import edu.ntut.project_01.homegym.model.Member;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CourseCommentService {
    void save( CourseComment curseComment);
    Integer countStar();
}
