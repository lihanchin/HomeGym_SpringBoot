package edu.ntut.project_01.homegym.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;
    private String name;
    private String email;
    private String password;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.sql.Date birthday;
    @Column(columnDefinition = "TINYINT(1)")
    //前端input hidden name:status    value:0
    private Integer status;
    private String code;
    //前端input hidden name:role  value:會員
    private String role;
    @Lob
    private byte[] memberImage;
    @CreatedDate
    private Date createDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "member_courses",
            joinColumns = {
                    @JoinColumn(name = "MEMBER_ID", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "COURSE_ID", nullable = false)}
    )
    private Set<Video> video = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private Coach coach;

    public Member() {
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(byte[] memberImage) {
        this.memberImage = memberImage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Video> getVideo() {
        return video;
    }

    public void setVideo(Set<Video> video) {
        this.video = video;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}