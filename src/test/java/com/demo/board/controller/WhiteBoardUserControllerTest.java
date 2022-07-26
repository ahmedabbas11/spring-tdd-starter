package com.demo.board.controller;

import com.demo.board.command.WhiteBoardUserCmd;
import com.demo.board.repository.BoardRepository;
import com.demo.board.repository.BoardUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
class WhiteBoardUserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardUserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;


    @Autowired
    private MockMvc mvc;

    @Test
    @DatabaseSetup(value= "classpath:dbunit/user/testAddUserToBoardsData.xml")
    @ExpectedDatabase(value = "classpath:dbunit/user/testAddUserToBoardExpectedResult.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void inviteUserToBoard() throws Exception{

        long testId = 10001;
        long boardOwner = 10001;
        long loggedInUserId = 19957;
        WhiteBoardUserCmd user = WhiteBoardUserCmd
                .builder()
                .userId(boardOwner)
                .boardId(testId)
                .build();
        String json = objectMapper.writeValueAsString(user);

        MvcResult result = mvc.perform(
                post("/v1/private/demo/board/"+testId+"/user/")
                        .header("user_id", loggedInUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" ).exists())
                .andExpect(jsonPath("$.userId" , is(user.getUserId()), Long.class))
                .andExpect(jsonPath("$.boardId" , is(user.getBoardId()), Long.class))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DatabaseSetup("classpath:dbunit/user/testAddUserToBoardWithNotAllowedUser.xml")
    void inviteUserToBoardByNotAllowedUserWillFail() throws Exception{

        long testId= 10002;
        long boardId = testId;
        long boardNewMember =10003;
        long loggedInUserId = 19957;

        WhiteBoardUserCmd user = WhiteBoardUserCmd
                .builder()
                .userId(boardNewMember)
                .boardId(boardId)
                .build();
        String json = objectMapper.writeValueAsString(user);

        mvc.perform(
                post("/v1/private/demo/board/"+boardId+"/user/")
                        .header("user_id", loggedInUserId)
                        //token is for not allowed user 19957
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void inviteUserToNotExistingBoardWillFail() throws Exception{

        long testId= 10004L;
        long boardId = testId; //doesn't exist in db
        long boardNewMember =10005;
        long loggedInUserId = 19957;

        WhiteBoardUserCmd user = WhiteBoardUserCmd
                .builder()
                .userId(boardNewMember)
                .boardId(boardId)
                .build();
        String json = objectMapper.writeValueAsString(user);

        mvc.perform(
                post("/v1/private/demo/board/"+boardId+"/user/")
                        .header("user_id", loggedInUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

    }

    @Test
    @DatabaseSetup(value= "classpath:dbunit/user/testAddUserToBoardsAlreadyExistData.xml")
    void inviteUserAlreadyExistToBoardWillFail() throws Exception{

        long testId= 10005;
        long boardId = testId;
        long existingBoardMember =testId;
        long loggedInUserId = 19957;

        WhiteBoardUserCmd user = WhiteBoardUserCmd
                .builder()
                .userId(existingBoardMember)
                .boardId(boardId)
                .build();
        String json = objectMapper.writeValueAsString(user);

        mvc.perform(
                post("/v1/private/demo/board/"+boardId+"/user/")
                        .header("user_id", loggedInUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotAcceptable());
    }

}
