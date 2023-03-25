package com.example.A2MavenTry.Model;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class AlbumDTOWithId {
    private Integer idAlbum;
    private String albumName;
    private Integer yearRelease;
    private Integer noSongs;
    private Integer idGroup;
    private Integer idSinger;

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public Integer getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(Integer idSinger) {
        this.idSinger = idSinger;
    }
}
