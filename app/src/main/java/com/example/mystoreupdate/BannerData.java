package com.example.mystoreupdate;

public class BannerData {
    String img,color;
    public BannerData(){

    }

    public BannerData(String img, String color) {
        this.img = img;
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
