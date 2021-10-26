package edu.ntut.project_01.homegym.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phone;
    @JsonFormat(pattern="yyyy-MM-dd")
    private java.sql.Date birthday;
    @Column(columnDefinition = "TINYINT(1)")
    private Integer status;
    private String code;
    private String role;
    private String memberImg;
    @CreatedDate
    private Date createDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Video> courses = new HashSet<>();

    //教練身份判斷欄位（目前想法是註冊時input hidden 放入０/null）
//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "fk_coachId")
//    private Coach coach;

    public Member() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

//    public Coach getCoach() {
//        return coach;
//    }
//
//    public void setCoach(Coach coach) {
//        this.coach = coach;
//    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Video> getCourses() {
        return courses;
    }

    public void setCourses(Set<Video> courses) {
        this.courses = courses;
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
}