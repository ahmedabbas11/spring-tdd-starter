package com.demo.board.mapper;

import java.util.List;

public interface DtoMapper<Model, Dto> {

    List<Dto> mapListToDto(List<Model> whiteBoard);
    Dto mapToDto(Model t);
    Model mapToModel(Dto dto);
}
