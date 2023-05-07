package com.example.A2MavenTry.Repository;

import com.example.A2MavenTry.Model.RecordLable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
@Component
public interface RecordLableRepository extends JpaRepository<RecordLable, Integer> {
    //List<Object[]> findAllOrderByAvgSingersAge();
    }
