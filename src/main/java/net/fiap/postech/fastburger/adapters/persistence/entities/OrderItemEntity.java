package net.fiap.postech.fastburger.adapters.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {
    private Long id;
    private Long productId;
    private Integer quantity;
}
