package ru.yandex.practicum.utils;

import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.ArrayList;
import java.util.List;

public class AvroMappers {
    public static List<DeviceActionAvro> toDeviceActionAvro(List<DeviceActionProto> actions) {
        return actions.stream()
                .map(action -> DeviceActionAvro.newBuilder()
                        .setSensorId(action.getSensorId())
                        .setType(ActionTypeAvro.valueOf(action.getType().name()))
                        .setValue(action.getValue())
                        .build())
                .toList();
    }

    public static List<ScenarioConditionAvro> toScenarioConditionAvro(List<ScenarioConditionProto> conditions) {
        List<ScenarioConditionAvro> result = new ArrayList<>();
        Object value = null;

        for (ScenarioConditionProto condition : conditions) {
            if (condition.getValueCase() == ScenarioConditionProto.ValueCase.INT_VALUE) {
                value = condition.getIntValue();
            } else if (condition.getValueCase() == ScenarioConditionProto.ValueCase.BOOL_VALUE) {
                value = condition.getBoolValue();
            }

            result.add(ScenarioConditionAvro.newBuilder()
                    .setSensorId(condition.getSensorId())
                    .setValue(value)
                    .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                    .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                    .build());
        }
        return result;
    }
}
