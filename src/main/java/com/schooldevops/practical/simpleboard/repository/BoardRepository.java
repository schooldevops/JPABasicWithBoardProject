package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
