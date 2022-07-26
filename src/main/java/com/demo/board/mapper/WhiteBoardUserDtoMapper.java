package com.demo.board.mapper;

import com.demo.board.model.WhiteBoardUser;
import com.demo.board.dto.WhiteBoardUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WhiteBoardUserDtoMapper extends DtoMapper<WhiteBoardUser, WhiteBoardUserDto>{
    @Mapping(source = "board.id", target = "boardId")
    WhiteBoardUserDto mapToDto(WhiteBoardUser user);

}