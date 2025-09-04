package ru.yandex.practicum.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka_client.KafkaClient;
import ru.yandex.practicum.utils.AvroMappers;

@Component
public class ScenarioAddedEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {
    public ScenarioAddedEventHandler(KafkaClient kafkaClient) {
        super(kafkaClient);
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public ScenarioAddedEventAvro mapToAvro(HubEventProto hubEvent) {
        ScenarioAddedEventProto scenarioAddedEvent = hubEvent.getScenarioAdded();

        return ScenarioAddedEventAvro.newBuilder()
                .setActions(AvroMappers.toDeviceActionAvro(scenarioAddedEvent.getActionList()))
                .setConditions(AvroMappers.toScenarioConditionAvro(scenarioAddedEvent.getConditionList()))
                .setName(scenarioAddedEvent.getName())
                .build();
    }
}
