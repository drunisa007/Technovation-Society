package com.nura.cumdyapp.Model;

import android.support.v4.app.Fragment;

/**
 * Created by USER on 12/17/2017.
 */

public class Result {
    public Result(Fragment fragment, String name) {
        this.fragment = fragment;
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private    Fragment fragment;

 private String name;

   public Result(){

    }
}
