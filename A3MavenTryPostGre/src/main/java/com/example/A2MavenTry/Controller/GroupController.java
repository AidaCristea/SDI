package com.example.A2MavenTry.Controller;


import com.example.A2MavenTry.Exceptions.GroupNotFoundException;
import com.example.A2MavenTry.Model.*;
import com.example.A2MavenTry.Repository.GroupRepository;
import com.example.A2MavenTry.Service.GroupService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GroupController {
    /*@Autowired
    GroupRepository groupRepo;*/

    @Autowired
    GroupService groupService;


    /*public GroupController(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }*/

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /*@GetMapping("/groups")
    public List<GroupDTO> getAllGroups()
    {
        *//*ModelMapper modelMapper = new ModelMapper();

        return groupRepo.findAll().stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());*//*

        return this.groupService.getAllGroups();
    }*/

    @GetMapping("/groups/countAll")
     public Long countAllGroups()
    {
        return this.groupService.countAllGroups();
    }

    @GetMapping("/groups/page/{page}/size/{size}")
    public List<GroupDTO> getAllGroups(@PathVariable int page, @PathVariable int size)
    {
        PageRequest pr = PageRequest.of(page, size);
        return this.groupService.getAllGroups(pr);
    }




    @GetMapping("/groups/{id}")
    public GroupDTO getGroup(@PathVariable Integer id)
    {
        /*ModelMapper modelMapper = new ModelMapper();
        Group group=groupRepo.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
        GroupDTO groupDTO=modelMapper.map(group, GroupDTO.class);
        return groupDTO;*/

        return this.groupService.getGroup(id);


    }

    @PostMapping("/groups")
    void newGroup (@RequestBody Group newGr)
    {
        this.groupService.newGroup(newGr);
    }

    @PutMapping("/groups/{id}")
    void replaceGroup(@RequestBody Group newGr, @PathVariable Integer id)
    {
        this.groupService.replaceGroup(newGr, id);
    }

    @DeleteMapping("/groups/{id}")
    void deleteGroup(@PathVariable Integer id)
    {
        /*groupRepo.deleteById(id);*/
        this.groupService.deleteGroup(id);
    }

    @GetMapping("/groups/autocomplete")
    public List<GroupDTO> get(@RequestParam String query)
    {
        //List<Group> groups=groupService.getGroupsAutocomplete(query);
        //System.out.println(groups.get(1));
        return this.groupService.getGroupsAutocomplete(query);
    }

    /*@GetMapping("/average-nr-songs")
    public List<GroupDTOForAvg> groupsOrderedByAlbumNrOfSongs()
    {
        ModelMapper modelMapper=new ModelMapper();
        List<Group> groupList = groupRepo.findAll();

        groupList.sort(Comparator.comparing(Group::getAverageAlbumNrSongs)
                .reversed());
        List<GroupDTOForAvg> groupDTOForAvgList=new ArrayList<>();
        for(Group g: groupList)
        {
            if(!Double.isNaN(g.getAverageAlbumNrSongs()))
            {
                GroupDTOForAvg groupDTOForAvg = modelMapper.map(g, GroupDTOForAvg.class);
                groupDTOForAvg.setAvgAlbumsNrSongs(g.getAverageAlbumNrSongs());
                groupDTOForAvg.setId(g.getIdGroup());
                groupDTOForAvg.setMembers(g.getMembers());
                groupDTOForAvg.setDateFormed(g.getDateFormed());
                groupDTOForAvg.setReview(g.getReview());
                groupDTOForAvg.setNameGr(g.getNameGr());
                groupDTOForAvg.setMusicSpecialization(g.getMusicSpecialization());
                groupDTOForAvgList.add(groupDTOForAvg);
            }
        }
        return groupDTOForAvgList;
    }
*/
}
