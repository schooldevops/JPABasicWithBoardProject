package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.ClubUsers;
import com.schooldevops.practical.simpleboard.entity.ClubUsersKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubUsersRepository extends JpaRepository<ClubUsers, ClubUsersKey> {

    List<ClubUsers> findAllByClubId(Long clubId);

    List<ClubUsers> findAllByUserId(String userId);
}
