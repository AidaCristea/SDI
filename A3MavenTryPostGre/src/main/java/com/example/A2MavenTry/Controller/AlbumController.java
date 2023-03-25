package com.example.A2MavenTry.Controller;


import com.example.A2MavenTry.Exceptions.AlbumNotFoundException;
import com.example.A2MavenTry.Exceptions.GroupNotFoundException;
import com.example.A2MavenTry.Exceptions.SingerNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.AlbumsRepository;
import com.example.A2MavenTry.Repository.GroupRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AlbumController {
    @Autowired
    AlbumsRepository albumsRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SingerRepository singerRepository;

    public AlbumController(AlbumsRepository albumsRepository, GroupRepository groupRepository, SingerRepository singerRepository) {
        this.albumsRepository = albumsRepository;
        this.groupRepository = groupRepository;
        this.singerRepository = singerRepository;
    }

    @GetMapping("/albums")
    public List<AlbumDTOWithId> getAllAlbums()
    {
        ModelMapper modelMapper=new ModelMapper();
        return albumsRepository.findAll().stream()
                .map(al -> modelMapper.map(al, AlbumDTOWithId.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/albums/{id}")
    public AlbumsDTO getAlbumsById(@PathVariable Integer id)
    {
        ModelMapper modelMapper=new ModelMapper();
        return albumsRepository.findById(id)
                .map(al -> modelMapper.map(al, AlbumsDTO.class))
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }

/*    @PostMapping("/albums")
    public Albums createAlbum(@RequestBody Albums al)
    {
        return albumsRepository.save(al);
    }*/





    //create/add Group for a Singer
    @PostMapping("/albums/singers/{id}/groups/{idd}")
    public Albums addAlbumToSingerAndGroup(@PathVariable("id") Integer id, @PathVariable("idd") Integer idd, @RequestBody Albums album)
    {
        //find singer by id
        Singer singer = singerRepository.findById(id)
                .orElseThrow(() -> new SingerNotFoundException(id));

        //find group by id
        Group group = groupRepository.findById(idd)
                .orElseThrow(() -> new GroupNotFoundException(idd));

        album.setSinger(singer);
        album.setGroup(group);

        Albums al = albumsRepository.save(album);

        singer.getAlbums().add(al);
        group.getAlbums().add(al);
        return al;

    }


    @PutMapping("/albums/{id}")
    Albums replaceAlbum(@RequestBody Albums al, @PathVariable Integer id)
    {
        return albumsRepository.findById(id)
                .map(album -> {
                    album.setAlbumName(al.getAlbumName());
                    album.setYearRelease(al.getYearRelease());
                    album.setNoSongs(al.getNoSongs());
                    return albumsRepository.save(album);
                })
                .orElseGet(() -> {
                    al.setIdAlbum(id);
                    return albumsRepository.save(al);
                });
    }

    @DeleteMapping("/albums/{id}")
    public void deleteAlbumById(@PathVariable Integer id)
    {
        albumsRepository.deleteById(id);
    }




}

