package com.lk.entity;

import java.sql.Timestamp;

public class OtherUniversityReceipt {

    private Integer Id;
    private Integer UserId;
    private String UserFullName;
    private String UniversityName;
    private String InformationAboutReceipt;
    private String Url;
    private Timestamp CreationDate;
    private Boolean IsDeleted;
    private Integer Year;

    public Integer getId() { return Id; }
    public void setId(Integer id) { Id = id; }
    public Integer getUserId() { return UserId; }
    public void setUserId(Integer userId) { UserId = userId; }
    public String getUniversityName() { return UniversityName; }
    public void setUniversityName(String universityName) { UniversityName = universityName; }
    public String getInformationAboutReceipt() { return InformationAboutReceipt; }
    public void setInformationAboutReceipt(String informationAboutReceipt) { InformationAboutReceipt = informationAboutReceipt; }
    public String getUrl() { return Url; }
    public void setUrl(String url) { Url = url; }
    public Timestamp getCreationDate() { return CreationDate; }
    public void setCreationDate(Timestamp creationDate) { CreationDate = creationDate; }
    public Boolean getIsDeleted() { return IsDeleted; }
    public void setIsDeleted(Boolean isdeleted) { IsDeleted = isdeleted; }
    public String getUserFullName() { return UserFullName; }
    public void setUserFullName(String userFullName) { UserFullName = userFullName; }
    public Integer getYear() { return Year; }
    public void setYear(Integer year) { Year = year; }
}
