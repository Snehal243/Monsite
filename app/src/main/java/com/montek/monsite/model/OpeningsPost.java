package com.montek.monsite.model;
public class OpeningsPost {
    String id = null;
    String Title_Of_Requirment = null;
    String skill = null;
    String experience = null;
    String location = null;
    String skillrating = null;
    String duration = null;
    String Description = null;
    String CurrentTimeStamp = null;
    String companyName = null;
    String AboutCompany = null;
    String Education_Required = null;
    String contactpersonname = null;
    String email = null;
    String contact = null;
    int employer_id = 0;
    String freelancer_email=null;
    String freelancer_id=null;
    String freelancer_appliedprofile=null,dateofhiring=null;
    public OpeningsPost(String id, String Title_Of_Requirment, String skill, String experience,String dateofhiring, String location, String skillrating, String duration, String Education_Required, String Description, String companyName, String AboutCompany, String contactpersonname, String email, String contact, String CurrentTimeStamp, int employer_id, String freelancer_id, String freelancer_email, String freelancer_appliedprofile) {
        super();
        this.id = id;
        this.Title_Of_Requirment = Title_Of_Requirment;
        this.skill = skill;
        this.skill = skill;
        this.location = location;
        this.dateofhiring = dateofhiring;
        this.skillrating = skillrating;
        this.duration = duration;
        this.Description = Description;
        this.experience = experience;
        this.CurrentTimeStamp = CurrentTimeStamp;
        this.Education_Required = Education_Required;
        this.companyName = companyName;
        this.AboutCompany = AboutCompany;
        this.contactpersonname = contactpersonname;
        this.email = email;
        this.contact = contact;
        this.employer_id=employer_id;
        this.freelancer_id=freelancer_id;
        this.freelancer_email=freelancer_email;
        this.freelancer_appliedprofile=freelancer_appliedprofile;
     }

    public String getdateofhiring() {
        return dateofhiring;
    }
//    public void setdateofhiring(String dateofhiring) {
//            this.dateofhiring = dateofhiring;
//    }
//    public String getfreelancer_appliedprofile() {
//        return freelancer_appliedprofile;
//    }
//    public void setfreelancer_appliedprofile(String freelancer_appliedprofile) {
//        this.freelancer_appliedprofile = freelancer_appliedprofile;
//    }
    public String getfreelancer_id() {
        return freelancer_id;
    }
//    public void setfreelancer_id(String freelancer_id) {
//        this.freelancer_id = freelancer_id;
//    }
    public String getfreelancer_email() {
        return freelancer_email;
    }
//    public void setfreelancer_email(String freelancer_email) {
//        this.freelancer_email = freelancer_email;
//    }
    public String getid() {
        return id;
    }
//    public void setid(String id) {
//        this.id = id;
//    }
//    public int getemployer_id() {
//        return employer_id;
//    }
//    public void setemployer_id(int employer_id) {
//        this.employer_id = employer_id;
//    }
    public String getTitle_Of_Requirment() {
        return Title_Of_Requirment;
    }
//    public void setTitle_Of_Requirment(String Title_Of_Requirment) {
//        this.Title_Of_Requirment = Title_Of_Requirment;
//    }
    public String getskillrating() {
        return skillrating;
    }
    public String getduration() {
        return duration;
    }
//    public void setduration(String duration) {
//        this.duration = duration;
//    }
//    public String getDescription() {
//        return Description;
//    }
//    public void setDescription(String Description) {
//        this.Description = Description;
//    }
    public String getCurrentTimeStamp() {
        return CurrentTimeStamp;
    }
//    public void setCurrentTimeStamp(String CurrentTimeStamp) {
//        this.CurrentTimeStamp = CurrentTimeStamp;
//    }
    public String getskill() {
        return skill;
    }
//    public void setskill(String skill) {
//        this.skill = skill;
//    }
    public String getexperience() {
        return experience;
    }
//    public void setexperience(String experience) {
//        this.experience = experience;
//    }
    public String getlocation() {
        return location;
    }
//    public void setlocation(String location) {
//        this.location = location;
//    }
//    public String getEducation_Required() {
//        return Education_Required;
//    }
//    public void setEducation_Required(String Education_Required) {
//        this.Education_Required = Education_Required;
//    }
    public String getcompanyName() {
        return companyName;
    }
//    public void setcompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//    public String getAboutCompany() {
//        return AboutCompany;
//    }
//    public void setAboutCompany(String AboutCompany) {
//        this.AboutCompany = AboutCompany;
//    }
//    public String getemail() {
//        return email;
//    }
//    public void setemail(String email) {
//        this.email = email;
//    }
//    public String getcontactpersonname() {
//        return contactpersonname;
//    }
//    public void setcontactpersonname(String contactpersonname) {
//        this.contactpersonname = contactpersonname;
//    }
//    public String getcontact() {
//        return contact;
//    }
//    public void setcontact(String contact) {
//        this.contact = contact;
//    }
    @Override
    public String toString() {
           return location+skill+companyName;
    }
}