package com.banking.transactionservice.kafka;

import com.banking.transactionservice.dto.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventProducer {

    private static final String TOPIC = "bank-transactions";

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void publish(TransactionEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.getTransactionId()), event);
        log.info("Published event to Kafka → transactionId: {}, type: {}",
                event.getTransactionId(), event.getType());
    }
}
