package com.example.A2MavenTry.Model;

public class AlbumDTOForAvg {
    public Integer id;
    private String albumName;
    private Integer yearRelease;
    private Integer noSongs;

    private Double avgMembersNumber;

    public AlbumDTOForAvg(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getYearRelease() {
        return yearRelease;
    }

    public void setYearRelease(Integer yearRelease) {
        this.yearRelease = yearRelease;
    }

    public Integer getNoSongs() {
        return noSongs;
    }

    public void setNoSongs(Integer noSongs) {
        this.noSongs = noSongs;
    }

    public Double getAvgMembersNumber() {
        return avgMembersNumber;
    }

    public void setAvgMembersNumber(Double avgMembersNumber) {
        this.avgMembersNumber = avgMembersNumber;
    }
}
