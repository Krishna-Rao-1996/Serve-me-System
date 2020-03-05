package com.example.servemesystem.domain;

import java.util.List;

public class ServiceProvider {

    private String userName;
    private String City;
    private String Companyname;
    private String DateOfBirth;
    private String Email;
    private String FirstName;
    private String IsVerified;
    private String Officeaddress;
    private String Officenumber;
    private String Password;
    private String Phone;
    private String ServiceTypes;
    private String State;
    private String WorkingDays;
    private String WorkingHours;
    private String zipCode;
    private String dp;

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    private String dp;
    public ServiceProvider() {
    }

    public ServiceProvider(String userName, String city, String companyname, String dateOfBirth,
                           String email, String firstName, String isVerified, String officeaddress,
                           String officenumber, String password, String phone, String serviceTypes,
                           String state, String workingDays, String workingHours, String zipCode) {
        this.userName = userName;
        City = city;
        Companyname = companyname;
        DateOfBirth = dateOfBirth;
        Email = email;
        FirstName = firstName;
        IsVerified = isVerified;
        Officeaddress = officeaddress;
        Officenumber = officenumber;
        Password = password;
        Phone = phone;
        ServiceTypes = serviceTypes;
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

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String fname) {
        FirstName = fname;
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

    public String getServiceTypes() {
        return ServiceTypes;
    }

    public void setServiceTypes(String serviceTypes) {
        ServiceTypes = serviceTypes;
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

    public String getDp() { return dp;}

    public void setDp(String dp) {this.dp = dp; }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "userName='" + userName + '\'' +
                ", City='" + City + '\'' +
                ", Companyname='" + Companyname + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", Email='" + Email + '\'' +
                ", Fname='" + FirstName + '\'' +
                ", IsVerified=" + IsVerified +
                ", Officeaddress='" + Officeaddress + '\'' +
                ", Officenumber='" + Officenumber + '\'' +
                ", Password='" + Password + '\'' +
                ", Phone='" + Phone + '\'' +
                ", ServieTypes='" + ServiceTypes + '\'' +
                ", State='" + State + '\'' +
                ", WorkingDays='" + WorkingDays + '\'' +
                ", WorkingHours='" + WorkingHours + '\'' +
                '}';
    }
}
