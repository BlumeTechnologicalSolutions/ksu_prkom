package com.lk.entity;

import java.sql.Timestamp;

public class User {

    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Patronymic;
    private String Email;
    private Timestamp CreationDate;
    private String Login;
    private String Password;
    private String ControlQuestion;
    private String ControlAnswer;
    private Boolean IsAdmin;
    private Boolean IsOperator;
    private Boolean IsDeleted;
    private String Token;

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
    public Timestamp getCreationDate() { return CreationDate; }
    public void setCreationDate(Timestamp creationDate) { CreationDate = creationDate; }
    public Boolean getIsAdmin() { return IsAdmin; }
    public void setIsAdmin(Boolean admin) { IsAdmin = admin; }
    public Boolean getIsOperator() { return IsOperator; }
    public void setIsOperator(Boolean operator) { IsOperator = operator; }
    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }
    public Boolean getIsDeleted() { return IsDeleted; }
    public void setIsDeleted(Boolean deleted) { IsDeleted = deleted; }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
