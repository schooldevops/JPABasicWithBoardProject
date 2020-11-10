package com.schooldevops.practical.simpleboard.controller;

import com.schooldevops.practical.simpleboard.dto.ClubDto;
import com.schooldevops.practical.simpleboard.dto.UserDetailDto;
import com.schooldevops.practical.simpleboard.dto.UserDto;
import com.schooldevops.practical.simpleboard.entity.UserDetail;
import com.schooldevops.practical.simpleboard.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/clubs")
@RestController
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping
    public ClubDto saveUser(@RequestBody ClubDto club) {
        return clubService.saveClub(club);
    }

    @GetMapping("/{clubId}")
    public ClubDto findByUserId(@PathVariable("clubId") Long clubId) {
        return clubService.getClubById(clubId);
    }

    @PutMapping("/{clubId}")
    public ClubDto updateByClubId(@PathVariable("clubId") Long clubId, @RequestBody ClubDto clubDto) {
        if (clubId == null) { throw new IllegalArgumentException(); }
        return clubService.updateClub(clubId, clubDto);
    }

    @DeleteMapping("/{clubId}")
    public ClubDto deleteClubById(@PathVariable("clubId") Long clubId) {
        return clubService.deleteClubById(clubId);
    }

}
