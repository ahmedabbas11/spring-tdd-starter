package com.demo.board.controller;

import com.demo.board.command.BoardCreationCmd;
import com.demo.board.model.WhiteBoard;
import com.demo.board.util.BoardType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.demo.board.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
class WhiteBoardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/board/testCreateBoardExpectedResult.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseSetup("classpath:dbunit/board/testcreateBoardsData.xml")
    void createBoardSuccessfully() throws Exception {

        BoardCreationCmd boardCreationCmd = generateBoardCommand("abbas-whiteBoard");
        String json = objectMapper.writeValueAsString(boardCreationCmd);
        long loggedInUserId = 19957;

        mvc.perform(
                post("/v1/private/demo/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("user_id", loggedInUserId)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" ).exists())
                .andExpect(jsonPath("$.name" , is(boardCreationCmd.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type" , Matchers.is(BoardType.BASIC.toString())))
                .andDo(print());
    }

    private BoardCreationCmd generateBoardCommand(String boardName) {
        return BoardCreationCmd
                    .builder()
                    .name(boardName)
                    .type(BoardType.BASIC)
                    .build();
    }

    @Test
    @DatabaseSetup("classpath:dbunit/board/testGetBoardData.xml")
    void getBoardInfoByOwnerSuccessfully() throws Exception{
        long loggedInUserId = 19957;
        Long testId = 2000L;
        Long boardId = testId;
        mvc.perform(
                get("/v1/private/demo/board/"+boardId)
                        .header("user_id", loggedInUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" , is(testId), Long.class))
                .andDo(print());
    }

    @Test
    @DatabaseSetup("classpath:dbunit/board/testGetBoardByMemberData.xml")
    void getBoardInfoByMemberSuccessfully() throws Exception{
        long loggedInUserId = 19957;
        Long testId = 2000L;
        Long boardId = testId;
        mvc.perform(
                get("/v1/private/demo/board/"+boardId)
                        .header("user_id", loggedInUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" , is(testId), Long.class))
                .andDo(print());
    }

    @Test
    @DatabaseSetup("classpath:dbunit/board/testGetBoardWithNonMemberData.xml")
    void getBoardInfoByNonMemberWillFail() throws Exception{
        long loggedInUserId = 19957;
        Long testId = 2000L;
        Long boardId = testId;
        mvc.perform(
                get("/v1/private/demo/board/"+boardId)
                        .header("user_id", loggedInUserId))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getNotExistingBoardInfoFail() throws Exception{
        long loggedInUserId = 19957;
        Long testId = 2000L; // no seed file , so board doesn't exist
        Long boardId = testId;
        mvc.perform(
                get("/v1/private/demo/board/"+boardId)
                        .header("user_id", loggedInUserId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    private WhiteBoard generateBoard(String boardName) {
        return WhiteBoard
                .builder()
                .name(boardName)
                .type(BoardType.BASIC)
                .createdBy(987546L)
                .build();
    }
}
