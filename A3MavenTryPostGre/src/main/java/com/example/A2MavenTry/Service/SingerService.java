package com.example.A2MavenTry.Service;

import com.example.A2MavenTry.Exceptions.SingerNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

    @Autowired
    RecordLableRepository rLrepo;


    /*public SingerService(SingerRepository repo) {
        this.repo = repo;
    }*/


    public SingerService(SingerRepository repo, RecordLableRepository rLrepo) {
        this.repo = repo;
        this.rLrepo = rLrepo;
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

    public void createSinger(SingerDTOWithId singerdto)
    {
        ModelMapper modelMapper = new ModelMapper();

        RecordLable finalrec=null;

        //Integer id = singerdto.getRecLbl().getId();

        Integer id = singerdto.getRecLblId();
        for(RecordLable rl: rLrepo.findAll())
        {
            if(rl.getIdRecLbl()==id)
            {
                finalrec=rl;
                break;
            }
        }

        Singer singer=modelMapper.map(singerdto, Singer.class);

        singer.setRecordLable(finalrec);

        System.out.println(singer.getRecordLable());
        System.out.println(singer);

        repo.save((Singer) singer);

    }


    //@PutMapping("/singers/{id}")
    public void replaceSinger( SingerDTOWithId newSinger,  Integer id)
    {
        ModelMapper modelMapper = new ModelMapper();
        RecordLable finalrec=null;

        //find the record lable in the repository
        Integer idr = newSinger.getRecLblId();
        for(RecordLable rl: rLrepo.findAll())
        {
            if(rl.getIdRecLbl()==idr)
            {
                finalrec=rl;
                break;
            }
        }

        Singer singerr=modelMapper.map(newSinger, Singer.class);

        singerr.setRecordLable(finalrec);

        //inainte era newSinger
         repo.findById(id)
                .map(singer -> {
                    singer.setAge(singerr.getAge());
                    singer.setCity(singerr.getCity());
                    singer.setFirstName(singerr.getFirstName());
                    singer.setLastName(singerr.getLastName());
                    singer.setTypeOfMusic(singerr.getTypeOfMusic());
                    singer.setRecordLable(singerr.getRecordLable());
                    return repo.save(singer);
                })
                .orElseGet(() -> {
                    singerr.setIdSinger(id);
                    //newSinger.setIdSinger(id);
                    //return repo.save(newSinger);
                    return repo.save(singerr);
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


    public List<SingerDTO> getSingersAutocomplete(String query)
    {
        ModelMapper modelMapper=new ModelMapper();
        List<Singer> singers=repo.findAll();

        List<SingerDTO> singerDTO = singers.stream()
                .map(singer -> modelMapper.map(singer, SingerDTO.class))
                .collect(Collectors.toList());



        return singerDTO.stream()
                .filter(sg -> sg.getFirstName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

}
