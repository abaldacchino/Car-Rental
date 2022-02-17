package com.um.carrental.customer_management.web.requests;

public class CustomerDetails {

    private String name;
    private String surname;
    private int age;

    public CustomerDetails(String name, String surname, int age, String Id){
        this.name = name;
        this.surname = surname;
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

    public void setSurname(String Surname) {
        this.surname = surname;
    }
    public String getSurname(){
        return surname;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge(){
        return age;
    }

}
