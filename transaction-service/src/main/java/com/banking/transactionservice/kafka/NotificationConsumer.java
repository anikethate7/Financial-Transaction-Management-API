package com.banking.transactionservice.kafka;

// kafka/NotificationConsumer.java

import com.banking.transactionservice.dto.TransactionEvent;
import com.banking.transactionservice.entity.Notification;
import com.banking.transactionservice.repository.NotificationRepository;
import com.banking.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final TransactionRepository transactionRepository;

    @KafkaListener(topics = "bank-transactions", groupId = "notification-group")
    public void consume(TransactionEvent event) {
        log.info("Consumed event from Kafka → transactionId: {}, type: {}, amount: {}",
                event.getTransactionId(), event.getType(), event.getAmount());

        // Build notification message
        String message = String.format(
                "Transaction %s of ₹%.2f for account %d was successful.",
                event.getType(), event.getAmount(), event.getAccountId()
        );

        // Fetch the linked transaction
        transactionRepository.findById(event.getTransactionId()).ifPresent(transaction -> {
            Notification notification = Notification.builder()
                    .message(message)
                    .sentAt(LocalDateTime.now())
                    .transaction(transaction)
                    .build();

            notificationRepository.save(notification);
            log.info("Notification saved → {}", message);
        });
    }
}
