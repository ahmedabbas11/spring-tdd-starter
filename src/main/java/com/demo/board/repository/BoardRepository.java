package com.demo.board.repository;

import com.demo.board.model.WhiteBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends PagingAndSortingRepository<WhiteBoard, Long> {

}
