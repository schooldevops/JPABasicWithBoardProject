package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.dto.BoardDto;
import com.schooldevops.practical.simpleboard.entity.Board;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.repository.BoardRepository;
import com.schooldevops.practical.simpleboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BoardService {

    final BoardRepository boardRepository;
    final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public BoardDto saveBoard(BoardDto boardDto) {

        Optional<User> user = userRepository.findById(boardDto.getUser().getId());
        User userEntity = user.orElseThrow(() -> new RuntimeException("Not valid user for write board content."));

        Board entity = boardDto.getEntity();
        entity.setUser(userEntity);
        entity.setReadCount(0);
        entity.setGoodCount(0);
        entity.setBadCount(0);
        entity.setCreatedAt(LocalDateTime.now());

        Board savedBoard = boardRepository.save(entity);
        return savedBoard.getDTO();
    }

    public BoardDto findBoardById(Long boardId) {

        Optional<Board> boardOption = boardRepository.findById(boardId);
        Board one = boardOption.orElseThrow(() -> new RuntimeException("Ther are any contents by id"));

        one.setReadCount(one.getReadCount() + 1);

        Board savedBoard = boardRepository.save(one);
        BoardDto dto = savedBoard.getDTO();
        return dto;
    }

    public BoardDto modifyBoard(Long boardId, BoardDto boardDto) {
        Optional<Board> boardOption = boardRepository.findById(boardId);
        Board board = boardOption.orElseThrow(() -> new RuntimeException("Ther are any contents by id"));

        if (boardDto.getCategory() != null) {
            board.setCategory(boardDto.getCategory());
        }
        if (boardDto.getTitle() != null) {
            board.setTitle(boardDto.getTitle());
        }
        if (boardDto.getContents() != null) {
            board.setContents(boardDto.getContents());
        }
        if (boardDto.getStatus() != null) {
            board.setStatus(boardDto.getStatus());
        }

        board.setModifiedAt(LocalDateTime.now());

        Board savedBoard = boardRepository.save(board);

        return savedBoard.getDTO();
    }

    public BoardDto deleteBoard(Long boardId) {
        Optional<Board> boardOption = boardRepository.findById(boardId);
        Board board = boardOption.orElseThrow(() -> new RuntimeException("Ther are any contents by id"));

        boardRepository.delete(board);
        return board.getDTO();
    }
}
