package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Video;
import edu.ntut.project_01.homegym.repository.VideoRepository;
import edu.ntut.project_01.homegym.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Page<Video> findAllCourse(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return videoRepository.findAll(pageRequest);
    }

    @Override
    public Integer getAllCoursesTotalPage(Integer size) {
        return (int)Math.ceil(videoRepository.findAll().size()/(double)size);
    }
}
