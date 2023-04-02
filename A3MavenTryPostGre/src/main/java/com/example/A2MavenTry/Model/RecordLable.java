package com.example.A2MavenTry.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "recordLables")
//implements Serializable
@JsonIgnoreProperties("singers")
public class RecordLable implements Serializable {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer idRecLbl;
    @NotBlank(message ="Name is mandatory")
    private String nameRl;
    private String address;
    @Min(value=1, message ="Price should be more than 0")
    private Integer price;
    private String review;
    @NotNull(message = "The number of collaborations should not pe null")
    private Integer nrCollaborations;

    @OneToMany(mappedBy = "recordLable", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Singer> singers;


    public double getAverageSingerAge()
    {
        int sumOfAges = singers.stream()
                .mapToInt(singer -> singer.getAge())
                .sum();
        return sumOfAges/(double) singers.size();

    }

    public RecordLable() {}

    public RecordLable(String nameRl, String address, Integer price, String review, Integer nrCollaborations) {

        this.nameRl = nameRl;
        this.address = address;
        this.price = price;
        this.review = review;
        this.nrCollaborations = nrCollaborations;

    }

    public RecordLable(Integer idRecLbl, String nameRl, String address, Integer price, String review, Integer nrCollaborations) {
        this.idRecLbl = idRecLbl;
        this.nameRl = nameRl;
        this.address = address;
        this.price = price;
        this.review = review;
        this.nrCollaborations = nrCollaborations;
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

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public Integer getIdRecLbl() {
        return idRecLbl;
    }

    public String getNameRl() {
        return nameRl;
    }

    public String getAddress() {
        return address;
    }

    public void setIdRecLbl(Integer idRecLbl) {
        this.idRecLbl = idRecLbl;
    }

    public void setNameRl(String nameRl) {
        this.nameRl = nameRl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecordLable that)) return false;
        return idRecLbl == that.idRecLbl && price == that.price && nrCollaborations == that.nrCollaborations && Objects.equals(nameRl, that.nameRl) && Objects.equals(address, that.address) && Objects.equals(review, that.review) && Objects.equals(singers, that.singers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecLbl, nameRl, address, price, review, nrCollaborations, singers);
    }



}
