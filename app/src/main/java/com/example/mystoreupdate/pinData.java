package com.example.mystoreupdate;

public class pinData {

    String pincode,status;

    public pinData(String pincode, String status) {
        this.pincode = pincode;
        this.status = status;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
