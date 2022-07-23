package com.example.mystoreupdate;

public class productData {
    String catName,img,price,products_name,quant,HindiName,stock;

    public productData( String img, String price, String products_name,String quant,String hindi,String stock) {
        this.img = img;
        this.price = price;
        this.products_name = products_name;
        this.quant=quant;
        this.HindiName=hindi;
        this.stock=stock;

    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getHindiName() {
        return HindiName;
    }

    public void setHindiName(String hindiName) {
        HindiName = hindiName;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String product_name) {
        this.products_name = product_name;
    }
}
