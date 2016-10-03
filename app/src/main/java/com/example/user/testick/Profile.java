package com.example.user.testick;

/**
 * Created by user on 03.10.2016.
 */

public class Profile {

    private String name, profession, about, image; //username;
    public Profile() {

    }

    public Profile(String name, String profession, String about, String image) {//, String username) {
        this.name = name;
        this.profession = profession;
        this.about = about;
        this.image = image;
        //this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /*public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }*/
}
