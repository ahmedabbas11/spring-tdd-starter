package com.demo.board.service;

import com.demo.board.dto.WhiteBoardDto;
import com.demo.board.model.WhiteBoard;
import org.springframework.data.domain.Page;

public interface WhiteBoardService {

    WhiteBoard save(WhiteBoard board);
    WhiteBoard validateBoardExistance(Long boardId);
}
