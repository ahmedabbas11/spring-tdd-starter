CREATE TABLE white_board_user
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id   INT  NOT NULL,
    board_id   INT ,
    FOREIGN KEY(board_id) REFERENCES white_board(id)
);