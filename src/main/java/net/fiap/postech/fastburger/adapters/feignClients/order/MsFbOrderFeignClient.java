package net.fiap.postech.fastburger.adapters.feignClients.order;

import net.fiap.postech.fastburger.adapters.persistence.dto.OrderDTO;
import net.fiap.postech.fastburger.application.domain.Order;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "order-service", url = "${MS-FB-ORDER}")
public interface MsFbOrderFeignClient {

    @GetMapping("/api/v1/order")
    ResponseEntity<List<OrderDTO>> findOrders();

    @GetMapping("/api/v1/order/orderNumber/{orderNumber}")
    ResponseEntity<OrderDTO> findOrderByNumber(@PathVariable("orderNumber") String orderNumber);

    @GetMapping("/api/v1/order/{status}")
    ResponseEntity<List<OrderDTO>> findOrdersByStatus(@PathVariable("status") StatusOrder statusOrder);

    @PutMapping("/api/v1/order/order/ready/{orderNumber}")
    ResponseEntity<Order> orderReady(@PathVariable("orderNumber") String orderNumber);

    @PutMapping("/api/v1/order/order/finished/{orderNumber}")
    ResponseEntity<Order> orderFinished(@PathVariable("orderNumber") String orderNumber);

    @DeleteMapping("/api/v1/order/{orderNumber}")
    ResponseEntity<Void> deleteOrderByNumber(@PathVariable("orderNumber") String orderNumber);

    @PutMapping("/order/status/{orderNumber}/{wasPaid}")
    ResponseEntity updateStatusOrder(@PathVariable("orderNumber") String orderNumber, @PathVariable("wasPaid") Boolean wasPaid);
}
