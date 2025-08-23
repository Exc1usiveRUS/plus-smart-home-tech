package ru.yandex.practicum.service.handler.sensor;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.sensor.SensorEvent;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    protected final KafkaClient kafkaClient;
    @Value("${kafka.topics.sensor-events}")
    private String SENSOR_EVENT_TOPIC;

    protected abstract T mapToAvro(SensorEvent sensorEvent);

    @Override
    public void handle(SensorEvent sensorEvent) {
        if(!sensorEvent.getType().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события сенсора: " + sensorEvent.getType());
        }

        T payload = mapToAvro(sensorEvent);

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setHubId(sensorEvent.getHubId())
                .setId(sensorEvent.getId())
                .setTimestamp(sensorEvent.getTimestamp())
                .setPayload(payload)
                .build();

        kafkaClient.getProducer().send(new ProducerRecord<>(SENSOR_EVENT_TOPIC, null, null, eventAvro));
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
