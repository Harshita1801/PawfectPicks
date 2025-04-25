package com.example.pawfectpicks.models;

public class PetModel {
    String petPic, username, pname, page, ptype, pgender, postedBy ;

    public PetModel() {

    }
    public PetModel(String petPic,String username, String pname, String page, String ptype, String pgender, String postedBy) {
        this.username = username;
        this.petPic= petPic;
        this.pname=pname;
        this.page=page;
        this.ptype=ptype;
        this.pgender=pgender;
        this.postedBy=postedBy;
    }

    public String getPetPic() {
        return petPic;
    }

    public void setPetPic(String petPic) {
        this.petPic = petPic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getPgender() {
        return pgender;
    }

    public void setPgender(String pgender) {
        this.pgender = pgender;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
