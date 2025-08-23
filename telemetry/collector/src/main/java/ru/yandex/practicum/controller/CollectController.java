package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.enums.HubEventType;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.enums.SensorEventType;
import ru.yandex.practicum.service.handler.hub.HubEventHandler;
import ru.yandex.practicum.service.handler.sensor.SensorEventHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/events")
@Validated
public class CollectController {
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    public CollectController(List<SensorEventHandler> sensorEventHandlers, List<HubEventHandler> hubEventHandlers) {
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent request) {
        log.info("Получен запрос на обработку события датчика {}", request);
        if (sensorEventHandlers.containsKey(request.getType())) {
            sensorEventHandlers.get(request.getType()).handle(request);
        } else {
            throw new IllegalArgumentException("Не удалось обнаружить обработчик для датчика " + request.getType());
        }
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@Valid @RequestBody HubEvent request) {
        log.info("Получен запрос на обработку события хаба {}", request);
        if (hubEventHandlers.containsKey(request.getType())) {
            hubEventHandlers.get(request.getType()).handle(request);
        } else {
            throw new IllegalArgumentException("Не удалось обнаружить обработчик для хаба " + request.getType());
        }
    }
}
