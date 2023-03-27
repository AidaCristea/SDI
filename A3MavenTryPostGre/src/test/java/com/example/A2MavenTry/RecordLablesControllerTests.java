package com.example.A2MavenTry;

/*

import com.example.A2MavenTry.Controller.Controller;
import com.example.A2MavenTry.Controller.RecordLablesController;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

*/


/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.A2MavenTry.Controller.Controller;
import com.example.A2MavenTry.Controller.RecordLablesController;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;*/

import com.example.A2MavenTry.Controller.Controller;
import com.example.A2MavenTry.Controller.RecordLablesController;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

//@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecordLablesControllerTests {


    /*@InjectMocks
    private RecordLablesController recordLablesController;*/


    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RecordLableRepository recordLableRepository;


    @MockBean
    private SingerRepository singerRepository;


    @Test
    public void testRecordLblsControllerSortRecordLblsByAveAgeSinger()
    {

        Singer sg_1 = new Singer("Adele", "Adkins", 31, "London", "Pop");
        Singer sg_2 = new Singer("Ed", "Sheeran", 32, "Halifax", "Pop");
        Singer sg_5 = new Singer("Elton", "John", 74, "London", "Pop");
        RecordLable rl1 = new RecordLable("RecLbl1", "address1", 2300, "Good", 5);
        rl1.setSingers(Arrays.asList(sg_1, sg_2, sg_5));

        Singer sg_3 = new Singer("Taylor", "Swift", 35, "Nashville", "Pop");
        Singer sg_4 = new Singer("John", "Lennon", 40, "Liverpool", "Rock");
        RecordLable rl2 = new RecordLable("RecLbl2", "address2", 2000, "Good", 9);
        rl2.setSingers(Arrays.asList(sg_3, sg_4));

        List<RecordLable> records =Arrays.asList(rl1, rl2);

        when(recordLableRepository.findAll()).thenReturn(records);

        ResponseEntity<List<RecordLableDTOForAvg>> response = restTemplate.exchange(
                "/average-age",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecordLableDTOForAvg>>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<RecordLableDTOForAvg> result = response.getBody();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(45.66, result.get(0).getAvgSingerAge(), 0.01);
        assertEquals(37.50, result.get(1).getAvgSingerAge(), 0.01);
        assertEquals("RecLbl2", result.get(1).getNameRl());




/*

        RecordLable rl1 = new RecordLable("RecLbl1", "address1", 2300, "Good", 5);
        Singer sg_1 = new Singer("Adele", "Adkins", 31, "London", "Pop");
        Singer sg_2 = new Singer("Ed", "Sheeran", 32, "Halifax", "Pop");
        Singer sg_5 = new Singer("Elton", "John", 74, "London", "Pop");


        if(rl1.getSingers()==null)
            rl1.setSingers(new ArrayList<>());


        rl1.getSingers().add(sg_1);
        rl1.getSingers().add(sg_2);
        rl1.getSingers().add(sg_5);

        RecordLable rl2 = new RecordLable("RecLbl2", "address2", 2000, "Good", 9);
        Singer sg_3 = new Singer("Taylor", "Swift", 35, "Nashville", "Pop");
        Singer sg_4 = new Singer("John", "Lennon", 40, "Liverpool", "Rock");


        if(rl2.getSingers()==null)
            rl2.setSingers(new ArrayList<>());

        rl2.getSingers().add(sg_3);
        rl2.getSingers().add(sg_4);


        when(recordLableRepository.findAll()).thenReturn(Arrays.asList(rl1, rl2));


        List<RecordLableDTOForAvg> result = recordLablesController.recordLableOrderBySingerAgeAvg();


        assertEquals(2, result.size());
        assertEquals(45.66, result.get(0).getAvgSingerAge(), 0.01);
        assertEquals(37.50, result.get(1).getAvgSingerAge(), 0.01);
        assertEquals("RecLbl2", result.get(1).getNameRl());

*/


    }

}
