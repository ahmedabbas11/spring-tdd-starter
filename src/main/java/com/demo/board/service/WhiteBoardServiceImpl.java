package com.demo.board.service;

import com.demo.board.dto.WhiteBoardDto;
import com.demo.board.exception.BoardNotFoundException;
import com.demo.board.mapper.WhiteBoardDtoMapper;
import com.demo.board.model.WhiteBoard;
import com.demo.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WhiteBoardServiceImpl implements WhiteBoardService {

    @Autowired
    private BoardRepository repository;
    @Autowired private WhiteBoardDtoMapper dtoMapper;


    @Override
    public WhiteBoard save(WhiteBoard board) {
        return this.repository.save(board);
    }

    @Override
    public WhiteBoard validateBoardExistance(Long boardId) {
        return this.repository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    }
}
