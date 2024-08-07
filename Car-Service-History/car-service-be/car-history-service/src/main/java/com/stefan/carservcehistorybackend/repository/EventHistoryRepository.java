package com.stefan.carservcehistorybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefan.carservcehistorybackend.model.EventHistory;

@Repository
public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {
}
