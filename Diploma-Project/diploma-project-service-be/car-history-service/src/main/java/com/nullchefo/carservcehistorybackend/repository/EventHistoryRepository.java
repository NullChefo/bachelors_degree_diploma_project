package com.nullchefo.carservcehistorybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.carservcehistorybackend.model.EventHistory;

@Repository
public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {
}
