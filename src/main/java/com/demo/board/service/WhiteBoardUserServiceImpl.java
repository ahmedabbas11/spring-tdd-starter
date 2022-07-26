package com.demo.board.service;

import com.demo.board.exception.UserAlreayMemberException;
import com.demo.board.exception.UserNotBoardMemberException;
import com.demo.board.exception.UserNotBoardOwnerException;
import com.demo.board.mapper.WhiteBoardUserDtoMapper;
import com.demo.board.model.WhiteBoard;
import com.demo.board.model.WhiteBoardUser;
import com.demo.board.repository.BoardUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhiteBoardUserServiceImpl implements WhiteBoardUserService {

    @Autowired
    private BoardUserRepository repository;
    @Autowired private WhiteBoardUserDtoMapper userDtoMapper;

    @Override
    public void validateUserIsBoardOwner(WhiteBoard board, long userId){
        if(board.getCreatedBy() != userId){
            throw new UserNotBoardOwnerException();
        }
    }

    @Override
    public void validateUserIsBoardMember(WhiteBoard board, long userId){
        if(board.getCreatedBy() != userId && this.repository.countByUserIdAndBoard_Id(userId, board.getId()) ==0){
            throw new UserNotBoardMemberException();
        }
    }


    @Override
    public WhiteBoardUser save(WhiteBoardUser user) {
        return repository.save(user);
    }

    @Override
    public void validateUserNotAlreadyMember(WhiteBoard board, long userId) {
        if(board.getCreatedBy() == userId || this.repository.countByUserIdAndBoard_Id(userId, board.getId()) > 0){
            throw new UserAlreayMemberException();
        }
    }
}
