package edu.ntut.project_01.homegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String fqaReplyCreateTime;


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
    @JoinColumn(name = "fqa_id")
    private FQA fqa;

    public FQAReply() {
    }

    public FQAReply(Integer fqaReplyId, String fqaReplyContent, String fqaReplyCreateTime, String memberName, byte[] memberImage, String mineType, Member member, FQA fqa) {
        this.fqaReplyId = fqaReplyId;
        this.fqaReplyContent = fqaReplyContent;
        this.fqaReplyCreateTime = fqaReplyCreateTime;
        this.memberName = memberName;
        this.memberImage = memberImage;
        this.mineType = mineType;
        this.member = member;
        this.fqa = fqa;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getFqaReplyCreateTime() {
        return fqaReplyCreateTime;
    }

    public void setFqaReplyCreateTime(String fqaReplyCreateTime) {
        this.fqaReplyCreateTime = fqaReplyCreateTime;
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
