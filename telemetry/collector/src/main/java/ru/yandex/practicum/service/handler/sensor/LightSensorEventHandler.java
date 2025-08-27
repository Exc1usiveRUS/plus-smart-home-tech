package ru.yandex.practicum.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.sensor.LightSensor;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.enums.SensorEventType;

@Component
public class LightSensorEventHandler extends BaseSensorEventHandler<LightSensorAvro> {
    public LightSensorEventHandler(KafkaClient kafkaClient) {
        super(kafkaClient);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }

    @Override
    protected LightSensorAvro mapToAvro(SensorEvent sensorEvent) {
        LightSensor event = (LightSensor) sensorEvent;
        return LightSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setLuminosity(event.getLuminosity())
                .build();
    }
}
