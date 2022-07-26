package com.demo.board.mapper;

import com.demo.board.model.WhiteBoard;
import com.demo.board.dto.WhiteBoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WhiteBoardDtoMapper  extends DtoMapper<WhiteBoard, WhiteBoardDto>{


}