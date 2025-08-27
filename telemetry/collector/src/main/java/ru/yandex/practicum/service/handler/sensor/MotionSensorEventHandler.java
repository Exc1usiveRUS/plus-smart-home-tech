package ru.yandex.practicum.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.sensor.MotionSensor;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.enums.SensorEventType;

@Component
public class MotionSensorEventHandler extends BaseSensorEventHandler<MotionSensorAvro> {
    public MotionSensorEventHandler(KafkaClient kafkaClient) {
        super(kafkaClient);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }

    @Override
    public MotionSensorAvro mapToAvro(SensorEvent sensorEvent) {
        MotionSensor event = (MotionSensor) sensorEvent;
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setMotion(event.isMotion())
                .setVoltage(event.getVoltage())
                .build();
    }
}
