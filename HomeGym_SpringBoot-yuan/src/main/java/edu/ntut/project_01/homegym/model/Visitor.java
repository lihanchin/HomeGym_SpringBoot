package edu.ntut.project_01.homegym.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
//@EntityListeners(AuditingEntityListener.class)
@Table(name = "visitor")
public class Visitor {
    @Id
    @Column(name = "visitor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitorId;
    @Column(name = "visitor_email")
    private String visitorEmail;
    @Column(name = "visitor_name")
    private String visitorName;
    @Column(name = "visitor_message")
    private String visitorMessage;
    @Column(name = "visitor_time")
    private String visitorTime;

    public Visitor() {
    }

    public Visitor(Integer visitorId, String visitorEmail, String visitorName, String visitorMessage, String visitorTime) {
        this.visitorId = visitorId;
        this.visitorEmail = visitorEmail;
        this.visitorName = visitorName;
        this.visitorMessage = visitorMessage;
        this.visitorTime = visitorTime;
    }

    public String getVisitorTime() {
        return visitorTime;
    }

    public void setVisitorTime(String visitorTime) {
        this.visitorTime = visitorTime;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorEmail() {
        return visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorMessage() {
        return visitorMessage;
    }

    public void setVisitorMessage(String visitorMessage) {
        this.visitorMessage = visitorMessage;
    }
}
