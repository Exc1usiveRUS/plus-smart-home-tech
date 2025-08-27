package ru.yandex.practicum.service.handler.hub;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.hub.HubEvent;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    protected final KafkaClient kafkaClient;
    @Value("${kafka.topics.hub-events}")
    private String HUB_EVENT_TOPIC;

    protected abstract T mapToAvro(HubEvent hubEvent);

    @Override
    public void process(HubEvent hubEvent) {
        if(!hubEvent.getType().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события хаба: " + hubEvent.getType());
        }

        T payload = mapToAvro(hubEvent);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(hubEvent.getTimestamp())
                .setPayload(payload)
                .build();

        kafkaClient.getProducer().send(new ProducerRecord<>(HUB_EVENT_TOPIC, null, null, eventAvro));
    }

    @PreDestroy
    public void close() {
        try {
            kafkaClient.getProducer().flush();
        } finally {
            kafkaClient.getProducer().close();
        }
    }
}
