package com.montek.monsite.model;
public class SortinglistFreelancer {
    String sorting_title = null;
    String image = null;
    public SortinglistFreelancer(int id , String sorting_title, String image) {
        super();
        this.sorting_title = sorting_title;
        this.image = image;
    }
    public String getsorting_title() {
        return sorting_title;
    }
//    public void setsorting_title(String sorting_title) {
//        this.sorting_title = sorting_title;
//    }
    public String getimage() {
        return image;
    }
//    public void setimage(String image) {
//        this.image = image;
//    }
//    @Override
//    public String toString() {
//        return  SubName + " " + SubFullForm ;
//    }

}