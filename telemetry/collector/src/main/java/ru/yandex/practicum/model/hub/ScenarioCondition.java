package ru.yandex.practicum.model.hub;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.model.hub.enums.ConditionOperationType;
import ru.yandex.practicum.model.hub.enums.ConditionType;


@Getter @Setter @ToString
public class ScenarioCondition {
    private String sensorId;
    private ConditionType type;
    private ConditionOperationType operation;
    private int value;
}
