package com.example.A2MavenTry.Controller;

import com.example.A2MavenTry.Exceptions.RecordLableNotFoundException;
import com.example.A2MavenTry.Model.RecordLable;
import com.example.A2MavenTry.Model.RecordLableDTO;
import com.example.A2MavenTry.Model.RecordLableDTOForAvg;
import com.example.A2MavenTry.Model.Singer;
import com.example.A2MavenTry.Repository.RecordLableRepository;

import com.example.A2MavenTry.Repository.SingerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

@RestController
public class RecordLablesController {

    @Autowired
    RecordLableRepository rLrepo;

    @Autowired
    SingerRepository sgrepo;



    public RecordLablesController(RecordLableRepository rLrepo, SingerRepository sgrepo) {
        this.rLrepo = rLrepo;
        this.sgrepo = sgrepo;
    }


    @GetMapping("/recordLbls")
    public List<RecordLableDTO> getAllRecLbls()
    {
        ModelMapper modelMapper = new ModelMapper();
        List<RecordLable> recordLables = rLrepo.findAll();
        List<RecordLableDTO> recordLableDTOS = recordLables.stream()
                .map(recordLable -> modelMapper.map(recordLable, RecordLableDTO.class))
                .collect(Collectors.toList());
        return recordLableDTOS;
    }



    @GetMapping("/recordLbls/{id}")
    public RecordLable getRecLblById(@PathVariable("id") Integer id)
    {
        return rLrepo.findById(id)
                .orElseThrow(()-> new RecordLableNotFoundException(id));
    }

    @PostMapping("/recordLbls")
    public void createRecordLbl(@RequestBody RecordLable reclbl) {
        ModelMapper modelMapper=new ModelMapper();
        List<RecordLable> recordLables = rLrepo.findAll();
        rLrepo.save(reclbl);
        List<RecordLableDTO> recordLableDTOS = recordLables.stream()
                .map(recordLable -> modelMapper.map(recordLable, RecordLableDTO.class))
                .collect(Collectors.toList());
        //return recordLableDTOS;
        //return rLrepo.save(reclbl);
    }

    @PostMapping("/recordLbls/{id}/singers")
    public Singer addSingerToRecordLbl(@PathVariable("id") int id, @RequestBody Singer singer) {
        //find record lable by id
        RecordLable rclbl = rLrepo.findById(id)
                .orElseThrow(() -> new RecordLableNotFoundException(id));

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
            Singer savedSng = sgrepo.save(singer);
            rclbl.getSingers().add(savedSng);
            rLrepo.save(rclbl);
            return savedSng;
        }

    }

    @PutMapping("/recordLbls/{id}")
    RecordLable replaceRecLbl(@RequestBody RecordLable newRecLbl, @PathVariable Integer id)
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

    @DeleteMapping("/recordLbls/{id}")
    public void deleteRecordLblById(@PathVariable("id") int id) {
        rLrepo.deleteById(id);

    }

    @GetMapping("/average-age")
    public List<RecordLableDTOForAvg> recordLableOrderBySingerAgeAvg()
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
