package ru.yandex.practicum.service.handler.sensor;

import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.enums.SensorEventType;

public interface SensorEventHandler {
    SensorEventType getMessageType();

    void process(SensorEvent sensorEvent);
}
