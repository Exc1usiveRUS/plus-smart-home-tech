package ru.yandex.practicum.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.model.hub.enums.HubEventType;
import ru.yandex.practicum.utils.AvroMappers;

@Component
public class ScenarioAddedEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {
    public ScenarioAddedEventHandler(KafkaClient kafkaClient) {
        super(kafkaClient);
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_ADDED;
    }

    @Override
    public ScenarioAddedEventAvro mapToAvro(HubEvent hubEvent) {
        ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) hubEvent;

        return ScenarioAddedEventAvro.newBuilder()
                .setActions(AvroMappers.toDeviceActionAvro(scenarioAddedEvent.getActions()))
                .setConditions(AvroMappers.toScenarioConditionAvro(scenarioAddedEvent.getConditions()))
                .setName(scenarioAddedEvent.getName())
                .build();
    }
}
