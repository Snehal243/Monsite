package com.montek.monsite.model;
public class Categoires {
    String id = null;
    String categoires = null;
    String category_image=null;
    public Categoires(String id, String categoires, String category_image) {
        super();
        this.id = id;
        this.categoires = categoires;
        this.category_image = category_image;
    }
    public Categoires() {
    }
    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }
    public String getcategoires() {
        return categoires;
    }
    public void setcategoires(String categoires) {
        this.categoires = categoires;
    }
    public String getcategoires_image() {
        return category_image;
    }
    public void setcategoires_image(String categoires_image) {
        this.category_image = categoires_image;
    }
    @Override
    public String toString() {
        return  categoires;
    }
}