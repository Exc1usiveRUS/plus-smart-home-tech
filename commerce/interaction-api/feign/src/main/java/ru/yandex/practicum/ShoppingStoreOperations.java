package ru.yandex.practicum;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ShoppingStoreOperations {
    @GetMapping("/api/v1/shopping-store")
    Page<ProductDto> getProducts(@RequestParam String category, Pageable pageable);

    @PutMapping("/api/v1/shopping-store")
    ProductDto addProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping("/api/v1/shopping-store")
    ProductDto updateProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping("/api/v1/shopping-store/removeProductFromStore")
    void removeProduct(@RequestBody UUID productId);

    @PostMapping("/api/v1/shopping-store/quantityState")
    void setProductState(@RequestParam UUID productId,
                         @RequestParam String quantityState);

    @GetMapping("/api/v1/shopping-store/{productId}")
    ProductDto getProductById(@PathVariable UUID productId);
}