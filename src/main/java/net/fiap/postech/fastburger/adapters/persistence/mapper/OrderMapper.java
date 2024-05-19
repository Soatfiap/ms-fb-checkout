package net.fiap.postech.fastburger.adapters.persistence.mapper;

import net.fiap.postech.fastburger.adapters.configuration.exceptionHandler.BusinessException;
import net.fiap.postech.fastburger.adapters.configuration.exceptionHandler.ClientNotFoundException;
import net.fiap.postech.fastburger.adapters.persistence.dto.OrderDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.OrderItemDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.OrderRequestDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.ProductsOrderDTO;
import net.fiap.postech.fastburger.adapters.persistence.entities.OrderEntity;
import net.fiap.postech.fastburger.adapters.persistence.entities.ProductEntity;
import net.fiap.postech.fastburger.application.domain.Client;
import net.fiap.postech.fastburger.application.domain.Order;
import net.fiap.postech.fastburger.application.domain.OrderItem;
import net.fiap.postech.fastburger.application.domain.Product;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class OrderMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ClientMapper clientMapper;

    public OrderEntity orderToOrderEntity(Order order) {
        return modelMapper.map(order, OrderEntity.class);
    }

    public Order orderEntityToOrder(OrderEntity orderSaved) {
        return modelMapper.map(orderSaved, Order.class);
    }

    public Order orderDTOToOrder(OrderDTO order) {
        return modelMapper.map(order, Order.class);
    }

    public OrderDTO orderToOrderDTO(Order saved) {
        AtomicReference<Double> totalValueOrder = new AtomicReference<>(0.0);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(saved.getStatus());
        orderDTO.setOrderNumber(saved.getOrderNumber());
        orderDTO.setWasPaid(saved.getWasPaid());

        if (saved.getOrderItems() != null) {
            orderDTO.setOrderItens(orderItemToOrderItemDTO(saved.getOrderItems()));
            if (saved.getTotalValue() != null)
                orderDTO.setTotalValue(BigDecimal.valueOf(saved.getTotalValue().doubleValue()));
        }

        if (saved.getClient() != null)
            orderDTO.setClientCPF(saved.getClient().getCpf());

        return orderDTO;
    }

    public List<OrderItemDTO> orderItemToOrderItemDTO(List<OrderItem> itens) {
        List<OrderItemDTO> orderItemDTOSToReturn = new ArrayList<>();
        itens.forEach(orderItem -> {
            orderItemDTOSToReturn.add(new OrderItemDTO(orderItem.getProductId(), orderItem.getQuantity()));
        });
        return orderItemDTOSToReturn;
    }

    public Order mapOrderToReady(Order order) {
        order.setStatus(StatusOrder.READY);
        return order;
    }

    public Order mapOrderToFinished(Order order) {
        order.setStatus(StatusOrder.FINISHED);
        return order;
    }
}
