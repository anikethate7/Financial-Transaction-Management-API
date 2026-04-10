package com.banking.transactionservice.repository;

import com.banking.transactionservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// NotificationRepository.java
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {}
