package com.nura.cumdyapp.Model;

/**
 * Created by USER on 12/12/2017.
 */

public class King {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    private int url;
    public  King(){

    }

    public King(String name, int url) {
        this.name = name;
        this.url = url;
    }
}
