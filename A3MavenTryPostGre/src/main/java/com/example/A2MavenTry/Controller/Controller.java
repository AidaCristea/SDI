package com.example.A2MavenTry.Controller;


import com.example.A2MavenTry.Exceptions.RecordLableNotFoundException;
import com.example.A2MavenTry.Exceptions.SingerNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import com.example.A2MavenTry.Service.SingerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class Controller {


    @Autowired
    private final SingerService singerService;

    public Controller(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping("/singers/countAll")
    public Long countAllSingers()
    {
        return this.singerService.countAllSingers();
    }

    /*@GetMapping("/singers")
    public List<SingerDTOWithId> getAll()
    {
        *//*ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Singer.class, SingerDTOWithId.class).addMapping(singer -> singer.getRecordLable().getIdRecLbl(), SingerDTOWithId::setRecLblId);
        List<SingerDTOWithId> singerDTOWithIds = repo.findAll().stream()
                .map(singer -> modelMapper.map(singer, SingerDTOWithId.class))
                .collect(Collectors.toList());
        return singerDTOWithIds;
*//*
        return this.singerService.getAll();

    }*/

    @GetMapping("/singers/page/{page}/size/{size}")
    public List<SingerDTOWithId> getAll(@PathVariable int page, @PathVariable int size)
    {
        PageRequest pr = PageRequest.of(page, size);
        List<SingerDTOWithId> singers = this.singerService.getAll(pr);
        singers.sort(Comparator.comparing(SingerDTOWithId::getLastName).thenComparing(SingerDTOWithId::getFirstName));
        return singers;
        //return this.singerService.getAll(pr);

    }


    @GetMapping("/singers/{id}")
    public SingerDTO getSingerById(@PathVariable("id") String id)
    {
        return this.singerService.getSingerById(id);
    }


    @PostMapping("/singers")
    public void createSinger(@RequestBody SingerDTOWithId singer)
    {
        //System.out.println(singer.getRecordLable());
        this.singerService.createSinger(singer);
    }



    @PutMapping("/singers/{id}")
    public void replaceSinger(@RequestBody SingerDTOWithId newSinger, @PathVariable Integer id)
    {
        singerService.replaceSinger(newSinger, id);
    }


    @DeleteMapping("/singers/{id}")
    public void deleteSingerById(@PathVariable("id") Integer id)
    {
        //repo.deleteById(id);
        this.singerService.deleteSingerById(id);
    }


    @GetMapping("/singers/greaterThan/{givenAge}")
    public List<SingerDTOWithId> findByAgeGreaterThanEqual(@PathVariable Integer givenAge)
    {
        return singerService.findByAgeGreaterThanEqual(givenAge);
        //return repo.findByAgeGreaterThanEqual(givenAge);
    }



    @GetMapping("/average-nr-songs")
    public List<SingerDTOForAvg> singersOrderedByAlbumNrOfSongs()
    {
        return this.singerService.singersOrderedByAlbumNrOfSongs();
    }

    @GetMapping("/singers/autocomplete")
    public List<SingerDTO> getSg(@RequestParam String query)
    {
        return this.singerService.getSingersAutocomplete(query);
    }


}