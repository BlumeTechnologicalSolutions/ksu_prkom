package com.lk.entity;

public class UserRegistration {

    private String FirstName;
    private String LastName;
    private String Patronymic;
    private String Login;
    private String Password;
    private String Email;
    private String ControlQuestion;
    private String ControlAnswer;

    public String getFirstName() { return FirstName; }
    public void setFirstName(String firstName) { FirstName = firstName; }
    public String getLastName() { return LastName; }
    public void setLastName(String lastName) { LastName = lastName; }
    public String getPatronymic() { return Patronymic; }
    public void setPatronymic(String patronymic) { Patronymic = patronymic; }
    public String getLogin() { return Login; }
    public void setLogin(String login) { Login = login; }
    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }
    public String getControlQuestion() { return ControlQuestion; }
    public void setControlQuestion(String controlQuestion) { ControlQuestion = controlQuestion; }
    public String getControlAnswer() { return ControlAnswer; }
    public void setControlAnswer(String controlAnswer) { ControlAnswer = controlAnswer; }
    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }
}
