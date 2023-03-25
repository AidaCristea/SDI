package com.example.A2MavenTry.Model;

public class SingerDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String city;
    private String typeOfMusic;

    private RecordLableDTO recLbl;

    public SingerDTO(){}

    public RecordLableDTO getRecLbl() {
        return recLbl;
    }

    public void setRecLbl(RecordLableDTO recLbl) {
        this.recLbl = recLbl;
    }

    public Integer getId() {
        return id;
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

    }
