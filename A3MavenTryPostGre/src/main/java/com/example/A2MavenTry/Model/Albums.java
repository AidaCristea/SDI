package com.example.A2MavenTry.Model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="albums")
public class Albums {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlbum;

    private String albumName;
    private Integer yearRelease;
    private Integer noSongs;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idGroup")
    Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="idSinger")
    Singer singer;

    public Albums(){}

    public Albums( String albumName, Integer yearRelease, Integer noSongs) {
        this.albumName = albumName;
        this.yearRelease = yearRelease;
        this.noSongs = noSongs;

    }

    public Albums(Integer idAlbum, String albumName, Integer yearRelease, Integer noSongs) {
        this.idAlbum = idAlbum;
        this.albumName = albumName;
        this.yearRelease = yearRelease;
        this.noSongs = noSongs;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Albums albums)) return false;
        return idAlbum == albums.idAlbum && Objects.equals(albumName, albums.albumName) && Objects.equals(yearRelease, albums.yearRelease) && Objects.equals(noSongs, albums.noSongs) && Objects.equals(group, albums.group) && Objects.equals(singer, albums.singer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlbum, albumName, yearRelease, noSongs, group, singer);
    }
}
