package edu.ntut.project_01.homegym.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coachId;
    private String experience;
    private String certification;
    private String skill;
    private String coachImage;
    private String coachInfo;
    //需要隱藏欄位
    @Column(columnDefinition = "TINYINT(1)")
    private Integer suspension;
    private String account;
    private String checked;
    private Date checkTime;
    private String pass;
    @CreatedDate
    private Date applyTime;

    @OneToOne(mappedBy = "coach")
    private Member member;

    @OneToMany(mappedBy = "coach", cascade = { CascadeType.PERSIST })
    private Set<Video> courses = new HashSet<>();

    public Coach() {
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getCoachImage() {
        return coachImage;
    }

    public void setCoachImage(String coachImage) {
        this.coachImage = coachImage;
    }

    public String getCoachInfo() {
        return coachInfo;
    }

    public void setCoachInfo(String coachInfo) {
        this.coachInfo = coachInfo;
    }

    public Integer getSuspension() {
        return suspension;
    }

    public void setSuspension(Integer suspension) {
        this.suspension = suspension;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<Video> getCourses() {
        return courses;
    }

    public void setCourses(Set<Video> courses) {
        this.courses = courses;
    }
}
