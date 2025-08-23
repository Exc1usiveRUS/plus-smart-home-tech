package ru.yandex.practicum.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.sensor.SensorEvent;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    protected final KafkaClient kafkaClient;

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

        kafkaClient.getProducer().send(new ProducerRecord<>(sensorEvent.getType().name(), null, eventAvro));
    }
}
