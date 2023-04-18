package com.example.A2MavenTry.Model;

public class GroupDTOForAvg {
    private Integer id;

    private Integer members;
    private String dateFormed;
    private String nameGr;
    private String musicSpecialization;
    private String review;
    private String description;
    private Double avgAlbumsNrSongs;

    public GroupDTOForAvg(){}

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getDateFormed() {
        return dateFormed;
    }

    public void setDateFormed(String dateFormed) {
        this.dateFormed = dateFormed;
    }

    public String getNameGr() {
        return nameGr;
    }

    public void setNameGr(String nameGr) {
        this.nameGr = nameGr;
    }

    public String getMusicSpecialization() {
        return musicSpecialization;
    }

    public void setMusicSpecialization(String musicSpecialization) {
        this.musicSpecialization = musicSpecialization;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Double getAvgAlbumsNrSongs() {
        return avgAlbumsNrSongs;
    }

    public void setAvgAlbumsNrSongs(Double avgAlbumsNrSongs) {
        this.avgAlbumsNrSongs = avgAlbumsNrSongs;
    }
}
