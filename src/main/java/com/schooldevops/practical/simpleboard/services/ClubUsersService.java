package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.constants.ClubUserLevel;
import com.schooldevops.practical.simpleboard.constants.ClubUserStatus;
import com.schooldevops.practical.simpleboard.dto.ClubUsersDto;
import com.schooldevops.practical.simpleboard.entity.Club;
import com.schooldevops.practical.simpleboard.entity.ClubUsers;
import com.schooldevops.practical.simpleboard.entity.ClubUsersKey;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.repository.ClubRepository;
import com.schooldevops.practical.simpleboard.repository.ClubUsersRepository;
import com.schooldevops.practical.simpleboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClubUsersService {

    private final ClubUsersRepository clubUsersRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClubUsersService(ClubUsersRepository clubUsersRepository, ClubRepository clubRepository, UserRepository userRepository) {
        this.clubUsersRepository = clubUsersRepository;
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    public ClubUsersDto saveClubUsers(ClubUsersDto clubUsersDto) {
        ClubUsers entity = clubUsersDto.getEntity();

        if (clubUsersDto.getClub() == null || clubUsersDto.getClub().getId() == null) {
            throw new IllegalArgumentException("There are not clubid in clubDTO");
        }

        if (clubUsersDto.getUser() == null || clubUsersDto.getUser().getId() == null) {
            throw new IllegalArgumentException("There are not userid in clubDTO");
        }

        ClubUsersKey key = ClubUsersKey.builder().clubId(clubUsersDto.getClub().getId()).userId(clubUsersDto.getUser().getId()).build();
        entity.setId(key);

        Optional<Club> clubEntity = clubRepository.findById(clubUsersDto.getClub().getId());
        Club club = clubEntity.orElseThrow(() -> new RuntimeException("There are any club by clubId: " + clubUsersDto.getClub().getId()));

        Optional<User> userEntity = userRepository.findById(clubUsersDto.getUser().getId());
        User user = userEntity.orElseThrow(() -> new RuntimeException("There are any user by userId: " + clubUsersDto.getUser().getId()));

        entity.setClub(club);
        entity.setUser(user);

        ClubUsers savedClubUser = clubUsersRepository.save(entity);

        return savedClubUser.getDTO();
    }

    public ClubUsersDto findById(Long clubId, String userId) {
        ClubUsersKey key = ClubUsersKey.builder()
                .clubId(clubId)
                .userId(userId)
                .build();
        Optional<ClubUsers> clubUsersOption = clubUsersRepository.findById(key);
        ClubUsers clubUsers = clubUsersOption.orElseThrow(() -> new RuntimeException("There are any data by key " + key));
        return clubUsers.getDTO();
    }

    public ClubUsersDto deleteById(Long clubId, String userId) {
        ClubUsersKey key = ClubUsersKey.builder()
                .clubId(clubId)
                .userId(userId)
                .build();
        Optional<ClubUsers> clubUsersOption = clubUsersRepository.findById(key);
        ClubUsers clubUsers = clubUsersOption.orElseThrow(() -> new RuntimeException("There are any data by key " + key));

        clubUsersRepository.deleteById(key);

        return clubUsers.getDTO();
    }

    public List<String>  bindUsersToClub(Long clubId, List<String> userIds) {
        Optional<Club> clubOptoin = clubRepository.findById(clubId);
        Club club = clubOptoin.orElseThrow(() -> new RuntimeException("There are any club by clubId: " + clubId));

        List<ClubUsers> allByClubId = clubUsersRepository.findAllByClubId(clubId);

        if (clubId == null || userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("Illegal Argument for binding club to users " + clubId + " : " + userIds);
        }
        if (allByClubId == null || allByClubId.isEmpty()) {
            throw new RuntimeException("There are any data by clubId : " + clubId);
        }

        Set<String> findUserIds = allByClubId.stream().map(clubUser -> clubUser.getUser().getId()).collect(Collectors.toSet());

        List<String> notBindedUserIds = userIds.stream().filter(userId -> !findUserIds.contains(userId)).collect(Collectors.toList());
        if (notBindedUserIds == null || notBindedUserIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<User> allUserByUserIds = userRepository.findAllByIdIn(notBindedUserIds);

        if (allUserByUserIds == null || allUserByUserIds.isEmpty()) {
            return new ArrayList<>();
        }

        allUserByUserIds.stream().forEach(user -> {
            ClubUsersKey key = ClubUsersKey.builder().clubId(club.getId()).userId(user.getId()).build();
            ClubUsers clubUsers = ClubUsers.builder()
                    .id(key)
                    .club(club)
                    .user(user)
                    .score(0)
                    .clubUserLevel(ClubUserLevel.BABY)
                    .clubUserStatus(ClubUserStatus.OPEN)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .build();

            clubUsersRepository.save(clubUsers);
        });

        return notBindedUserIds;
    }

    public List<Long> bindClubsToUser(String userId, List<Long> clubIds) {

        Optional<User> userOption = userRepository.findById(userId);
        User user = userOption.orElseThrow(() -> new RuntimeException("There are any user by userId: " + userId));

        List<ClubUsers> allByUserId = clubUsersRepository.findAllByUserId(userId);

        if (userId == null || clubIds == null || clubIds.isEmpty()) {
            throw new IllegalArgumentException("Illegal Argument for binding club to users " + userId + " : " + clubIds);
        }

        if (allByUserId == null || allByUserId.isEmpty()) {
            throw new RuntimeException("There are any data by userId : " + userId);
        }

        Set<Long> findClubId = allByUserId.stream().map(clubUser -> clubUser.getClub().getId()).collect(Collectors.toSet());

        List<Long> notBindedClubIds = clubIds.stream().filter(clubId -> !findClubId.contains(clubId)).collect(Collectors.toList());

        if (notBindedClubIds == null || notBindedClubIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Club> allClubByClubIds = clubRepository.findAllByIdIn(notBindedClubIds);

        if (allClubByClubIds == null || allClubByClubIds.isEmpty()) {
            return new ArrayList<>();
        }

        allClubByClubIds.stream().forEach(club -> {
            ClubUsersKey key = ClubUsersKey.builder().clubId(club.getId()).userId(user.getId()).build();
            ClubUsers clubUsers = ClubUsers.builder()
                    .id(key)
                    .club(club)
                    .user(user)
                    .score(0)
                    .clubUserLevel(ClubUserLevel.BABY)
                    .clubUserStatus(ClubUserStatus.OPEN)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .build();

            clubUsersRepository.save(clubUsers);
        });

        return notBindedClubIds;
    }
}
