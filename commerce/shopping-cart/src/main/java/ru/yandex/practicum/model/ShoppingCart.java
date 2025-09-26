package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @UuidGenerator
    private UUID shoppingCartId;

    @ElementCollection
    @CollectionTable(
            name = "products_in_shopping_carts",
            joinColumns = @JoinColumn(name = "shopping_cart_id")
    )
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Integer> products;

    private String userName;

    @Enumerated(EnumType.STRING)
    private ShoppingCartState state;

}
