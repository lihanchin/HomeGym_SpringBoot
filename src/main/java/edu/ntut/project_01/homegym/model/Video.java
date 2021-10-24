package edu.ntut.project_01.homegym.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoId;
    private String name;	//課程名
    @Lob
    private char[] videoInfo;	//課程資訊
    private String category; //課程類別
    private String partOfBody; //不確定會用數字還是字串來設定  //運動部位
    @Lob
    private byte[] videoImage;	//影片圖片
    @CreatedDate
    private Date time;	//上傳時間
    private Integer price; //Integer or Double 	//課程價格
    private String equipment; //器材
    private String level; //適合的層級
    private Integer pass; //課程是否審核成功
    private	Integer checked; //審核狀態（未審核/已審核）
    private Date checkTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_memberId")
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Video() {
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(char[] videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPartOfBody() {
        return partOfBody;
    }

    public void setPartOfBody(String partOfBody) {
        this.partOfBody = partOfBody;
    }

    public byte[] getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(byte[] videoImage) {
        this.videoImage = videoImage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}
