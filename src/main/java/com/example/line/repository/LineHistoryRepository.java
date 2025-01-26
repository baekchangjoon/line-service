package com.example.line.repository;

import com.example.line.domain.LineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineHistoryRepository extends JpaRepository<LineHistory, Long> {
}
