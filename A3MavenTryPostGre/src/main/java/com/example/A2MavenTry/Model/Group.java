package com.example.A2MavenTry.Model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "MyGroup")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGroup;

    private Integer members;
    private String dateFormed;
    private String nameGr;
    private String musicSpecialization;
    private String review;

    private String description;


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Albums> albums;

    public double getAverageAlbumNrSongs()
    {
        int sumOfNrSongs = albums.stream()
                .mapToInt(album-> album.getNoSongs())
                .sum();
        return sumOfNrSongs/(double) albums.size();

    }
    public Group(){}

    public Group(Integer members, String dateFormed, String nameGr, String musicSpecialization, String review, String description) {
        this.members = members;
        this.dateFormed = dateFormed;
        this.nameGr = nameGr;
        this.musicSpecialization = musicSpecialization;
        this.review = review;
        this.description = description;
    }

    /*public Group(Integer members, String dateFormed, String nameGr, String musicSpecialization, String review) {
        this.members = members;
        this.dateFormed = dateFormed;
        this.nameGr = nameGr;
        this.musicSpecialization = musicSpecialization;
        this.review = review;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
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

    public List<Albums> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Albums> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;
        return Objects.equals(idGroup, group.idGroup) && Objects.equals(members, group.members) && Objects.equals(dateFormed, group.dateFormed) && Objects.equals(nameGr, group.nameGr) && Objects.equals(musicSpecialization, group.musicSpecialization) && Objects.equals(review, group.review) && Objects.equals(description, group.description) && Objects.equals(albums, group.albums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroup, members, dateFormed, nameGr, musicSpecialization, review, description, albums);
    }
}
