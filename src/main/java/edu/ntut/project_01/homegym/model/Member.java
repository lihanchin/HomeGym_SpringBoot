package edu.ntut.project_01.homegym.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;
    @Column(name = "member_name")
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
    @Column(name = "member_image")
    private byte[] memberImage;
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;
    private String mimeType;
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    Set<Orders> orders = new HashSet<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<CourseComment> courseComments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<FQA> fqas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<FQAReply> fqaReplies = new HashSet<>();

    public Member() {
    }

    public Member(Integer memberId, String name, String email, String password, String phone, java.sql.Date birthday, Integer status, String code, String role, byte[] memberImage, Date createTime, String mimeType, Set<Orders> orders, Coach coach, Set<CourseComment> courseComments, Set<FQA> fqas, Set<FQAReply> fqaReplies) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.status = status;
        this.code = code;
        this.role = role;
        this.memberImage = memberImage;
        this.createTime = createTime;
        this.mimeType = mimeType;
        this.orders = orders;
        this.coach = coach;
        this.courseComments = courseComments;
        this.fqas = fqas;
        this.fqaReplies = fqaReplies;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Set<CourseComment> getCourseComments() {
        return courseComments;
    }

    public void setCourseComments(Set<CourseComment> courseComments) {
        this.courseComments = courseComments;
    }

    public Set<FQA> getFqas() {
        return fqas;
    }

    public void setFqas(Set<FQA> fqas) {
        this.fqas = fqas;
    }

    public Set<FQAReply> getFqaReplies() {
        return fqaReplies;
    }

    public void setFqaReplies(Set<FQAReply> fqaReplies) {
        this.fqaReplies = fqaReplies;
    }
}
