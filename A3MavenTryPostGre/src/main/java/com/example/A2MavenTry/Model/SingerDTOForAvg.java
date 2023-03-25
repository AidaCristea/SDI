package com.example.A2MavenTry.Model;

public class SingerDTOForAvg {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String city;
    private String typeOfMusic;

    private Double avgAlbumNrSongs;

    public SingerDTOForAvg(){}

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
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

    public Double getAvgAlbumNrSongs() {
        return avgAlbumNrSongs;
    }

    public void setAvgAlbumNrSongs(Double avgAlbumNrSongs) {
        this.avgAlbumNrSongs = avgAlbumNrSongs;
    }
}
