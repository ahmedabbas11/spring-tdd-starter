package com.demo.board.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhiteBoardUserCmd {
    private Long userId;
    private Long boardId;
}
