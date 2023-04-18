package com.example.A2MavenTry.Service;

import com.example.A2MavenTry.Exceptions.GroupNotFoundException;
import com.example.A2MavenTry.Model.Group;
import com.example.A2MavenTry.Model.GroupDTO;
import com.example.A2MavenTry.Repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepo;

    public GroupService(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    //@GetMapping("/groups")
    public List<GroupDTO> getAllGroups()
    {
        ModelMapper modelMapper = new ModelMapper();

        return groupRepo.findAll().stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

    //@GetMapping("/groups/{id}")
    public GroupDTO getGroup(Integer id)
    {
        ModelMapper modelMapper = new ModelMapper();
        Group group=groupRepo.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
        GroupDTO groupDTO=modelMapper.map(group, GroupDTO.class);
        return groupDTO;


    }

    //@PostMapping("/groups")
    public Group newGroup ( Group newGr)
    {
        return groupRepo.save(newGr);
    }

    //@PutMapping("/groups/{id}")
    public Group replaceGroup( Group newGr,  Integer id)
    {
        return groupRepo.findById(id)
                .map(group -> {
                    group.setMembers(newGr.getMembers());
                    group.setDateFormed(newGr.getDateFormed());
                    group.setNameGr(newGr.getNameGr());
                    group.setMusicSpecialization(newGr.getMusicSpecialization());
                    group.setReview(newGr.getReview());
                    group.setDescription(newGr.getDescription());
                    return groupRepo.save(group);
                })
                .orElseGet(() -> {
                    newGr.setIdGroup(id);
                    return groupRepo.save(newGr);
                });
    }

    //@DeleteMapping("/groups/{id}")
    public void deleteGroup( Integer id)
    {
        groupRepo.deleteById(id);
    }

}
