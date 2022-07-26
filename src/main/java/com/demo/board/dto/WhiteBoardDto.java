package com.demo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhiteBoardDto{
    private Long id;

    private String name;
    private String type;
    private Long createdBy;
}
