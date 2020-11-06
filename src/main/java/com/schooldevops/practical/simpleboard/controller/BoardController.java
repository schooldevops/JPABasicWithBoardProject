package com.schooldevops.practical.simpleboard.controller;

import com.schooldevops.practical.simpleboard.dto.BoardDto;
import com.schooldevops.practical.simpleboard.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/boards")
@RestController
public class BoardController {

    final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public BoardDto saveBoard(@RequestBody BoardDto boardDto) {
        return this.boardService.saveBoard(boardDto);
    }

    @GetMapping("/{boardId}")
    public BoardDto findBoardById(@PathVariable("boardId") Long boardId) {
        return this.boardService.findBoardById(boardId);
    }

    @PutMapping("/{boardId}")
    public BoardDto modifyBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardDto boardDto) {
        return this.boardService.modifyBoard(boardId, boardDto);
    }

    @DeleteMapping("/{boardId}")
    public BoardDto deleteBoard(@PathVariable("boardId") Long boardId) {
        return this.boardService.deleteBoard(boardId);
    }
}
