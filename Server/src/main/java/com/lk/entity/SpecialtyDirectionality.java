package com.lk.entity;

public class SpecialtyDirectionality {

    private Integer Id;
    private String Directionality;
    private Integer BudgesPlace;
    private Integer CommercialPlaces;
    private Integer DirectionId;
    private Boolean IsDeleted;

    public Integer getId() { return Id; }
    public void setId(Integer id) { Id = id; }
    public String getDirectionality() { return Directionality; }
    public void setDirectionality(String directionality) { Directionality = directionality; }
    public Integer getBudgesPlace() { return BudgesPlace; }
    public void setBudgesPlace(Integer budgesPlace) { BudgesPlace = budgesPlace; }
    public Integer getCommercialPlaces() { return CommercialPlaces; }
    public void setCommercialPlaces(Integer commercialPlaces) { CommercialPlaces = commercialPlaces; }
    public Integer getDirectionId() { return DirectionId; }
    public void setDirectionId(Integer directionId) { DirectionId = directionId; }
    public Boolean getIsDeleted() { return IsDeleted; }
    public void setIsDeleted(Boolean deleted) { IsDeleted = deleted; }
}

