package com.lk.entity;

public class Direction {

    private Integer Id;
    private String Code;
    private String Direction;
    private Integer InstituteId;
    private Integer EducationId;
    private Integer IsDeleted;
    public Integer getId() { return Id; }
    public void setId(Integer id) { Id = id; }
    public String getCode() { return Code; }
    public void setCode(String code) { Code = code; }
    public String getDirection() { return Direction; }
    public void setDirection(String direction) { Direction = direction; }
    public Integer getInstituteId() { return InstituteId; }
    public void setInstituteId(Integer institute_id) { InstituteId = institute_id; }
    public Integer getEducationId() { return EducationId; }
    public void setEducationId(Integer education_id) { EducationId = education_id; }
    public Integer getIsDeleted() { return IsDeleted; }
    public void setIsDeleted(Integer is_deleted) { IsDeleted = is_deleted; }
}
