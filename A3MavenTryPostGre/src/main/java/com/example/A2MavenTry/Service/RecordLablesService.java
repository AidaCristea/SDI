package com.example.A2MavenTry.Service;

import com.example.A2MavenTry.Exceptions.RecordLableNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecordLablesService {
    @Autowired
    RecordLableRepository rLrepo;

    @Autowired
    SingerRepository sgrepo;



    public RecordLablesService(RecordLableRepository rLrepo, SingerRepository sgrepo) {
        this.rLrepo = rLrepo;
        this.sgrepo = sgrepo;
    }


    //@GetMapping("/recordLbls")
    /*public List<RecordLableDTO> getAllRecLbls()
    {
        ModelMapper modelMapper = new ModelMapper();
        List<RecordLable> recordLables = rLrepo.findAll();
        List<RecordLableDTO> recordLableDTOS = recordLables.stream()
                .map(recordLable -> modelMapper.map(recordLable, RecordLableDTO.class))
                .collect(Collectors.toList());
        return recordLableDTOS;
    }*/

    public Long countAllRecordLbls()
    {
        return rLrepo.count();
    }

    public List<RecordLableDTO> getAllRecLbls(PageRequest pr)
    {
        ModelMapper modelMapper = new ModelMapper();
        //List<RecordLable> recordLables = rLrepo.findAll();
        Page<RecordLable> recordLables = rLrepo.findAll(pr);
        List<RecordLableDTO> recordLableDTOS = recordLables.stream()
                .map(recordLable -> modelMapper.map(recordLable, RecordLableDTO.class))
                .collect(Collectors.toList());
        return recordLableDTOS;
    }


    //@GetMapping("/recordLbls/{id}")
    public RecordLable getRecLblById( Integer id)
    {
        if(rLrepo.findById(id).isEmpty())
            throw new RecordLableNotFoundException(id);
        RecordLable recordLable=rLrepo.findById(id).get();
        return recordLable;

    }


    //more items
    /*@GetMapping("/recordLbls/{id}")
    public RecordLableDTOWithSingerId getRecLblById(@PathVariable("id") Integer id)
    {

        if(rLrepo.findById(id).isEmpty())
            throw new RecordLableNotFoundException(id);

        RecordLable recordLable=rLrepo.findById(id).get();
        RecordLableDTOWithSingerId recordLableDTOWithSingerId = new RecordLableDTOWithSingerId();

        List<Integer> singersId = new ArrayList<>();
        List<Singer> singers = sgrepo.findAll();

        for(Singer sg:singers)
        {
            if(sg.getRecordLable().getIdRecLbl() == recordLable.getIdRecLbl())
            {
                singersId.add(sg.getIdSinger());
            }
        }
        recordLableDTOWithSingerId.setSingersId(singersId);
        recordLableDTOWithSingerId.setRecordLable(recordLable);

        return recordLableDTOWithSingerId;
*/

        /*ModelMapper modelMapper = new ModelMapper();
        RecordLable recordLable=rLrepo.findById(id)
                .orElseThrow(() -> new RecordLableNotFoundException(id));
        RecordLableDTO recordLableDTO = modelMapper.map(recordLable, RecordLableDTO.class);

        return recordLableDTO;*/
    //}


    //bulk function
    //@PostMapping("/recordLbls/{id}/singers")
    public List<Singer> addMoreSingers( List<Singer> singerList,  Integer id)
    {
        RecordLable recordLable = rLrepo.findById(id).get();
        List<Singer> singersfinalList = new ArrayList<>();
        for(Singer sg : singerList)
        {
            Singer newSg = new Singer();
            newSg.setFirstName(sg.getFirstName());
            newSg.setLastName(sg.getLastName());
            newSg.setIdSinger(sg.getIdSinger());
            newSg.setCity(sg.getCity());
            newSg.setTypeOfMusic(sg.getTypeOfMusic());
            newSg.setAge(sg.getAge());
            newSg.setRecordLable(recordLable);
            if(sg.getAlbums()==null)
                sg.setAlbums(new ArrayList<>());

            newSg.setAlbums(sg.getAlbums());
            newSg=sgrepo.save(newSg);
            recordLable.getSingers().add(newSg);

            singersfinalList.add(newSg);

        }
        rLrepo.save(recordLable);
        return singersfinalList;

    }



    //@PostMapping("/recordLbls")
    public void createRecordLbl( RecordLable reclbl) {
        ModelMapper modelMapper=new ModelMapper();
        List<RecordLable> recordLables = rLrepo.findAll();
        rLrepo.save(reclbl);
        List<RecordLableDTO> recordLableDTOS = recordLables.stream()
                .map(recordLable -> modelMapper.map(recordLable, RecordLableDTO.class))
                .collect(Collectors.toList());

        //return recordLableDTOS;
        //return rLrepo.save(reclbl);
    }


    //@PostMapping("/recordLbls/{id}/singers")



    //@PostMapping("/recordLbls/{id}/singer")
    public Singer addSingerToRecordLbl( String id,  Singer singer) {

        Integer rec_id = Integer.parseInt(id);
        //find record lable by id
        RecordLable rclbl = rLrepo.findById(rec_id)
                .orElseThrow(() -> new RecordLableNotFoundException(rec_id));

        //check if singer with given id already exists
        Singer existSng = null;
        for (Singer sg : sgrepo.findAll())
        {
            if (sg.equals(singer))
            {
                existSng=sg;
                break;
            }
        }

        if (existSng !=null)
        {
            //if the singer already exists, set the record prop and return the singer
            existSng.setRecordLable(rclbl);
            sgrepo.save(existSng);
            return existSng;
        }
        else
        {
            //the singer doesn't exists, set the record prop for new singer
            singer.setRecordLable(rclbl);
            List<Albums> list = new ArrayList<>();
            singer.setAlbums(list);
            Singer savedSng = sgrepo.save(singer);
            rclbl.getSingers().add(savedSng);
            rLrepo.save(rclbl);
            return savedSng;
        }

    }

    //@PutMapping("/recordLbls/{id}")
    public RecordLable replaceRecLbl(RecordLable newRecLbl, Integer id)
    {
        return rLrepo.findById(id)
                .map(rclbl -> {
                    rclbl.setAddress(newRecLbl.getAddress());
                    rclbl.setNameRl(newRecLbl.getNameRl());
                    rclbl.setPrice(newRecLbl.getPrice());
                    rclbl.setReview(newRecLbl.getReview());
                    rclbl.setNrCollaborations(newRecLbl.getNrCollaborations());
                    return rLrepo.save(rclbl);
                })
                .orElseGet(() -> {
                    newRecLbl.setIdRecLbl(id);
                    return rLrepo.save(newRecLbl);
                });
    }

    //@DeleteMapping("/recordLbls/{id}")
    public void deleteRecordLblById( String id) {
        Integer rec_id = Integer.parseInt(id);
        rLrepo.deleteById(rec_id);

    }

    //@GetMapping("/average-age")
    public List<RecordLableDTOForAvg> recordLableOrderBySingerAgeAvg(PageRequest pr)
    {
        ModelMapper modelMapper = new ModelMapper();
        List<RecordLable> recordLableList = rLrepo.findAll();

        recordLableList.sort(Comparator.comparingDouble(RecordLable::getAverageSingerAge)
                .reversed());
        //Collections.sort(recordLableList, Collections.reverseOrder());
        List<RecordLableDTOForAvg> recordLableDTOForAvgList = new ArrayList<>();
        for (RecordLable rl : recordLableList)
        {
            if(!Double.isNaN(rl.getAverageSingerAge()))
            {
                RecordLableDTOForAvg recordLableDTOForAvg = modelMapper.map(rl, RecordLableDTOForAvg.class);
                recordLableDTOForAvg.setAvgSingerAge(rl.getAverageSingerAge());
                recordLableDTOForAvg.setPrice(rl.getPrice());
                recordLableDTOForAvg.setAddress(rl.getAddress());
                recordLableDTOForAvg.setReview(rl.getReview());
                recordLableDTOForAvg.setId(rl.getIdRecLbl());
                recordLableDTOForAvg.setNameRl(rl.getNameRl());
                recordLableDTOForAvg.setNrCollaborations(rl.getNrCollaborations());
                recordLableDTOForAvgList.add(recordLableDTOForAvg);
            }
        }

        return recordLableDTOForAvgList;

    }

}
