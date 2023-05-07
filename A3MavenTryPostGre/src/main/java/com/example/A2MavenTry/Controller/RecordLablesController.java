package com.example.A2MavenTry.Controller;

import com.example.A2MavenTry.Exceptions.GroupNotFoundException;
import com.example.A2MavenTry.Exceptions.RecordLableNotFoundException;
import com.example.A2MavenTry.Exceptions.SingerNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;

import com.example.A2MavenTry.Repository.SingerRepository;
import com.example.A2MavenTry.Service.RecordLablesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import javax.management.ObjectName;
import java.util.*;

import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
//added values

@RestController

public class RecordLablesController {


    @Autowired
    RecordLablesService recordLablesService;



    public RecordLablesController(RecordLablesService recordLablesService) {
        this.recordLablesService = recordLablesService;
    }

    /*@GetMapping("/recordLbls")
    public List<RecordLableDTO> getAllRecLbls()
    {
        *//*ModelMapper modelMapper = new ModelMapper();
        List<RecordLable> recordLables = rLrepo.findAll();
        List<RecordLableDTO> recordLableDTOS = recordLables.stream()
                .map(recordLable -> modelMapper.map(recordLable, RecordLableDTO.class))
                .collect(Collectors.toList());
        return recordLableDTOS;*//*

        return this.recordLablesService.getAllRecLbls();
    }
*/

    @GetMapping("/recordLbls/countAll")
    public Long countAllRecordLbls()
    {
        return this.recordLablesService.countAllRecordLbls();
    }

    @GetMapping("/recordLbls/page/{page}/size/{size}")
    public List<RecordLableDTO> getAllRecLbls(@PathVariable int page, @PathVariable int size)
    {

        //e ok, dar eventual sa imi fac o clasa ajutatoare care imi returneaza lista cu toate datele plus numarul de pagini de care am nevoie, si atunci in front end nu mai apelez 2 endointuri


        PageRequest pr = PageRequest.of(page, size);
        List<RecordLableDTO> recordLableDTOS = this.recordLablesService.getAllRecLbls(pr);
        recordLableDTOS.sort(Comparator.comparing(RecordLableDTO::getNameRl));
        return recordLableDTOS;
        //return this.recordLablesService.getAllRecLbls(pr);
    }

    @GetMapping("/recordLbls/{id}")
    public RecordLableDTO getRecLblById(@PathVariable("id") String id)
    {
        return this.recordLablesService.getRecLblById(id);
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
    @PostMapping("/recordLbls/{id}/singers")
    public List<Singer> addMoreSingers(@RequestBody List<Singer> singerList, @PathVariable Integer id)
    {
        return this.recordLablesService.addMoreSingers(singerList, id);

    }


    @PostMapping("/recordLbls")
    public void createRecordLbl(@Valid @RequestBody RecordLable reclbl) {
        this.recordLablesService.createRecordLbl(reclbl);
    }


    /*@PostMapping("/recordLbls/{id}/singer")
    public Singer addSingerToRecordLbl(@PathVariable("id") String id, @RequestBody Singer singer) {

        return this.recordLablesService.addSingerToRecordLbl(id, singer);

    }*/

    @PutMapping("/recordLbls/{id}")
    RecordLable replaceRecLbl(@RequestBody RecordLable newRecLbl, @PathVariable Integer id)
    {
        return this.recordLablesService.replaceRecLbl(newRecLbl, id);
    }

    @DeleteMapping("/recordLbls/{id}")
    public void deleteRecordLblById(@PathVariable("id") String id) {
        /*Integer rec_id = Integer.parseInt(id);
        rLrepo.deleteById(rec_id);*/
        this.recordLablesService.deleteRecordLblById(id);

    }

    @GetMapping("/average-age/page/{page}/size/{size}")
    public List<RecordLableDTOForAvg> recordLableOrderBySingerAgeAvg(@PathVariable int page, @PathVariable int size)
    {

        PageRequest pr = PageRequest.of(page, size);
        return this.recordLablesService.recordLableOrderBySingerAgeAvg(pr);

    }

    /*@GetMapping("/recordLbls/autocomplete")
    public ResponseEntity<ObjectNode> get(@RequestParam String query)
    {
        ObjectMapper mapper = new ObjectMapper();
        List<RecordLable> recordLables = this.recordLablesService.getRecordsAutocomplete(query);
        ObjectNode response = mapper.createObjectNode();
        response.putPOJO("recordLables", recordLables);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

    @GetMapping("/recordLbls/autocomplete")
    public List<RecordLable> get(@RequestParam String query)
    {
        return this.recordLablesService.getRecordsAutocomplete(query);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName =((FieldError) error).getField();
            String errorMessage =error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }






}
