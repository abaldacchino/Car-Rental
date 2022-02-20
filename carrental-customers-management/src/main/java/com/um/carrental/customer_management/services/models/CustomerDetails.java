package com.um.carrental.customer_management.services.models;

public class CustomerDetails {
    private String name;
    private int age;


    public CustomerDetails(String name, int age){
        this.name = name;
        this.age = age;
    }

    public CustomerDetails(){

    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge(){
        return age;
    }

}
