package com.www.model;

public class User {
    private String name;
    private int isTeacher;

    public void setName(String name){
        this.name = name;
    }

    public void setIsTeacher(int isTeacher){
        this.isTeacher = isTeacher;
    }

    public String getName(){
        return this.name;
    }

    public int getIsTeacher(){
        return this.isTeacher;
    }
}
