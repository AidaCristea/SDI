package com.example.A2MavenTry.Model;

public class RecordLableDTOForAvg {
    private Integer id;
    private String nameRl;
    private String address;
    private Integer price;
    private String review;
    private Integer nrCollaborations;
    private Double avgSingerAge;


    public RecordLableDTOForAvg(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameRl() {
        return nameRl;
    }

    public void setNameRl(String nameRl) {
        this.nameRl = nameRl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getNrCollaborations() {
        return nrCollaborations;
    }

    public void setNrCollaborations(Integer nrCollaborations) {
        this.nrCollaborations = nrCollaborations;
    }

    public Double getAvgSingerAge() {
        return avgSingerAge;
    }

    public void setAvgSingerAge(Double avgSingerAge) {
        this.avgSingerAge = avgSingerAge;
    }
}
