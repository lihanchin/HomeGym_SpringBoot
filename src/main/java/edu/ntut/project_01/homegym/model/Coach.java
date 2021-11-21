package edu.ntut.project_01.homegym.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "coach_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coachId;
    private String experience;
    @Column(columnDefinition = "LONGTEXT")
    private String certification;
    private String skill;


    @Column(name = "coach_image",columnDefinition = "LONGTEXT")
    private String coachImage;


    @Column(name = "coach_info")
    private String coachInfo;
    //需要隱藏欄位
    @Column(columnDefinition = "TINYINT(1) default 0")
    private Integer suspension;
    private String account;
    @Column(columnDefinition = "varchar(1) default '0'")
    private String checked;
    @Column(name = "check_time")
    private Date checkTime;
    private String pass;

    @Column(name = "apply_time")
    private String applyTime;


    @JsonIgnore
    @OneToOne(mappedBy = "coach")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "coach", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Course> courses = new HashSet<>();

    public Coach() {
    }

    public Coach(Integer coachId, String experience, String certification, String skill, String coachImage, String coachInfo, Integer suspension, String account, String checked, Date checkTime, String pass, String applyTime, Member member, Set<Course> courses) {
        this.coachId = coachId;
        this.experience = experience;
        this.certification = certification;
        this.skill = skill;
        this.coachImage = coachImage;
        this.coachInfo = coachInfo;
        this.suspension = suspension;
        this.account = account;
        this.checked = checked;
        this.checkTime = checkTime;
        this.pass = pass;
        this.applyTime = applyTime;
        this.member = member;
        this.courses = courses;
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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
