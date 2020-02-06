package com.lk.entity;

import java.sql.Timestamp;

public class UniversityReceiptFilter {

    private String FullName;
    private Integer Year;


    public Integer getYear() { return Year; }
    public void setYear(Integer year) { Year = year; }
    public String getFullName() { return FullName; }
    public void setFullName(String fullName) { FullName = fullName; }
}
