package ru.yandex.practicum.service.handler.hub;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    protected final KafkaClient kafkaClient;
    @Value("${kafka.topics.hub-events}")
    private String HUB_EVENT_TOPIC;

    protected abstract T mapToAvro(HubEventProto hubEvent);

    @Override
    public void process(HubEventProto hubEvent) {
        if(!hubEvent.getPayloadCase().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события хаба: " + hubEvent.getPayloadCase());
        }

        T payload = mapToAvro(hubEvent);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(Instant.ofEpochSecond(hubEvent.getTimestamp().getSeconds(),
                        hubEvent.getTimestamp().getNanos()))
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
