package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAllByIdIn(List<Long> clubIds);
}
