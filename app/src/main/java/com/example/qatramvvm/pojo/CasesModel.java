package com.example.qatramvvm.pojo;

public class CasesModel {
    private String case_id, case_name, hospital, blood_type, phone, description, age, gender, date, num;

    public CasesModel(String case_id, String case_name, String hospital, String blood_type, String phone, String description, String age, String gender, String date, String num) {
        this.case_id = case_id;
        this.case_name = case_name;
        this.hospital = hospital;
        this.blood_type = blood_type;
        this.phone = phone;
        this.description = description;
        this.age = age;
        this.gender = gender;
        this.date = date;
        this.num = num;
    }

    public String getCase_id() {
        return case_id;
    }

    public String getCase_name() {
        return case_name;
    }

    public String getHospital() {
        return hospital;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDate() {
        return date;
    }

    public String getNum() {
        return num;
    }
}
