package com.example.servemesystem.domain;

import java.util.List;

public class ServiceProvider {

    private String userName;
    private String City;
    private String Companyname;
    private String DateOfBirth;
    private String Email;
    private String Fname;
    private String IsVerified;
    private String Officeaddress;
    private String Officenumber;
    private String Password;
    private String Phone;
    private String ServieTypes;
    private String State;
    private String WorkingDays;
    private String WorkingHours;
    private String zipCode;

    public ServiceProvider() {
    }

    public ServiceProvider(String userName, String city, String companyname, String dateOfBirth,
                           String email, String fname, String isVerified, String officeaddress,
                           String officenumber, String password, String phone, String servieTypes,
                           String state, String workingDays, String workingHours, String zipCode) {
        this.userName = userName;
        City = city;
        Companyname = companyname;
        DateOfBirth = dateOfBirth;
        Email = email;
        Fname = fname;
        IsVerified = isVerified;
        Officeaddress = officeaddress;
        Officenumber = officenumber;
        Password = password;
        Phone = phone;
        ServieTypes = servieTypes;
        State = state;
        WorkingDays = workingDays;
        WorkingHours = workingHours;
        this.zipCode = zipCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCompanyname() {
        return Companyname;
    }

    public void setCompanyname(String companyname) {
        Companyname = companyname;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getIsVerified() {
        return IsVerified;
    }

    public void setVerified(String verified) {
        IsVerified = verified;
    }

    public String getOfficeaddress() {
        return Officeaddress;
    }

    public void setOfficeaddress(String officeaddress) {
        Officeaddress = officeaddress;
    }

    public String getOfficenumber() {
        return Officenumber;
    }

    public void setOfficenumber(String officenumber) {
        Officenumber = officenumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getServieTypes() {
        return ServieTypes;
    }

    public void setServieTypes(String servieTypes) {
        ServieTypes = servieTypes;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getWorkingDays() {
        return WorkingDays;
    }

    public void setWorkingDays(String workingDays) {
        WorkingDays = workingDays;
    }

    public String getWorkingHours() {
        return WorkingHours;
    }

    public void setWorkingHours(String workingHours) {
        WorkingHours = workingHours;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "userName='" + userName + '\'' +
                ", City='" + City + '\'' +
                ", Companyname='" + Companyname + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", Email='" + Email + '\'' +
                ", Fname='" + Fname + '\'' +
                ", IsVerified=" + IsVerified +
                ", Officeaddress='" + Officeaddress + '\'' +
                ", Officenumber='" + Officenumber + '\'' +
                ", Password='" + Password + '\'' +
                ", Phone='" + Phone + '\'' +
                ", ServieTypes='" + ServieTypes + '\'' +
                ", State='" + State + '\'' +
                ", WorkingDays='" + WorkingDays + '\'' +
                ", WorkingHours='" + WorkingHours + '\'' +
                '}';
    }
}
