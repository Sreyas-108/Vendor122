package com.example.abhishek.myapplication;

/**
 * Created by abhishek on 22/10/18.
 */

public class ItemModel {
    String name;
    String qty;

    public ItemModel() {
    }

    public ItemModel(String name, String qty) {
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public String getQty() {
        return qty;
    }




}
