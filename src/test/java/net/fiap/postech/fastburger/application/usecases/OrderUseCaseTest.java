package net.fiap.postech.fastburger.application.usecases;

import net.fiap.postech.fastburger.application.domain.Order;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;
import net.fiap.postech.fastburger.application.ports.outputports.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    private DeleteOrderOutPutPort deleteOrderOutPutPort;
    private ListOrderByNumberOutPutPort listOrderByNumberOutPutPort;
    private ListOrdersOutPutPort listOrdersOutPutPort;
    private SaveOrderOutPutPort saveOrderOutPutPort;
    private UpdateOrderOutPutPort updateOrderOutPutPort;
    private ListOrderByIdOutPutPort listOrderByIdOutPutPort;
    private ListOrderByStatusOutPutPort listOrderByStatusOutPutPort;
    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        deleteOrderOutPutPort = mock(DeleteOrderOutPutPort.class);
        listOrderByNumberOutPutPort = mock(ListOrderByNumberOutPutPort.class);
        listOrdersOutPutPort = mock(ListOrdersOutPutPort.class);
        saveOrderOutPutPort = mock(SaveOrderOutPutPort.class);
        updateOrderOutPutPort = mock(UpdateOrderOutPutPort.class);
        listOrderByIdOutPutPort = mock(ListOrderByIdOutPutPort.class);
        listOrderByStatusOutPutPort = mock(ListOrderByStatusOutPutPort.class);
        orderUseCase = new OrderUseCase(deleteOrderOutPutPort, listOrderByNumberOutPutPort, listOrdersOutPutPort, saveOrderOutPutPort, updateOrderOutPutPort, listOrderByIdOutPutPort, listOrderByStatusOutPutPort);
    }

    @Test
    void delete() {
        String id = "1";
        orderUseCase.delete(id);
        verify(deleteOrderOutPutPort, times(1)).delete(id);
    }

    @Test
    void listByNumber() {
        String number = "123";
        Order order = new Order();
        when(listOrderByNumberOutPutPort.listByNumber(anyString())).thenReturn(order);
        Order foundOrder = orderUseCase.listByNumber(number);
        assertEquals(order, foundOrder);
    }

    @Test
    void list() {
        Order order = new Order();
        when(listOrdersOutPutPort.list()).thenReturn(Collections.singletonList(order));
        var orders = orderUseCase.list();
        assertFalse(orders.isEmpty());
        assertEquals(order, orders.get(0));
    }

    @Test
    void save() {
        Order order = new Order();
        when(saveOrderOutPutPort.save(any())).thenReturn(order);
        Order savedOrder = orderUseCase.save(order);
        assertEquals(order, savedOrder);
    }

    @Test
    void update() {
        String id = "1";
        Order order = new Order();
        when(updateOrderOutPutPort.update(any(), any())).thenReturn(order);
        Order updatedOrder = orderUseCase.update(id, order);
        assertEquals(order, updatedOrder);
    }

    @Test
    void listById() {
        String id = "1";
        Order order = new Order();
        when(listOrderByIdOutPutPort.listById(anyString())).thenReturn(order);
        Order foundOrder = orderUseCase.listById(id);
        assertEquals(order, foundOrder);
    }

    @Test
    void listByStatus() {
        StatusOrder status = StatusOrder.INPREPARATION;
        Order order = new Order();
        when(listOrderByStatusOutPutPort.listByStatus(any())).thenReturn(Collections.singletonList(order));
        var orders = orderUseCase.listByStatus(status);
        assertFalse(orders.isEmpty());
        assertEquals(order, orders.get(0));
    }
}