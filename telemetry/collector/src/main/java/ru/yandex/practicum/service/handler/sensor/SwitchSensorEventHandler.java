package ru.yandex.practicum.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SwitchSensor;
import ru.yandex.practicum.model.sensor.enums.SensorEventType;

@Component
public class SwitchSensorEventHandler extends BaseSensorEventHandler<SwitchSensorAvro> {
    public SwitchSensorEventHandler(KafkaClient kafkaClient) {
        super(kafkaClient);
    }

    @Override
    public SensorEventType getMessageType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }

    @Override
    public SwitchSensorAvro mapToAvro(SensorEvent sensorEvent) {
        SwitchSensor event = (SwitchSensor) sensorEvent;
        return SwitchSensorAvro.newBuilder()
                .setState(event.getState())
                .build();
    }
}
