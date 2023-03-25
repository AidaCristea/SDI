package com.example.A2MavenTry.Repository;

import com.example.A2MavenTry.Model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
