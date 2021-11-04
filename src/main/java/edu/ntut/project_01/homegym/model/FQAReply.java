package edu.ntut.project_01.homegym.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "fqaReply")
public class FQAReply {
    @Id
    @Column(name = "fqaReply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fqaReplyId;
    @Column(name = "fqa_reply_content")
    private String fqaReplyContent;
    @Column(name = "fqa_reply_create_time")
    private Date fqaCreateTime;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "fqa_id")
    private FQA fqa;

    public FQAReply() {
    }

    public FQAReply(Integer fqaReplyId, String fqaReplyContent, Date fqaCreateTime, Member member, FQA fqa) {
        this.fqaReplyId = fqaReplyId;
        this.fqaReplyContent = fqaReplyContent;
        this.fqaCreateTime = fqaCreateTime;
        this.member = member;
        this.fqa = fqa;
    }

    public Integer getFqaReplyId() {
        return fqaReplyId;
    }

    public void setFqaReplyId(Integer fqaReplyId) {
        this.fqaReplyId = fqaReplyId;
    }

    public String getFqaReplyContent() {
        return fqaReplyContent;
    }

    public void setFqaReplyContent(String fqaReplyContent) {
        this.fqaReplyContent = fqaReplyContent;
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

    public FQA getFqa() {
        return fqa;
    }

    public void setFqa(FQA fqa) {
        this.fqa = fqa;
    }
}
