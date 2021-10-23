package com.nura.cumdyapp.Model;

/**
 * Created by USER on 12/17/2017.
 */

public class Voting_Result {

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String no,name;

    public Voting_Result(String no, String name) {
        this.no = no;
        this.name = name;
    }

}
