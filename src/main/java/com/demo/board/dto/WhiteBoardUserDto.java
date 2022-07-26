package com.demo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhiteBoardUserDto {
    private Long id;
    private Long userId;
    private Long boardId;
}
