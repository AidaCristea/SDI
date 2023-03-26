package com.example.A2MavenTry.Controller;


import com.example.A2MavenTry.Exceptions.RecordLableNotFoundException;
import com.example.A2MavenTry.Exceptions.SingerNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
    private final SingerRepository repo;


    public Controller(SingerRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/singers")
    public List<SingerDTOWithId> getAll()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Singer.class, SingerDTOWithId.class).addMapping(singer -> singer.getRecordLable().getIdRecLbl(), SingerDTOWithId::setRecLblId);
        List<SingerDTOWithId> singerDTOWithIds = repo.findAll().stream()
                .map(singer -> modelMapper.map(singer, SingerDTOWithId.class))
                .collect(Collectors.toList());
        return singerDTOWithIds;


    }


    @GetMapping("/singers/{id}")
    public SingerDTO getSingerById(@PathVariable("id") Integer id)
    {
        if (repo.findById(id).isEmpty())
            throw new SingerNotFoundException(id);



        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Singer.class, SingerDTO.class).addMapping(singer -> singer.getRecordLable(), SingerDTO::setRecLbl);
        SingerDTO singerDTO = modelMapper.map(repo.findById(id).get(), SingerDTO.class);
        return singerDTO;


    }



    @PutMapping("/singers/{id}")
    Singer replaceSinger(@RequestBody Singer newSinger, @PathVariable Integer id)
    {
        return repo.findById(id)
                .map(singer -> {
                    singer.setAge(newSinger.getAge());
                    singer.setCity(newSinger.getCity());
                    singer.setFirstName(newSinger.getFirstName());
                    singer.setLastName(newSinger.getLastName());
                    singer.setTypeOfMusic(newSinger.getTypeOfMusic());
                    return repo.save(singer);
                })
                .orElseGet(() -> {
                    newSinger.setIdSinger(id);
                    return repo.save(newSinger);
                });


    }


    @DeleteMapping("/singers/{id}")
    public void deleteSingerById(@PathVariable("id") Integer id)
    {
        repo.deleteById(id);
    }


    @GetMapping("/singers/greaterThan/{givenAge}")
    public List<SingerDTOWithId> findByAgeGreaterThanEqual(@PathVariable Integer givenAge)
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Singer.class, SingerDTOWithId.class).addMapping(singer -> singer.getRecordLable().getIdRecLbl(), SingerDTOWithId::setRecLblId);
        List<SingerDTOWithId> singerDTOWithIds = repo.findByAgeGreaterThanEqual(givenAge).stream()
                .map(singer -> modelMapper.map(singer, SingerDTOWithId.class))
                .collect(Collectors.toList());
        return singerDTOWithIds;

        //return repo.findByAgeGreaterThanEqual(givenAge);
    }



    @GetMapping("/average-nr-songs")
    public List<SingerDTOForAvg> singersOrderedByAlbumNrOfSongs()
    {
        ModelMapper modelMapper=new ModelMapper();
        List<Singer> singersList = repo.findAll();

        singersList.sort(Comparator.comparing(Singer::getAverageAlbumNrSongs)
                .reversed());

        List<SingerDTOForAvg> singerDTOForAvgList=new ArrayList<>();
        //List<GroupDTOForAvg> groupDTOForAvgList=new ArrayList<>();
        for(Singer sg: singersList)
        {
            if(!Double.isNaN(sg.getAverageAlbumNrSongs()))
            {
                SingerDTOForAvg singerDTOForAvg=modelMapper.map(sg, SingerDTOForAvg.class);
                singerDTOForAvg.setId(sg.getIdSinger());
                singerDTOForAvg.setFirstName(sg.getFirstName());
                singerDTOForAvg.setLastName(sg.getLastName());
                singerDTOForAvg.setAge(sg.getAge());
                singerDTOForAvg.setCity(sg.getCity());
                singerDTOForAvg.setTypeOfMusic(sg.getTypeOfMusic());
                singerDTOForAvg.setAvgAlbumNrSongs(sg.getAverageAlbumNrSongs());
                singerDTOForAvgList.add(singerDTOForAvg);


            }
        }
        return singerDTOForAvgList;
    }


}