package com.demo.board.controller;

import com.demo.board.command.BoardCreationCmd;
import com.demo.board.dto.WhiteBoardDto;
import com.demo.board.exception.BoardNotFoundException;
import com.demo.board.exception.NotAuthorizedException;
import com.demo.board.exception.NotFoundException;
import com.demo.board.exception.UserNotBoardMemberException;
import com.demo.board.mapper.WhiteBoardCreationCmdMapper;
import com.demo.board.mapper.WhiteBoardDtoMapper;
import com.demo.board.model.WhiteBoard;
import com.demo.board.service.WhiteBoardService;
import com.demo.board.service.WhiteBoardUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix.wb}")
@Slf4j
public class WhiteBoardController {

    @Autowired private WhiteBoardCreationCmdMapper cmdMapper;
    @Autowired private WhiteBoardDtoMapper dtoMapper;
    @Autowired private WhiteBoardService service;
    @Autowired private WhiteBoardUserService userService;

    @PostMapping("/board")
    public WhiteBoardDto createBoard(
            @RequestHeader("user_id") Long userId,
            @RequestBody BoardCreationCmd command){
        // for demo only
        // TODO should be retrieved from token or security context
        Long loggedInUserId = userId;
        log.info("loggedInUserId " + loggedInUserId);

        WhiteBoard whiteBoard = this.cmdMapper.mapBoardCreationCmdToBoard(command);
        whiteBoard.setCreatedBy(loggedInUserId);
        WhiteBoard persistedWhiteBoard = service.save(whiteBoard);

        return dtoMapper.mapToDto(persistedWhiteBoard);
    }


    // already exist
    @GetMapping("/board/{boardId}")
    public WhiteBoardDto getBoard(
            @RequestHeader("user_id") Long userId,
            @PathVariable Long boardId){

        // for demo only
        // TODO should be retrieved from token or security context
        Long loggedInUserId = 19957L;

        try {

            WhiteBoard board = this.service.validateBoardExistance(boardId);
            this.userService.validateUserIsBoardMember(board, loggedInUserId);
            return this.dtoMapper.mapToDto(board);

        }catch (BoardNotFoundException ex){
            log.error("getBoard | Target Board Not Found", ex);
            throw new NotFoundException("Board [" + boardId + "] not found");
        }
        catch (UserNotBoardMemberException ex){
            log.error("getBoard | Logged In User is not the board member", ex);
            throw new NotAuthorizedException("userId [" + loggedInUserId + "] ");
        }
    }
}
