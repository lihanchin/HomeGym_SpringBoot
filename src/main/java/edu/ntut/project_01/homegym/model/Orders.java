package edu.ntut.project_01.homegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "total_amt")
    private Integer totalPrice;
    @Column(name = "order_status")
    private String orderStatus;
    @CreatedDate
    @Column(name = "order_time")
    private Date orderTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public Orders() {
    }

    public Orders(String orderId, Integer totalPrice, Member member, Set<Course> courses) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.member = member;
        this.courses = courses;
    }

    public Orders(String orderId, Integer totalPrice, String orderStatus, Date orderTime, Member member, Set<Course> courses) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.member = member;
        this.courses = courses;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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
