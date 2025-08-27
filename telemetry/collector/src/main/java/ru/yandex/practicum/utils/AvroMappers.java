package ru.yandex.practicum.utils;

import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.hub.DeviceAction;
import ru.yandex.practicum.model.hub.ScenarioCondition;

import java.util.List;

public class AvroMappers {
    public static List<DeviceActionAvro> toDeviceActionAvro(List<DeviceAction> actions) {
        return actions.stream()
                .map(action -> DeviceActionAvro.newBuilder()
                        .setSensorId(action.getSensorId())
                        .setType(ActionTypeAvro.valueOf(action.getType().name()))
                        .setValue(action.getValue())
                        .build())
                .toList();
    }

    public static List<ScenarioConditionAvro> toScenarioConditionAvro(List<ScenarioCondition> conditions) {
        return conditions.stream()
                .map(condition -> ScenarioConditionAvro.newBuilder()
                        .setSensorId(condition.getSensorId())
                        .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                        .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                        .setValue(condition.getValue())
                        .build())
                .toList();
    }
}
