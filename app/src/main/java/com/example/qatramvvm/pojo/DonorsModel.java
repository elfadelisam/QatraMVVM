package com.example.qatramvvm.pojo;

public class DonorsModel {
    private String donor_id, donor_name, email, phone, blood_type, address, age, gender, last_donation_date;

    public DonorsModel(String donor_id, String donor_name, String email, String phone, String blood_type, String address, String age, String gender, String last_donation_date) {
        this.donor_id = donor_id;
        this.donor_name = donor_name;
        this.email = email;
        this.phone = phone;
        this.blood_type = blood_type;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.last_donation_date = last_donation_date;
    }

    public String getDonor_id() {
        return donor_id;
    }

    public String getDonor_name() {
        return donor_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public String getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLast_donation_date() {
        return last_donation_date;
    }
}
