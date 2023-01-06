package ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

import java.io.Serializable;

@Entity(tableName = "item_table")
@Xml(name = "item")
public class Item implements Serializable {
    //병원명
    @PropertyElement(name="yadmNm")
    private String yadmNm;
    //종별코드명
    @PropertyElement(name="clCdNm")
    private String clCdNm;
    //거리
    @PropertyElement(name="distance")
    private Double distance;
    //전화번호
    @NonNull
    @PrimaryKey
    @PropertyElement(name="telno")
    private String telno;
    //홈페이지
    @PropertyElement(name="hospUrl")
    private String hospUrl;
    //경도
    @PropertyElement(name="XPos")
    public Double xPos;
    //위도
    @PropertyElement(name="YPos")
    public Double yPos;
    //주소
    @PropertyElement(name="addr")
    private String addr;

    public String memo;

    public Item() {
        this.yadmNm = "";
        this.clCdNm = "";
        this.distance = 0.0;
        this.telno = "";
        this.hospUrl = "해당 병원은 사이트가 없습니다.";
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.addr = "";
        this.memo = "";
    }

    public Item(String yadmNm, String telno, String addr) {
        this.yadmNm = yadmNm;
        this.telno = telno;
        this.addr = addr;
    }

    public Item(String yadmNm, String clCdNm, Double distance, String telno, String hospUrl, Double xPos, Double yPos, String addr) {
        this.yadmNm = yadmNm;
        this.clCdNm = clCdNm;
        this.distance = distance;
        this.telno = telno;
        this.hospUrl = hospUrl;
        this.xPos = xPos;
        this.yPos = yPos;
        this.addr = addr;
    }

    public Item(String yadmNm, String clCdNm, Double distance, @NonNull String telno, String hospUrl, Double xPos, Double yPos, String addr, String memo) {
        this.yadmNm = yadmNm;
        this.clCdNm = clCdNm;
        this.distance = distance;
        this.telno = telno;
        this.hospUrl = hospUrl;
        this.xPos = xPos;
        this.yPos = yPos;
        this.addr = addr;
        this.memo = memo;
    }

    public String getYadmNm() {
        return yadmNm;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public String getClCdNm() {
        return clCdNm;
    }

    public void setClCdNm(String clCdNm) {
        this.clCdNm = clCdNm;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getHospUrl() {
        return hospUrl;
    }

    public void setHospUrl(String hospUrl) {
        this.hospUrl = hospUrl;
    }

    public Double getxPos() {
        return xPos;
    }

    public void setxPos(Double xPos) {
        this.xPos = xPos;
    }

    public Double getyPos() {
        return yPos;
    }

    public void setyPos(Double yPos) {
        this.yPos = yPos;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "yadmNm='" + yadmNm + '\'' +
                ", clCdNm='" + clCdNm + '\'' +
                ", distance=" + distance +
                ", telno='" + telno + '\'' +
                ", hospUrl='" + hospUrl + '\'' +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                ", addr='" + addr + '\'' +
                '}';
    }
}
