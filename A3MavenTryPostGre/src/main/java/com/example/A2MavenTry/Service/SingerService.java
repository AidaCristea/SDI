package com.example.A2MavenTry.Service;

import com.example.A2MavenTry.Exceptions.SingerNotFoundException;
import com.example.A2MavenTry.Model.Singer;
import com.example.A2MavenTry.Model.SingerDTO;
import com.example.A2MavenTry.Model.SingerDTOForAvg;
import com.example.A2MavenTry.Model.SingerDTOWithId;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SingerService {
    @Autowired
    private final SingerRepository repo;


    public SingerService(SingerRepository repo) {
        this.repo = repo;
    }



    public Long countAllSingers()
    {
        return repo.count();
    }


    //@GetMapping("/singers")
    public List<SingerDTOWithId> getAll(PageRequest pr)
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Singer.class, SingerDTOWithId.class).addMapping(singer -> singer.getRecordLable().getIdRecLbl(), SingerDTOWithId::setRecLblId);
        Page<Singer> singers=repo.findAll(pr);
        List<SingerDTOWithId> singerDTOWithIds = singers.stream()
                .map(singer -> modelMapper.map(singer, SingerDTOWithId.class))
                .collect(Collectors.toList());
        return singerDTOWithIds;


    }


    //@GetMapping("/singers/{id}")
    public SingerDTO getSingerById(String id)
    {
        Integer singer_id = Integer.parseInt(id);
        if (repo.findById(singer_id ).isEmpty())
            throw new SingerNotFoundException(singer_id) ;



        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Singer.class, SingerDTO.class).addMapping(singer -> singer.getRecordLable(), SingerDTO::setRecLbl);
        SingerDTO singerDTO = modelMapper.map(repo.findById(singer_id ).get(), SingerDTO.class);
        return singerDTO;


    }



    //@PutMapping("/singers/{id}")
    public Singer replaceSinger( Singer newSinger,  Integer id)
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


    //@DeleteMapping("/singers/{id}")
    public void deleteSingerById( Integer id)
    {
        repo.deleteById(id);
    }


    //@GetMapping("/singers/greaterThan/{givenAge}")
    public List<SingerDTOWithId> findByAgeGreaterThanEqual( Integer givenAge)
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Singer.class, SingerDTOWithId.class).addMapping(singer -> singer.getRecordLable().getIdRecLbl(), SingerDTOWithId::setRecLblId);
        List<SingerDTOWithId> singerDTOWithIds = repo.findByAgeGreaterThanEqual(givenAge).stream()
                .map(singer -> modelMapper.map(singer, SingerDTOWithId.class))
                .collect(Collectors.toList());
        return singerDTOWithIds;

        //return repo.findByAgeGreaterThanEqual(givenAge);
    }



    //@GetMapping("/average-nr-songs")
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
