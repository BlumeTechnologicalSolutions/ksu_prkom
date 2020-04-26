package com.lk.entity;

import java.util.Date;

public class UserDocument {

    private Integer UserId;
    private String Citizenship;
    private String DocumentType;
    private String Series;
    private String DocumentNumber;
    private String DocumentCode;
    private Date   ReceiveDate;
    private String ReceiveBy;
    private String PlaceOfBirth;

    private String OldPlaceOfBirth;
    private String OldCitizenship;
    private String OldDocumentType;
    private String OldSeries;
    private String OldDocumentNumber;
    private String OldDocumentCode;
    private Date OldReceiveDate;
    private String OldReceiveBy;


    public UserDocument() {
    }
    public Integer getUserId() { return UserId; }
    public void setUserId(Integer userId) { UserId = userId; }
    public String getCitizenship() { return Citizenship; }
    public void setCitizenship(String citizenship) { Citizenship = citizenship; }
    public String getDocumentType() { return DocumentType; }
    public void setDocumentType(String documentType) { DocumentType = documentType; }
    public String getSeries() { return Series; }
    public void setSeries(String series) { Series = series; }
    public String getDocumentNumber() { return DocumentNumber; }
    public void setDocumentNumber(String documentNumber) { DocumentNumber = documentNumber; }
    public String getDocumentCode() { return DocumentCode; }
    public void setDocumentCode(String documentCode) { DocumentCode = documentCode; }
    public Date getReceiveDate() { return ReceiveDate; }
    public void setReceiveDate(Date receiveDate) { ReceiveDate = receiveDate; }
    public String getReceiveBy() { return ReceiveBy; }
    public void setReceiveBy(String receiveBy) { ReceiveBy = receiveBy; }
    public String getOldPlaceOfBirth() { return OldPlaceOfBirth; }
    public void setOldPlaceOfBirth(String oldPlaceOfBirth) { OldPlaceOfBirth = oldPlaceOfBirth; }
    public String getOldCitizenship() { return OldCitizenship; }
    public void setOldCitizenship(String oldCitizenship) { OldCitizenship = oldCitizenship; }
    public String getOldDocumentType() { return OldDocumentType; }
    public void setOldDocumentType(String oldDocumentType) { OldDocumentType = oldDocumentType; }
    public String getOldSeries() { return OldSeries; }
    public void setOldSeries(String oldSeries) { OldSeries = oldSeries; }
    public String getOldDocumentNumber() { return OldDocumentNumber; }
    public void setOldDocumentNumber(String oldDocumentNumber) { OldDocumentNumber = oldDocumentNumber; }
    public String getOldDocumentCode() { return OldDocumentCode; }
    public void setOldDocumentCode(String oldDocumentCode) { OldDocumentCode = oldDocumentCode; }
    public Date getOldReceiveDate() { return OldReceiveDate; }
    public void setOldReceiveDate(Date oldReceiveDate) { OldReceiveDate = oldReceiveDate; }
    public String getOldReceiveBy() { return OldReceiveBy; }
    public void setOldReceiveBy(String oldReceiveBy) { OldReceiveBy = oldReceiveBy; }
    public String getPlaceOfBirth() { return PlaceOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { PlaceOfBirth = placeOfBirth; }
}