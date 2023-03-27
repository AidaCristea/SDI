package com.example.A2MavenTry;

/*import com.example.A2MavenTry.Controller.Controller;
import com.example.A2MavenTry.Model.Albums;
import com.example.A2MavenTry.Model.Singer;
import com.example.A2MavenTry.Model.SingerDTOForAvg;
import com.example.A2MavenTry.Repository.AlbumsRepository;
import com.example.A2MavenTry.Repository.SingerRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;*/


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

import com.example.A2MavenTry.Controller.Controller;
import com.example.A2MavenTry.Model.Albums;
import com.example.A2MavenTry.Model.Singer;
import com.example.A2MavenTry.Model.SingerDTOForAvg;
import com.example.A2MavenTry.Repository.AlbumsRepository;
import com.example.A2MavenTry.Repository.SingerRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StatisticsSingerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private SingerRepository singerRepository;

    @MockBean
    private AlbumsRepository albumsRepository;

    @Test
    public void testSingersOrderedByAlbumNrOfSongs()
    {
        Albums al_1 = new Albums("album1", 2023, 6);
        Albums al_2 = new Albums("album2", 2022, 8);
        Singer sg_1 = new Singer("Adele", "Adkins", 31, "London", "Pop");
        sg_1.setAlbums(Arrays.asList(al_1, al_2));


        Albums al_3 = new Albums("album3", 2023, 10);
        Albums al_4 = new Albums("album4", 2022, 20);
        Singer sg_2 = new Singer("Ed", "Sheeran", 32, "Halifax", "Pop");
        sg_2.setAlbums(Arrays.asList(al_3, al_4));

        List<Singer> singers = Arrays.asList(sg_1, sg_2);

        when(singerRepository.findAll()).thenReturn(singers);

        ResponseEntity<List<SingerDTOForAvg>> response = restTemplate.exchange(
                "/average-nr-songs",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SingerDTOForAvg>>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<SingerDTOForAvg> result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(7.0, result.get(1).getAvgAlbumNrSongs(), 0.01);
        assertEquals(15.0, result.get(0).getAvgAlbumNrSongs(), 0.01);
        assertEquals("Ed", result.get(0).getFirstName());


    }
}
