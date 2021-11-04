package edu.ntut.project_01.homegym.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "courseComment")
public class CourseComment {
    @Id
    @Column(name = "course_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseCommentId;
    private Integer star;
    @Column(name = "comment_content")
    private String commentContent;
    @CreatedDate
    @Column(name = "comment_create_time")
    private Date commentCreateTime;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseComment() {
    }

    public CourseComment(Integer courseCommentId, Integer star, String commentContent, Date commentCreateTime, Member member, Course course) {
        this.courseCommentId = courseCommentId;
        this.star = star;
        this.commentContent = commentContent;
        this.commentCreateTime = commentCreateTime;
        this.member = member;
        this.course = course;
    }

    public Integer getCourseCommentId() {
        return courseCommentId;
    }

    public void setCourseCommentId(Integer courseCommentId) {
        this.courseCommentId = courseCommentId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(Date commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
