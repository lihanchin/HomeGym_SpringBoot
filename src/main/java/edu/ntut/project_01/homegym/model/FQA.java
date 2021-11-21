package edu.ntut.project_01.homegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;


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

    @Column(name = "fqa_create_time")
    private String fqaCreateTime;

    @Transient
    private String memberName;

    @Transient
    @Lob
    private byte[] memberImage;

    @Transient
    private String mineType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

//    @JsonIgnore
    @OneToMany(mappedBy = "fqa", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<FQAReply> fqaReplies = new HashSet<>();

    public FQA() {
    }

    public FQA(Integer fqaId, String fqaContent, String fqaCreateTime, String memberName, byte[] memberImage, String mineType, Member member, Course course, Set<FQAReply> fqaReplies) {
        this.fqaId = fqaId;
        this.fqaContent = fqaContent;
        this.fqaCreateTime = fqaCreateTime;
        this.memberName = memberName;
        this.memberImage = memberImage;
        this.mineType = mineType;
        this.member = member;
        this.course = course;
        this.fqaReplies = fqaReplies;
    }

    public String getMineType() {
        return mineType;
    }

    public void setMineType(String mineType) {
        this.mineType = mineType;
    }

    public byte[] getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(byte[] memberImage) {
        this.memberImage = memberImage;
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

    public String getFqaCreateTime() {
        return fqaCreateTime;
    }

    public void setFqaCreateTime(String fqaCreateTime) {
        this.fqaCreateTime = fqaCreateTime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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
