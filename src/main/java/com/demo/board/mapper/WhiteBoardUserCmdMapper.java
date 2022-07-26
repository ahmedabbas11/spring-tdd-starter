package com.demo.board.mapper;

import com.demo.board.model.WhiteBoardUser;
import com.demo.board.command.WhiteBoardUserCmd;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WhiteBoardUserCmdMapper {
    WhiteBoardUserCmd mapuserToCommand(WhiteBoardUser user);
    @Mapping(source = "boardId", target = "board.id")
    WhiteBoardUser mapCommandToUser(WhiteBoardUserCmd cmd);
}