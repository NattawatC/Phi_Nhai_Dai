package com.example.phi_nhai_dai.main_page;

public class Place {
    private int id;
    private String name;
    private String location;
    private String img_link;
    private float rating;
    private String region;
    private String FavStatus;

    public Place(int id, String name, String location, String img_link, float rating, String region) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.img_link = img_link;
        this.rating = rating;
        this.region = region;
    }

    public void setFavStatus(String favStatus) {
        FavStatus = favStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public String getLocation() {
        return location;
    }

    public String getImg_link() {
        return img_link;
    }

    public float getRating() {
        return rating;
    }

    public String getRegion() {
        return region;
    }

    public String  getFavStatus() {
        return FavStatus;
    }
}
