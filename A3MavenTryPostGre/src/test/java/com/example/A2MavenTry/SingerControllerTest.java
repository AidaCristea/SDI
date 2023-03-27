
package com.example.A2MavenTry;


/*import com.example.A2MavenTry.Controller.Controller;
import com.example.A2MavenTry.Controller.RecordLablesController;
import com.example.A2MavenTry.Model.RecordLable;
import com.example.A2MavenTry.Model.Singer;
import com.example.A2MavenTry.Model.SingerDTOWithId;
import com.example.A2MavenTry.Repository.RecordLableRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;*/


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import com.example.A2MavenTry.Controller.Controller;

import com.example.A2MavenTry.Model.Singer;
;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class SingerControllerTest {
    @Mock
    private SingerRepository singerRepository;

   /* @Mock
    private RecordLableRepository recordLableRepository;*/

    /*@InjectMocks
    private RecordLablesController recordLablesController;*/
    @InjectMocks
    private Controller singerController;

    /*@Before
    public void setUp()
    {
        singerController = new Controller(singerRepository);
    }

*/
    @Test
    public void testFindByAgeGreaterThanEqual() throws Exception
    {
        Singer sg_1 = new Singer("Adele", "Adkins", 31, "London", "Pop");
        Singer sg_2 = new Singer("Ed", "Sheeran", 32, "Halifax", "Pop");
        List<Singer> singers = Arrays.asList(sg_1, sg_2);
        List<Singer> singers2 = Arrays.asList(sg_2);

        when(singerRepository.findByAgeGreaterThanEqual(30)).thenReturn(singers);

        MockMvc mockMvc = standaloneSetup(singerController).build();
        mockMvc.perform(get("/singers/greaterThan/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Adele"))
                .andExpect(jsonPath("$[0].age").value(31))
                .andExpect(jsonPath("$[1].firstName").value("Ed"))
                .andExpect(jsonPath("$[1].age").value(32));



        when(singerRepository.findByAgeGreaterThanEqual(32)).thenReturn(singers2);
        MockMvc mockMvc2 = standaloneSetup(singerController).build();
        mockMvc2.perform(get("/singers/greaterThan/32"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ed"))
                .andExpect(jsonPath("$[0].age").value(32));



        /*Singer sg_1 = new Singer("Adele", "Adkins", 31, "London", "Pop");
        //sg_1.setIdSinger((Integer)1);
        Singer sg_2 = new Singer("Ed", "Sheeran", 32, "Halifax", "Pop");
        //sg_2.setIdSinger((Integer)2);

        List<Singer> singers = Arrays.asList(sg_1, sg_2);
        List<Singer> singers2 = Arrays.asList(sg_2);

        when(singerRepository.findByAgeGreaterThanEqual(30)).thenReturn(singers);

        when(singerRepository.findByAgeGreaterThanEqual(32)).thenReturn(singers2);
        List<SingerDTOWithId> result1 = singerController.findByAgeGreaterThanEqual(30);
        assertEquals(2, result1.size());
        assertEquals("Adele", result1.get(0).getFirstName());
        assertEquals("Ed", result1.get(1).getFirstName());

        List<SingerDTOWithId> result2 = singerController.findByAgeGreaterThanEqual(32);
        assertEquals(1, result2.size());
        assertEquals("Sheeran", result2.get(0).getLastName());*/

    }








}

