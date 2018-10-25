package com.example.abhishek.myapplication;

/**
 * Created by abhishek on 21/10/18.
 */

public class UserModel {
    String customer,status;
    int id,otp;

    public UserModel()
    {

    }
    public UserModel(String name, String status, int id,int otp)
    {
        this.customer = name;
        this.status = status;
        this.id = id;
        this.otp = otp;
    }
    public String getCustomer()
    {
        return customer;
    }
    /*public void setCustomer(String name)
    {
        this.customer = name;
    }*/

    public String getStatus() {
        return status;
    }
    public int getotp(){return otp;}
  /*  public void setStatus(String status)
    {
        this.status = status;
    }*/

    public int getId() {
        return id;
    }

    /*public void id(int id) {
        this.id = id;
    }*/
}
