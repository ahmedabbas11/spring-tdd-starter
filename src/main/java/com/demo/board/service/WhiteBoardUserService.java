package com.demo.board.service;

import com.demo.board.model.WhiteBoard;
import com.demo.board.model.WhiteBoardUser;

public interface WhiteBoardUserService {
    void validateUserIsBoardOwner(WhiteBoard board, long userId);

    void validateUserIsBoardMember(WhiteBoard board, long userId);

    WhiteBoardUser save(WhiteBoardUser user);

    void validateUserNotAlreadyMember(WhiteBoard board, long userId);
}
