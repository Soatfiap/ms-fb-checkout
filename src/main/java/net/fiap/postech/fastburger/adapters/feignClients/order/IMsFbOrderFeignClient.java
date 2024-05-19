package net.fiap.postech.fastburger.adapters.feignClients.order;

import net.fiap.postech.fastburger.adapters.persistence.dto.OrderDTO;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;

import java.util.List;

public interface IMsFbOrderFeignClient {

    List<OrderDTO> findOrders();

    OrderDTO findOrderByNumber(String orderNumber);

    List<OrderDTO> findOrdersByStatus(StatusOrder statusOrder);

    OrderDTO updateStatusOrder(String orderNumber, Boolean wasPaid);

}
