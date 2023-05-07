package com.example.A2MavenTry.Service;

import com.example.A2MavenTry.Exceptions.GroupNotFoundException;
import com.example.A2MavenTry.Model.Group;
import com.example.A2MavenTry.Model.GroupDTO;
import com.example.A2MavenTry.Repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    public Long countAllGroups()
    {
        return this.groupRepo.count();
    }

    //@GetMapping("/groups")
    public List<GroupDTO> getAllGroups(PageRequest pr)
    {
        ModelMapper modelMapper = new ModelMapper();
        Page<Group> groups = groupRepo.findAll(pr);
        List<GroupDTO> groupDTOS= groups.stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());

       return groupDTOS;

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
    public void newGroup ( Group newGr)
    {
         groupRepo.save(newGr);
    }

    //@PutMapping("/groups/{id}")
    public void replaceGroup( Group newGr,  Integer id)
    {
        //System.out.println(newGr);
        groupRepo.findById(id)
                .map(group -> {
                    group.setMembers(newGr.getMembers());
                    group.setDateFormed(newGr.getDateFormed());
                    group.setNameGr(newGr.getNameGr());
                    group.setMusicSpecialization(newGr.getMusicSpecialization());
                    group.setReview(newGr.getReview());
                    group.setDescription(newGr.getDescription());
                    //System.out.println(group);
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


    public List<GroupDTO> getGroupsAutocomplete(String query)
    {
        ModelMapper modelMapper=new ModelMapper();
        List<Group> groups=groupRepo.findAll();

        List<GroupDTO> groupsDTO= groups.stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());

        return groupsDTO.stream()
                .filter(gr -> gr.getNameGr().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

}
