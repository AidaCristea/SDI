package com.example.A2MavenTry.Controller;


import com.example.A2MavenTry.Exceptions.AlbumNotFoundException;
import com.example.A2MavenTry.Exceptions.GroupNotFoundException;
import com.example.A2MavenTry.Exceptions.SingerNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.AlbumsRepository;
import com.example.A2MavenTry.Repository.GroupRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import com.example.A2MavenTry.Service.AlbumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AlbumController {
    /*@Autowired
    AlbumsRepository albumsRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SingerRepository singerRepository;*/

    @Autowired
    AlbumService albumService;

    /*public AlbumController(AlbumsRepository albumsRepository, GroupRepository groupRepository, SingerRepository singerRepository) {
        this.albumsRepository = albumsRepository;
        this.groupRepository = groupRepository;
        this.singerRepository = singerRepository;
    }*/

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }


    /*@GetMapping("/albums")
    public List<AlbumDTOWithId> getAllAlbums()
    {
        *//*ModelMapper modelMapper=new ModelMapper();
        return albumsRepository.findAll().stream()
                .map(al -> modelMapper.map(al, AlbumDTOWithId.class))
                .collect(Collectors.toList());
*//*
        return this.albumService.getAllAlbums();
    }*/

    @GetMapping("/albums/countAll")
    public Long countAllAlbums()
    {
        return this.albumService.countAllAlbums();
    }

    @GetMapping("/albums/page/{page}/size/{size}")
    public List<AlbumDTOWithId> getAllAlbums(@PathVariable int page, @PathVariable int size)
    {
        PageRequest pr = PageRequest.of(page, size);
        return this.albumService.getAllAlbums(pr);
    }

    @GetMapping("/albums/{id}")
    public AlbumsDTO getAlbumsById(@PathVariable Integer id)
    {
        /*ModelMapper modelMapper=new ModelMapper();
        return albumsRepository.findById(id)
                .map(al -> modelMapper.map(al, AlbumsDTO.class))
                .orElseThrow(() -> new AlbumNotFoundException(id));*/
        return this.albumService.getAlbumsById(id);
    }

/*    @PostMapping("/albums")
    public Albums createAlbum(@RequestBody Albums al)
    {
        return albumsRepository.save(al);
    }*/




    /*//create/add Group for a Singer
    @PostMapping("/albums/singers/{id}/groups/{idd}")
    public Albums addAlbumToSingerAndGroup(@PathVariable("id") Integer id, @PathVariable("idd") Integer idd, @RequestBody Albums album)
    {
        return this.albumService.addAlbumToSingerAndGroup(id, idd, album);

    }*/

    @PostMapping("/albums")
    public void createALbum(@RequestBody AlbumDTOWithId albumDTOWithId)
    {
        this.albumService.createAlbum(albumDTOWithId);
    }


    /*@PutMapping("/albums/{id}")
    Albums replaceAlbum(@RequestBody AlbumDTOWithId al, @PathVariable Integer id)
    {

        return albumService.replaceAlbum(al, id);
    }*/


    @PutMapping("/albums/{id}")
    public void replaceAlbum(@RequestBody AlbumDTOWithId al, @PathVariable Integer id)
    {

        albumService.replaceAlbum(al, id);
    }

    @DeleteMapping("/albums/{id}")
    public void deleteAlbumById(@PathVariable Integer id)
    {
        //albumsRepository.deleteById(id);
        albumService.deleteAlbumById(id);
    }




}

