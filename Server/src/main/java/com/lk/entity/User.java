package com.lk.entity;

import java.sql.Date;

public class User {

    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Patronymic;

    private String Password;
    private String Login;
    private String ControlQuestion;
    private String ControlAnswer;

    public Integer getId() { return Id; }
    public void setId(Integer id) { Id = id; }
    public String getFirstName() { return FirstName; }
    public void setFirstName(String firstName) { FirstName = firstName; }
    public String getLastName() { return LastName; }
    public void setLastName(String lastName) { LastName = lastName; }
    public String getPatronymic() { return Patronymic; }
    public void setPatronymic(String patronymic) { Patronymic = patronymic; }
    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }
    public String getLogin() { return Login; }
    public void setLogin(String login) { Login = login; }
    public String getControlQuestion() { return ControlQuestion; }
    public void setControlQuestion(String controlQuestion) { ControlQuestion = controlQuestion; }
    public String getControlAnswer() { return ControlAnswer; }
    public void setControlAnswer(String controlAnswer) { ControlAnswer = controlAnswer; }
}
