package com.example.A2MavenTry.Repository;

import com.example.A2MavenTry.Model.RecordLable;
import com.example.A2MavenTry.Model.Singer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface SingerRepository extends JpaRepository<Singer, Integer> {
    //returns all Singers of a RecordLable specified by rcId
    //List<Singer> findByrecordLableId(Integer postId);

    //@Transactional
    //void deleteByrecordLableId(Integer recId);

    List<Singer> findByAgeGreaterThanEqual(Integer givenAge);


}