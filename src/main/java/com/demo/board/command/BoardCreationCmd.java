package com.demo.board.command;

import com.demo.board.util.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreationCmd {

    private String name;
    private BoardType type;


}
