package com.example.A2MavenTry.Model;

import lombok.Data;

@Data
public class AlbumsDTO {
    private Integer idAlbum;

    private String albumName;
    private Integer yearRelease;
    private Integer noSongs;

    private GroupDTO group;
    private SingerDTOWithId singer;

}
