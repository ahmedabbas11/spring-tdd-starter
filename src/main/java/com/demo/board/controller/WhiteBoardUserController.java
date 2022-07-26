package com.demo.board.controller;

import com.demo.board.command.WhiteBoardUserCmd;
import com.demo.board.dto.WhiteBoardUserDto;
import com.demo.board.exception.*;
import com.demo.board.mapper.WhiteBoardUserCmdMapper;
import com.demo.board.mapper.WhiteBoardUserDtoMapper;
import com.demo.board.model.WhiteBoard;
import com.demo.board.model.WhiteBoardUser;
import com.demo.board.service.WhiteBoardService;
import com.demo.board.service.WhiteBoardUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix.wb}")
@Slf4j
public class WhiteBoardUserController {


    @Autowired private WhiteBoardUserCmdMapper userCmdMapper;
    @Autowired private WhiteBoardUserDtoMapper userDtoMapper;
    @Autowired private WhiteBoardUserService userService;
    @Autowired private WhiteBoardService boardService;




    @PostMapping("/board/{boardId}/user")
    public WhiteBoardUserDto addUserToBoard(
            @RequestHeader("user_id") Long userId,
            @PathVariable Long boardId,
            @RequestBody WhiteBoardUserCmd command){
        // for demo only
        // TODO should be retrieved from token or security context
        Long loggedInUserId = userId;
        try {
            //get board
            WhiteBoard board = this.boardService.validateBoardExistance(boardId);

            // validate that user is allowed to add members to board
            this.userService.validateUserIsBoardOwner(board , loggedInUserId);

            this.userService.validateUserNotAlreadyMember(board, command.getUserId());

            WhiteBoardUser boardUser = this.userCmdMapper.mapCommandToUser(command);
            WhiteBoardUser persistedUser = userService.save(boardUser);
            return this.userDtoMapper.mapToDto(persistedUser);
        }
        catch (BoardNotFoundException ex){
            log.error("addUserToBoard | Target Board Not Found", ex);
            throw new NotFoundException("Board [" + boardId + "] Not Found");
        }
        catch (UserNotBoardOwnerException ex){
            log.error("addUserToBoard | Logged In User is not the board owners", ex);
            throw new NotAuthorizedException("userId [" + loggedInUserId + "] not board owner");
        }
        catch (UserAlreayMemberException ex){
            log.error("addUserToBoard | Invited User already board member", ex);
            throw new NotAcceptableException("userId [" + loggedInUserId + "] not board member");
        }

    }
}

