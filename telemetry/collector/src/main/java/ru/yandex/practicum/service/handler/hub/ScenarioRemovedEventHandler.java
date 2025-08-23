package ru.yandex.practicum.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.model.hub.enums.HubEventType;

@Component
public class ScenarioRemovedEventHandler extends BaseHubEventHandler<ScenarioRemovedEventAvro> {
    public ScenarioRemovedEventHandler(KafkaClient kafkaClient) {
        super(kafkaClient);
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_REMOVED;
    }

    @Override
    public ScenarioRemovedEventAvro mapToAvro(HubEvent hubEvent) {
        ScenarioRemovedEvent scenarioRemovedEvent = (ScenarioRemovedEvent) hubEvent;

        return ScenarioRemovedEventAvro.newBuilder()
                .setName(scenarioRemovedEvent.getName())
                .build();
    }
}
