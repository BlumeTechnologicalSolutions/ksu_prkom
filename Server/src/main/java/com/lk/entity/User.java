package com.lk.entity;

import java.sql.Date;

public class User {

    private Integer Id;
    private String FirstName;
    private String SecondName;
    private String LastName;
    private String Patronymic;
    private String Gender;
    private String Email;
    private String Login;
    private String Password;
    private String PlaceOfBirth;
    private Date BirthdayDate;
    private Date AccountCreationDate;
    private Boolean IsOperator;
    private Boolean IsAdmin;
    private Boolean IsDeleted;

    public Integer getId() { return Id; }
    public void setId(Integer id) { Id = id; }
    public String getFirstName() { return FirstName; }
    public void setFirstName(String firstName) { FirstName = firstName; }
    public String getLastName() { return LastName; }
    public void setLastName(String lastName) { LastName = lastName; }
    public String getPatronymic() { return Patronymic; }
    public void setPatronymic(String patronymic) { Patronymic = patronymic; }
    public String getGender() { return Gender; }
    public void setGender(String gender) { Gender = gender; }
    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }
    public String getLogin() { return Login; }
    public void setLogin(String login) { Login = login; }
    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }

    public Date getAccountCreationDate() { return AccountCreationDate; }
    public void setAccountCreationDate(Date accountCreationDate) { AccountCreationDate = accountCreationDate; }
    public Boolean getIsOperator() { return IsOperator; }
    public void setIsOperator(Boolean operator) { IsOperator = operator; }
    public Boolean getIsAdmin() { return IsAdmin; }
    public void setIsAdmin(Boolean admin) { IsAdmin = admin; }
    public Boolean getIsDeleted() { return IsDeleted; }
    public void setIsDeleted(Boolean deleted) { IsDeleted = deleted; }
    public Date getBirthdayDate() { return BirthdayDate; }
    public void setBirthdayDate(Date birthdayDate) { BirthdayDate = birthdayDate; }
    public String getSecondName() { return SecondName; }
    public void setSecondName(String secondName) { SecondName = secondName;
    }

    public String getPlaceOfBirth() {
        return PlaceOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        PlaceOfBirth = placeOfBirth;
    }
}
