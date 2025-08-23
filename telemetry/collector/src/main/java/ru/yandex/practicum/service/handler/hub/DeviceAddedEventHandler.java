package ru.yandex.practicum.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.enums.HubEventType;

@Component
public class DeviceAddedEventHandler extends BaseHubEventHandler<DeviceAddedEventAvro> {
    public DeviceAddedEventHandler(KafkaClient kafkaClient) {
        super(kafkaClient);
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.DEVICE_ADDED;
    }

    @Override
    public DeviceAddedEventAvro mapToAvro(HubEvent hubEvent) {
        DeviceAddedEvent deviceAddedEvent = (DeviceAddedEvent) hubEvent;
        return DeviceAddedEventAvro.newBuilder()
                .setId(deviceAddedEvent.getId())
                .setDeviceType(DeviceTypeAvro.valueOf(deviceAddedEvent.getDeviceType().name()))
                .build();
    }
}
