package com.example.ihab.labproject.models;

public class Profile {
    boolean isAdmin;
    String email;
    String gender;
    String firstName;
    String lastName;
    String password;
    String country;
    String city;
    String phone;

    public Profile(boolean isAdmin, String email,String gender, String firstName, String lastName, String password, String country, String city, String phone) {
        this.isAdmin = isAdmin;
        this.email = email;
        this.gender=gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.country = country;
        this.city = city;
        this.phone = phone;
    }

    public Profile(){ }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
