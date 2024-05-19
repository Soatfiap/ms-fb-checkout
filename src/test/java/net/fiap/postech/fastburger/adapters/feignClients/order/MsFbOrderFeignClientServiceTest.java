package net.fiap.postech.fastburger.adapters.feignClients.order;

import net.fiap.postech.fastburger.adapters.persistence.dto.OrderDTO;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MsFbOrderFeignClientServiceTest {

    @Mock
    private MsFbOrderFeignClient msFbOrderFeignClient;

    @InjectMocks
    private MsFbOrderFeignClientService msFbOrderFeignClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findOrders() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber("12345");
        when(msFbOrderFeignClient.findOrders()).thenReturn(ResponseEntity.ok(Collections.singletonList(orderDTO)));
        List<OrderDTO> result = msFbOrderFeignClientService.findOrders();
        assertEquals(1, result.size());
        assertEquals(orderDTO.getOrderNumber(), result.get(0).getOrderNumber());
    }

    @Test
    void findOrderByNumber() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber("12345");
        when(msFbOrderFeignClient.findOrderByNumber(any())).thenReturn(ResponseEntity.ok(orderDTO));
        OrderDTO result = msFbOrderFeignClientService.findOrderByNumber("12345");
        assertEquals(orderDTO.getOrderNumber(), result.getOrderNumber());
    }

    @Test
    void findOrdersByStatus() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber("12345");
        orderDTO.setStatus(StatusOrder.INPREPARATION);
        when(msFbOrderFeignClient.findOrdersByStatus(any())).thenReturn(ResponseEntity.ok(Collections.singletonList(orderDTO)));
        List<OrderDTO> result = msFbOrderFeignClientService.findOrdersByStatus(StatusOrder.INPREPARATION);
        assertEquals(1, result.size());
        assertEquals(orderDTO.getOrderNumber(), result.get(0).getOrderNumber());
        assertEquals(orderDTO.getStatus(), result.get(0).getStatus());
    }

    @Test
    void testUpdateStatusOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber("12345");
        orderDTO.setStatus(StatusOrder.INPREPARATION);
        when(msFbOrderFeignClient.updateStatusOrder(any(), any())).thenReturn(ResponseEntity.ok(orderDTO));
        OrderDTO result = msFbOrderFeignClientService.updateStatusOrder("12345", true);
        assertEquals(orderDTO.getOrderNumber(), result.getOrderNumber());
        assertEquals(orderDTO.getStatus(), result.getStatus());
    }
}