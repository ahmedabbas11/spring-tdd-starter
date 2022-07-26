package com.demo.board.mapper;

import com.demo.board.command.BoardCreationCmd;
import com.demo.board.model.WhiteBoard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WhiteBoardCreationCmdMapper {
    BoardCreationCmd mapBoardToBoardCreationCmd(WhiteBoard whiteBoard);
    WhiteBoard mapBoardCreationCmdToBoard(BoardCreationCmd cmd);
}