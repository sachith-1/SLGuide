package com.androidproject.slguide.launching;

public class TouristProfile {

    public String fullName;
    public String pno;
    public String id;
    public String email;
    public String address;
    public String type;
    public String medicalStatus;

    public TouristProfile(){

    }
    public TouristProfile(String fullName, String pno, String id, String email, String address, String medicalStatus) {
        this.fullName = fullName;
        this.pno = pno;
        this.id = id;
        this.email = email;
        this.address = address;
        this.medicalStatus = medicalStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public String getMedicalStatus() {
        return medicalStatus;
    }

    public void setMedicalStatus(String medicalStatus) {
        this.medicalStatus = medicalStatus;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
