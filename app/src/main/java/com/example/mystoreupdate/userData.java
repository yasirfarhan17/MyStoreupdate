package com.example.mystoreupdate;

public class userData {

    String name,phone,online;

    public userData(String name, String phone, String online) {
        this.name = name;
        this.phone = phone;
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
