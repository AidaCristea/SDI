package com.example.A2MavenTry.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "singers")
//implements Serializable
public class Singer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSinger;

    private String firstName;

    private String lastName;

    private Integer age;
    private String city;
    private String typeOfMusic;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recLbl_id")
    @JsonIgnore
    private RecordLable recordLable;

    // The @JoinColumn annotation is used to specify the foreign key column in the relationship owner.


    @OneToMany(mappedBy = "singer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Albums> albums;


    public Singer(){}

    public Singer(String firstName, String lastName, Integer age, String city, String typeOfMusic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.typeOfMusic = typeOfMusic;
    }

    public double getAverageAlbumNrSongs()
    {
        int sumOfNrSongs = albums.stream()
                .mapToInt(album-> album.getNoSongs())
                .sum();
        return sumOfNrSongs/(double) albums.size();

    }
    public List<Albums> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Albums> albums) {
        this.albums = albums;
    }

    public Integer getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(Integer idSinger) {
        this.idSinger = idSinger;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTypeOfMusic() {
        return typeOfMusic;
    }

    public void setTypeOfMusic(String typeOfMusic) {
        this.typeOfMusic = typeOfMusic;
    }

    public RecordLable getRecordLable() {
        return recordLable;
    }

    public void setRecordLable(RecordLable recordLable) {
        this.recordLable = recordLable;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Singer singer)) return false;
        return Objects.equals(idSinger, singer.idSinger) && Objects.equals(firstName, singer.firstName) && Objects.equals(lastName, singer.lastName) && Objects.equals(age, singer.age) && Objects.equals(city, singer.city) && Objects.equals(typeOfMusic, singer.typeOfMusic) && Objects.equals(recordLable, singer.recordLable) && Objects.equals(albums, singer.albums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSinger, firstName, lastName, age, city, typeOfMusic, recordLable, albums);
    }
}
