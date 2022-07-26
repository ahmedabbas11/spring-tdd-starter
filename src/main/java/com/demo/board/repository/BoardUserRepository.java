package com.demo.board.repository;

import com.demo.board.model.WhiteBoardUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BoardUserRepository extends PagingAndSortingRepository<WhiteBoardUser, Long> {

    int countByUserIdAndBoard_Id(long userId, Long id);

}
