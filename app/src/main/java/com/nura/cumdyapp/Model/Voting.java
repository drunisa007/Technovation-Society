package com.nura.cumdyapp.Model;

/**
 * Created by USER on 12/12/2017.
 */

public class Voting {
   private String name;
   private String no;
  private   String url;
   private String height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }



    public Voting(){

    }

    public Voting(String name, String no, String url,String height,String id) {
        this.name = name;
        this.no = no;
        this.url = url;
        this.height=height;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
