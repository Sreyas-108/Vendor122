package com.example.abhishek.myapplication;

/**
 * Created by abhishek on 23/10/18.
 */

public class User1Model {
    String name;
    int id;
   public boolean is_available;

    public User1Model()
    {

    }
    public User1Model(String name, String status, int id,boolean is_available)
    {
        this.name = name;
        this.id = id;
        this.is_available=is_available;
        }
    public String getName()
    {
        return name;
    }

    public int getId() {
        return id;
    }


}
