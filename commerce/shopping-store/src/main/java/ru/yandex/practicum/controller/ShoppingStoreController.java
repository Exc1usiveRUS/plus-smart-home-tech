package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.ShoppingStoreOperations;
import ru.yandex.practicum.model.ProductDto;
import ru.yandex.practicum.model.QuantityState;
import ru.yandex.practicum.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.service.ShoppingStoreService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShoppingStoreController implements ShoppingStoreOperations {
    private final ShoppingStoreService shoppingStoreService;

    @Override
    public Page<ProductDto> getProducts(String category, Pageable pageable) {
        log.info("Поступил запрос на получение списка продуктов по категории: {}", category);
        return shoppingStoreService.getProducts(category, pageable);
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        log.info("Поступил запрос на добавление продукта: {}", productDto);
        return shoppingStoreService.addProduct(productDto);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        log.info("Поступил запрос на обновление продукта: {}", productDto);
        return shoppingStoreService.updateProduct(productDto);
    }

    @Override
    public void removeProduct(UUID productId) {
        log.info("Поступил запрос на удаление продукта: {}", productId);
        shoppingStoreService.removeProduct(productId);
    }

    @Override
    public void setProductState(@RequestParam UUID productId,
                                @RequestParam String quantityState) {
        log.info("Поступил запрос на изменение состояния продукта: {}", productId);
        shoppingStoreService.setProductState(
                new SetProductQuantityStateRequest(productId, QuantityState.valueOf(quantityState)));
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        log.info("Поступил запрос на получение продукта по id: {}", productId);
        return shoppingStoreService.getProductById(productId);
    }
}