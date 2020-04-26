package com.lk.entity;

import java.sql.Timestamp;

public class Event {
    private Integer id, education_id, form_education_id;
    private String event;
    private Timestamp date;

    public Event(){ }
    public Event(Integer id, Integer education_id, Integer form_education_id,
                 String event, Timestamp date){
        this.id = id;
        this.education_id = education_id;
        this.form_education_id = form_education_id;
        this.event = event;
        this.date = date;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getEducation_id() { return education_id; }
    public void setEducation_id(Integer education_id) { this.education_id = education_id; }

    public Integer getForm_education_id() { return form_education_id; }
    public void setForm_education_id(Integer form_education_id) { this.form_education_id = form_education_id; }

    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }

}
