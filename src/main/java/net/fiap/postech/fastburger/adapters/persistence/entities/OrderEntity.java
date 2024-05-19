package net.fiap.postech.fastburger.adapters.persistence.entities;

import lombok.*;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

    private Long id;
    private String orderNumber;
    private Double totalValue = BigDecimal.ZERO.doubleValue();
    private Boolean wasPaid = false;
    private LocalDateTime dateTimeCreation;
    private ClientEntity client;
    private List<OrderItemEntity> orderItems;
    private StatusOrder status;
}
