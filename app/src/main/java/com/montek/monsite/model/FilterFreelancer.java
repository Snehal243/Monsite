package com.montek.monsite.model;
public class FilterFreelancer {
    String freelancer_education,freelancer_rating,categoires = null,duration=null,sortelementid = null,rating = null,fb = null,fbcompanyname = null,id=null;
    private boolean selected =false;
    public FilterFreelancer(String sortelementid, String categoires, Boolean selected) {
        super();
        this.categoires = categoires;
        this.sortelementid = sortelementid;
        this.selected = selected;
    }
    public FilterFreelancer(String rateid, String rate, String duration) {
        this.sortelementid = rateid;
        this.categoires = rate;
        this.duration = duration;
    }
    public FilterFreelancer(String id,String fbcompanyname, String fb, String rating) {
        super();
        this.id = id;
        this.fbcompanyname = fbcompanyname;
        this.fb = fb;
        this.rating = rating;
    }
    public FilterFreelancer(String freelancer_username, String status, String datetime, String freelancer_skill, String freelancer_location, String freelancer_education, String freelancer_categoires,String freelancer_rating,String rating) {
        this.fbcompanyname = freelancer_username;
        this.fb = status;
        this.duration = datetime;
        this.sortelementid = freelancer_skill;
        this.categoires = freelancer_categoires;
        this.id = freelancer_location;
        this.freelancer_education=freelancer_education;
        this.rating = rating;
        this.freelancer_rating = freelancer_rating;
    }

    public String getfreelancer_education() {
        return freelancer_education;
    }
    public String getfreelancer_rating() {
        return freelancer_rating;
    }
    public String getfbcompanyname() {
        return fbcompanyname;
    }
//    public void setfbcompanyname(String fbcompanyname) {
//        this.fbcompanyname = fbcompanyname;
//    }
    public String getfb() {
        return fb;
    }
//    public void setfb(String fb) {
//        this.fb = fb;
//    }
    public String getrating() {
        return rating;
    }
//    public void setrating(String rating) {
//       this.rating = rating;
//    }
        public String getid() {
        return id;
    }
//    public void setid(String id) {
//        this.id = id;
//    }
       public String getcategoires() {
        return categoires;
    }
//    public void setcategoires(String categoires) {
//        this.categoires = categoires;
//    }
    public String getsortelementid() {
        return sortelementid;
    }
//    public void setsortelementid(String sortelementid) {
//        this.sortelementid = sortelementid;
//    }
    public String getduration() {
        return duration;
    }
//    public void setduration(String duration) {
//        this.duration = duration;
//    }
    public boolean getSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    @Override
    public String toString() {
        return  categoires  ;
    }
}