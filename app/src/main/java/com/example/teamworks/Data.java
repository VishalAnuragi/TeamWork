package com.example.teamworks;

public class Data {

     public String userEmail;
     public String userPassword;
     public String userName;

     public Data(){

     }



    public Data(String userEmail, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;


    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

