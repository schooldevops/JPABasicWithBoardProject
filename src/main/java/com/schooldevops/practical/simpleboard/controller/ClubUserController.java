package com.schooldevops.practical.simpleboard.controller;

import com.schooldevops.practical.simpleboard.dto.ClubUsersDto;
import com.schooldevops.practical.simpleboard.services.ClubUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/clubusers")
@RestController
public class ClubUserController {

    private final ClubUsersService clubUsersService;

    @Autowired
    public ClubUserController(ClubUsersService clubUsersService) {
        this.clubUsersService = clubUsersService;
    }

    @PostMapping
    public ClubUsersDto saveUser(@RequestBody ClubUsersDto clubUsersDto) {
        return clubUsersService.saveClubUsers(clubUsersDto);
    }

    @GetMapping("/{clubId}/{userId}")
    public ClubUsersDto findById(@PathVariable("clubId") Long clubId, @PathVariable("userId") String userId) {
        return clubUsersService.findById(clubId, userId);
    }

    @DeleteMapping("/{clubId}/{userId}")
    public ClubUsersDto deleteById(@PathVariable("clubId") Long clubId, @PathVariable("userId") String userId) {
        return clubUsersService.deleteById(clubId, userId);
    }

    @PutMapping("/clubs/{clubId}/users")
    public List<String> bindClubToUsers(@PathVariable("clubId") Long clubId, @RequestBody List<String> userIds) {
        return clubUsersService.bindUsersToClub(clubId, userIds);
    }

    @PutMapping("/users/{userId}/groups")
    public List<Long> bindClubToUsers(@PathVariable("userId") String userId, @RequestBody List<Long> clubIds) {
        return clubUsersService.bindClubsToUser(userId, clubIds);
    }
}
