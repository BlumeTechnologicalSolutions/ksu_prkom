package com.lk.entity;

public class UserRegistration {

    private String FirstName;
    private String LastName;
    private String Patronymic;
    private String Phone;
    private String Password;
    private String ControlQuestion;
    private String ControlAnswer;

    public String getFirstName() { return FirstName; }
    public void setFirstName(String firstName) { FirstName = firstName; }
    public String getLastName() { return LastName; }
    public void setLastName(String lastName) { LastName = lastName; }
    public String getPatronymic() { return Patronymic; }
    public void setPatronymic(String patronymic) { Patronymic = patronymic; }
    public String getPhone() { return Phone; }
    public void setPhone(String phone) { Phone = phone; }
    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }
    public String getControlQuestion() { return ControlQuestion; }
    public void setControlQuestion(String controlQuestion) { ControlQuestion = controlQuestion; }
    public String getControlAnswer() { return ControlAnswer; }
    public void setControlAnswer(String controlAnswer) { ControlAnswer = controlAnswer; }

}
