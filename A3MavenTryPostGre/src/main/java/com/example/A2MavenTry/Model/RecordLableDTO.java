package com.example.A2MavenTry.Model;

import java.util.Objects;

public class RecordLableDTO {
    private Integer id;
    private String nameRl;
    private String address;
    private Integer price;
    private String review;
    private Integer nrCollaborations;

    public RecordLableDTO(){}

    public RecordLableDTO(String nameRl, String address, Integer price, String review, Integer nrCollaborations) {
        this.nameRl = nameRl;
        this.address = address;
        this.price = price;
        this.review = review;
        this.nrCollaborations = nrCollaborations;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecordLableDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(nameRl, that.nameRl) && Objects.equals(address, that.address) && Objects.equals(price, that.price) && Objects.equals(review, that.review) && Objects.equals(nrCollaborations, that.nrCollaborations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRl, address, price, review, nrCollaborations);
    }
}
