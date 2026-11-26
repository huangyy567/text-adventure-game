package com.itheima.domain;

import java.util.Random;

public class User {
    private String id;
    private String password;
    private String username;
    private boolean status;

    public User(){
        id=CreatID();
        status=true;
    }

    public User(String id,String password,String username,boolean status){
        this.id=id;
        this.password=password;
        this.username=username;
        status=true;
    }

    public String CreatID(){
        StringBuilder sb=new StringBuilder("heima");

        Random r=new Random();
        for (int i = 0; i < 5; i++) {
            int num=r.nextInt(10);//0-9
            sb.append(num);
        }
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
