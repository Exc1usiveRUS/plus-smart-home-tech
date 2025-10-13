package ru.yandex.practicum.service;

import ru.yandex.practicum.model.AddressDto;
import ru.yandex.practicum.model.BookedProductsDto;
import ru.yandex.practicum.model.ShoppingCartDto;
import ru.yandex.practicum.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.request.NewProductWarehouseRequest;
import ru.yandex.practicum.request.ShippedToDeliveryRequest;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {
    void addProduct(NewProductWarehouseRequest request);

    void shipped(ShippedToDeliveryRequest request);

    void returnProduct(Map<UUID, Integer> products);

    BookedProductsDto assembleProduct(AssemblyProductsForOrderRequest request);

    BookedProductsDto checkProduct(ShoppingCartDto cart);

    void addProductToWarehouse(AddProductToWarehouseRequest request);

    AddressDto getAddress();
}