package edu.ntut.project_01.homegym.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "fqa")
public class FQA {

    @Id
    @Column(name = "fqa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fqaId;
    @Column(name = "fqa_content")
    private String fqaContent;
    @CreatedDate
    @Column(name = "fqa_create_time")
    private Date fqaCreateTime;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "fqa", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<FQAReply> fqaReplies = new HashSet<>();

    public FQA() {
    }

    public FQA(Integer fqaId, String fqaContent, Date fqaCreateTime, Member member, Course course, Set<FQAReply> fqaReplies) {
        this.fqaId = fqaId;
        this.fqaContent = fqaContent;
        this.fqaCreateTime = fqaCreateTime;
        this.member = member;
        this.course = course;
        this.fqaReplies = fqaReplies;
    }

    public Integer getFqaId() {
        return fqaId;
    }

    public void setFqaId(Integer fqaId) {
        this.fqaId = fqaId;
    }

    public String getFqaContent() {
        return fqaContent;
    }

    public void setFqaContent(String fqaContent) {
        this.fqaContent = fqaContent;
    }

    public Date getFqaCreateTime() {
        return fqaCreateTime;
    }

    public void setFqaCreateTime(Date fqaCreateTime) {
        this.fqaCreateTime = fqaCreateTime;
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

    public Set<FQAReply> getFqaReplies() {
        return fqaReplies;
    }

    public void setFqaReplies(Set<FQAReply> fqaReplies) {
        this.fqaReplies = fqaReplies;
    }
}
