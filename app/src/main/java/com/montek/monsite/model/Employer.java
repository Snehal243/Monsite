package com.montek.monsite.model;
public class Employer {
    String  clientrating=null,startdate=null,freelancer_address=null,totalpaidrate=null,totalduration =null;
    String  freelancer_id = null,freelancer_rating=null;
    String username = null,employer_id=null;
    String email = null;
    String contact = null;
    String summary = null;
    String skill = null,status_reason=null;
    String industryexp = null;
    String domainexp = null;
    String education = null;
    String experience = null;
    String image = null,evfb_id=null,certification=null,freelancer_designation=null;
    String status = null,employerviewedfreelancerid=null;
    String location = null,System_date=null,paymentstatus=null,paymentId=null,approvestatus=null;
    String gstapplied=null,onwhichdurationhired=null,rateperduration=null,employer_email=null,freelancer_Categoires=null,freelancer_SubCategoires=null,gender=null,employer_hirefreelancer=null;
    public Employer(String freelancer_id, String freelancer_username, String freelancer_email, String freelancer_contact, String freelancer_summary, String freelancer_skill, String freelancer_industryexp, String freelancer_domainexp, String freelancer_education, String freelancer_experience, String freelancer_image, String freelancer_status,String status_reason, String freelancer_location, String freelancer_Categoires, String freelancer_SubCategoires, String freelancer_rating, String gender, String System_date, String freelancer_address, String employer_id, String employer_email, String employer_hirefreelancer,String onwhichdurationhired,String rateperduration,String totalpaidrate,String totalduration ,String startdate ,String clientrating,String employerviewedfreelancerid,String evfb_id,String paymentId,String approvestatus,String paymentstatus,String freelancer_designation,String certification,String gstapplied ) {
       super();
        this.employerviewedfreelancerid = employerviewedfreelancerid;
        this.gstapplied = gstapplied;
        this.freelancer_id = freelancer_id;
        this.username = freelancer_username;
        this.email = freelancer_email;
        this.contact = freelancer_contact;
        this.summary = freelancer_summary;
        this.skill = freelancer_skill;
        this.industryexp = freelancer_industryexp;
        this.domainexp = freelancer_domainexp;
        this.education = freelancer_education;
        this.experience = freelancer_experience;
        this.image = freelancer_image;
        this.status = freelancer_status;
        this.status_reason = status_reason;
        this.location = freelancer_location;
        this.freelancer_Categoires = freelancer_Categoires;
        this.freelancer_SubCategoires = freelancer_SubCategoires;
        this.freelancer_rating=freelancer_rating;
        this.gender = gender;
        this.freelancer_address = freelancer_address;
        this.employer_id = employer_id;
        this.employer_email = employer_email;
        this.System_date = System_date;
        this.employer_hirefreelancer = employer_hirefreelancer;
        this.rateperduration = rateperduration;
        this.onwhichdurationhired = onwhichdurationhired;
        this.totalpaidrate = totalpaidrate;
        this.totalduration  = totalduration ;
        this.startdate = startdate;
        this.clientrating  = clientrating ;
        this.paymentId  = paymentId ;
        this.approvestatus = approvestatus;
        this.paymentstatus  = paymentstatus ;
        this.evfb_id  = evfb_id ;
        this.certification  = certification ;
        this.freelancer_designation  = freelancer_designation ;
    }
    public String getemployerviewedfreelancerid() {
        return employerviewedfreelancerid;
    }
//    public void setemployerviewedfreelancerid(String employerviewedfreelancerid) {
//        this.employerviewedfreelancerid = employerviewedfreelancerid;
//    }
    public String getevfb_id() {
        return evfb_id;
    }
//    public void setevfb_id(String evfb_id) {
//        this.evfb_id = evfb_id;
//    }
    public String getclientrating() {
        return clientrating;
    }
//    public void setclientrating(String clientrating) {
//        this.clientrating = clientrating;
//    }
    public String getstartdate() {
        return startdate;
    }
//    public void setstartdate(String startdate) {
//        this.startdate = startdate;
//    }
    public String gettotalduration() {
        return totalduration;
    }
//    public void settotalduration(String totalduration) {
//        this.totalduration = totalduration;
//    }

