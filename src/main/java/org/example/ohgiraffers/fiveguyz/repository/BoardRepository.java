package org.example.ohgiraffers.fiveguyz.repository;

import org.example.ohgiraffers.fiveguyz.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
