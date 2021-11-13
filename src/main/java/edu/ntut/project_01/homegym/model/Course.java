package edu.ntut.project_01.homegym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    @Column(name = "course_path")
    private String coursePath;
    @Column(name = "mime_type")
    private String mimeType;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_info")
    private String courseInfo;

    @Column(name = "part_of_body")
    private String partOfBody;
    @Column(name = "course_image")
    private String courseImage;
    @CreatedDate
    @Column(name = "upload_time")
    private Date uploadTime;
    private Integer price;
    private String equipment;
    private String level;
    private Integer pass;
    private	Integer checked;
    @Column(name = "check_time")
    private Date checkTime;

    @Transient
    private String coachName;



    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)

    private Set<CourseComment> courseComments = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<FQA> fqas = new HashSet<>();

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "orderItem",
            joinColumns = {
                    @JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "order_id", nullable = false)}
    )
    private Set<Orders> orders = new HashSet<>();

    public Course() {
    }

    public Course(Integer courseId, String coursePath, String mimeType, String courseName, String courseInfo, String partOfBody, String courseImage, Date uploadTime, Integer price, String equipment, String level, Integer pass, Integer checked, Date checkTime, String coachName, Coach coach, Set<CourseComment> courseComments, Set<FQA> fqas, Set<Orders> orders) {
        this.courseId = courseId;
        this.coursePath = coursePath;
        this.mimeType = mimeType;
        this.courseName = courseName;
        this.courseInfo = courseInfo;
        this.partOfBody = partOfBody;
        this.courseImage = courseImage;
        this.uploadTime = uploadTime;
        this.price = price;
        this.equipment = equipment;
        this.level = level;
        this.pass = pass;
        this.checked = checked;
        this.checkTime = checkTime;
        this.coachName = coachName;
        this.coach = coach;
        this.courseComments = courseComments;
        this.fqas = fqas;
        this.orders = orders;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCoursePath() {
        return coursePath;
    }

    public void setCoursePath(String coursePath) {
        this.coursePath = coursePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getPartOfBody() {
        return partOfBody;
    }

    public void setPartOfBody(String partOfBody) {
        this.partOfBody = partOfBody;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }
}
