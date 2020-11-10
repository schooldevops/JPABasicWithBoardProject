package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.dto.ClubDto;
import com.schooldevops.practical.simpleboard.entity.Club;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.repository.ClubRepository;
import com.schooldevops.practical.simpleboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ClubDto saveClub(ClubDto clubDto) {
        Club entity = clubDto.getEntity();
        Club savedClub = clubRepository.save(entity);

        return savedClub.getDTO();
    }

    @Transactional
    public ClubDto updateClub(Long clubId, ClubDto clubDto) {
        Optional<Club> findClubOption = clubRepository.findById(clubId);
        Club findClub = findClubOption.orElseThrow(() -> new RuntimeException("There are any club by clubId: " + clubId));

        if (clubDto.getName() != null) {
            findClub.setName(clubDto.getName());
        }

        if (clubDto.getClubLevel() != null) {
            findClub.setClubLevel(clubDto.getClubLevel());
        }

        Club savedClub = clubRepository.save(findClub);
        return savedClub.getDTO();
    }

    @Transactional
    public ClubDto getClubById(Long clubId) {
        Optional<Club> findClubOption = clubRepository.findById(clubId);
        Club findClub = findClubOption.orElseThrow(() -> new RuntimeException("There are any club by clubId: " + clubId));

        return findClub.getDTO();
    }

    @Transactional
    public ClubDto deleteClubById(Long clubId) {
        Optional<Club> findClubOption = clubRepository.findById(clubId);
        Club findClub = findClubOption.orElseThrow(() -> new RuntimeException("There are any club by clubId: " + clubId));

        clubRepository.deleteById(clubId);

        return findClub.getDTO();
    }
}
