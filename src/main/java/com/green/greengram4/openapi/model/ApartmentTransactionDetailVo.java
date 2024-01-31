package com.green.greengram4.openapi.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ApartmentTransactionDetailVo {
    private int dealAmount;
    private String buildYear;
    private String dealYear;
    private String dealMonth;
    private String dealDay;
    private String dong;
    private String apartmentName;
    private float areaForExclusiveUse;
    private String jibun;
    private int floor;

    @JsonGetter("dealAmount")
    public int getDealAmount() {
        return dealAmount;
    }

    @JsonSetter("거래금액")
    public void setDealAmount(String dealAmount) {
        this.dealAmount = Integer.parseInt(dealAmount.replaceAll(",", "").trim());
    }

    @JsonGetter("buildYear")
    public String getBuildYear() {
        return buildYear;
    }

    @JsonSetter("건축년도")
    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    @JsonGetter("dealYear")
    public String getDealYear() {
        return dealYear;
    }
    @JsonSetter("년")
    public void setDealYear(String dealYear) {
        this.dealYear = dealYear;
    }
    @JsonGetter("dealMonth")
    public String getDealMonth() {
        return dealMonth;
    }
    @JsonSetter("월")
    public void setDealMonth(String dealMonth) {
        this.dealMonth = dealMonth;
    }
    @JsonGetter("dealDay")
    public String getDealDay() {
        return dealDay;
    }
    @JsonSetter("일")
    public void setDealDay(String dealDay) {
        this.dealDay = dealDay;
    }
    @JsonGetter("dong")
    public String getDong() {
        return dong;
    }
    @JsonSetter("법정동")
    public void setDong(String dong) {
        this.dong = dong;
    }
    @JsonGetter("apartmentName")
    public String getApartmentName() {
        return apartmentName;
    }
    @JsonSetter("아파트")
    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
    @JsonGetter("areaForExclusiveUse")
    public float getAreaForExclusiveUse() {
        return areaForExclusiveUse;
    }
    @JsonSetter("전용면적")
    public void setAreaForExclusiveUse(float areaForExclusiveUse) {
        this.areaForExclusiveUse = areaForExclusiveUse;
    }
    @JsonGetter("jibun")
    public String getJibun() {
        return jibun;
    }
    @JsonSetter("지번")
    public void setJibun(String jibun) {
        this.jibun = jibun;
    }
    @JsonGetter("floor")
    public int getFloor() {
        return floor;
    }
    @JsonSetter("층")
    public void setFloor(int floor) {
        this.floor = floor;
    }
}
