package edu.ntut.project_01.homegym.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date   ;
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
    private Date birthday;
    @CreatedDate
    private Date createDate;

    //可能要加外鍵 coach身份 此處為一方
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Video> courses = new HashSet<>();

    //教練身份判斷欄位（目前想法是註冊時input hidden 放入０/null）
    @OneToOne(cascade =CascadeType.PERSIST)
    @JoinColumn
    private Coach coach;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

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
}