    public String getpaymentId() {
        return paymentId;
    }
//    public void setpaymentId(String paymentId) {
//        this.paymentId = paymentId;
//    }
    public String getapprovestatus() {
        return approvestatus;
    }
//    public void setapprovestatus(String approvestatus) {
//        this.approvestatus = approvestatus;
//    }
    public String getgstapplied() {
        return gstapplied;
    }
    public String getpaymentstatus() {
        return paymentstatus;
    }
//    public void setpaymentstatus(String paymentstatus) {
//        this.totalduration = paymentstatus;
//    }
    public String gettotalpaidrate() {
        return totalpaidrate;
    }
    public String getcertification() {
        return certification;
    }
    public String getfreelancer_designation() {
        return freelancer_designation;
    }
//    public void settotalpaidrate(String totalpaidrate) {
//        this.totalpaidrate = totalpaidrate;
//    }
    public String getfreelancer_rating() {
        return freelancer_rating;
    }
//    public void setfreelancer_rating(String freelancer_rating) {
//        this.freelancer_rating = freelancer_rating;
//    }
    public String getemployer_hirefreelancer() {
        return employer_hirefreelancer;
    }
//    public void setemployer_hirefreelancer(String employer_hirefreelancer) {
//        this.employer_hirefreelancer = employer_hirefreelancer;
//    }
    public String getfreelancer_address() {
        return freelancer_address;
    }
//    public void setfreelancer_address(String freelancer_address) {
//        this.freelancer_address = freelancer_address;
//    }
    public String getfreelancer_Categoires() {
        return freelancer_Categoires;
    }
//    public void setfreelancer_Categoires(String freelancer_Categoires) {
//        this.freelancer_Categoires = freelancer_Categoires;
//    }
    public String getStatus_reason() {
        return status_reason;
    }
//    public void setstatus_reason(String status_reason) {
//        this.status_reason = status_reason;
//    }
    public String getfreelancer_SubCategoires() {
        return freelancer_SubCategoires;
    }
//    public void setfreelancer_SubCategoires(String freelancer_SubCategoires) {
//        this.freelancer_SubCategoires = freelancer_SubCategoires;
//    }
    public String getgender() {
        return gender;
    }
//    public void setDOB(String DOB) {
//        this.DOB = DOB;
//    }
    public String getfreelancer_id() {
        return freelancer_id;
    }
//    public void setfreelancer_id(String freelancer_id) {
//        this.freelancer_id = freelancer_id;
//    }
    public String getusername() {
        return username;
    }
//    public void setusername(String username) {
//        this.username = username;
//    }
    public String getemail() {
        return email;
    }
//    public void setemail(String email) {
//        this.email = email;
//    }
    public String getcontact() {
        return contact;
    }
//    public void setcontact(String contact) {
//        this.contact = contact;
//    }
    public String getsummary() {
        return summary;
    }
//    public void setsummary(String summary) {
//        this.summary = summary;
//    }
    public String getdomainexp() {
        return domainexp;
    }
//    public void setdomainexp(String domainexp) {
//        this.domainexp = domainexp;
//    }
    public String getindustryexp() {
        return industryexp;
    }
//    public void setindustryexp(String industryexp) {
//        this.industryexp = industryexp;
//    }
    public String geteducation() {
        return education;
    }
//    public void seteducation(String education) {
//        this.education = education;
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
    public String getstatus() {
        return status;
    }
//    public void setstatus(String status) {
//        this.status = status;
//    }
    public String getimage() {
        return image;
    }
//    public void setimage(String image) {
//        this.image = image;
//    }
    public String getlocation() {
        return location;
    }
//    public void setlocation(String location) {
//        this.location = location;
//    }
    public String getemployer_id() {
        return employer_id;
    }
//    public void setemployer_id(String employer_id) {
//        this.employer_id = employer_id;
//    }
    public String getemployer_email() {
        return employer_email;
    }
//    public void setemployer_email(String employer_email) {
//        this.employer_email = employer_email;
//    }
    public String getSystem_date() {
        return System_date;
    }
//    public void setSystem_date(String System_date) {
//        this.System_date = System_date;
//    }
    public String getrateperduration() {
        return rateperduration;
    }
//    public void setrateperduration(String rateperduration) {
//        this.rateperduration = rateperduration;
//    }
    public String getonwhichdurationhired() {
        return onwhichdurationhired;
    }
//    public void setonwhichdurationhired(String onwhichdurationhired) {
//        this.onwhichdurationhired = onwhichdurationhired;
//    }
    @Override
    public String toString() {
        return username+location+skill;
    }
}