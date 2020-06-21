package com.sw.PuppyFriends;

public class Userinformation {

    public String name;
    public String address;
    public String phoneno;
    public String gender;
    public String loc;
    public String age;

    public Userinformation(){
    }

    public Userinformation(String name,String surname, String phoneno, String gender,String loc,String age){
        this.name = name;
        this.address = surname;
        this.phoneno = phoneno;
        this.gender = gender;
        this.loc = loc;
        this.age = age;
    }
    public String getUserName() {
        return name;
    }
    public String getUserAddress() {
        return address;
    }
    public String getUserPhoneno() {
        return phoneno;
    }
    public String getUserGender(){return gender;}
    public String getUserLoc(){return loc;}
    public String getUserAge(){return age;}
}
