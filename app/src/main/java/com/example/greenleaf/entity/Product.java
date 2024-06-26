package com.example.greenleaf.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "image")
    private final String image;
    @ColumnInfo(name = "name")
    private final String name;
    @ColumnInfo(name = "mass")
    private final String mass;
    @ColumnInfo(name = "price")
    private final int price;

    @ColumnInfo(name = "quantity")
    private int quantity = 1;


    @ColumnInfo(name = "priceWithoutDiscount")
    private final int priceWithoutDiscount;


    public Product(int id, String image, String name, String mass, int price, int priceWithoutDiscount) {
        this.image = image;
        this.name = name;
        this.mass = mass;
        this.price = price;
        this.priceWithoutDiscount = priceWithoutDiscount;
        this.id = id;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getMass() {
        return mass;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice(){
        return price*quantity;
    }

    public int getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public int getId() {
        return id;
    }
}
