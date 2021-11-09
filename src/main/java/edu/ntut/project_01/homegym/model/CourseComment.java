package edu.ntut.project_01.homegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Blob;
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

    @Transient
    private String memberName;

    @Transient
    @Lob
    private byte[] memberImge;

    @CreatedDate
    @Column(name = "comment_create_time")
    private Date commentCreateTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseComment() {
    }

    public CourseComment(Integer courseCommentId, Integer star, String commentContent, String memberName, byte[] memberImge, Date commentCreateTime, Member member, Course course) {
        this.courseCommentId = courseCommentId;
        this.star = star;
        this.commentContent = commentContent;
        this.memberName = memberName;
        this.memberImge = memberImge;
        this.commentCreateTime = commentCreateTime;
        this.member = member;
        this.course = course;
    }

    public byte[] getMemberImge() {
        return memberImge;
    }

    public void setMemberImge(byte[] memberImge) {
        this.memberImge = memberImge;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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