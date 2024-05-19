package net.fiap.postech.fastburger.adapters.feignClients.order;

import net.fiap.postech.fastburger.adapters.persistence.dto.OrderDTO;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsFbOrderFeignClientService implements IMsFbOrderFeignClient {

    private final MsFbOrderFeignClient msFbOrderFeignClient;

    public MsFbOrderFeignClientService(MsFbOrderFeignClient msFbOrderFeignClient) {
        this.msFbOrderFeignClient = msFbOrderFeignClient;
    }

    @Override
    public List<OrderDTO> findOrders() {
        return this.msFbOrderFeignClient.findOrders().getBody();
    }

    @Override
    public OrderDTO findOrderByNumber(String orderNumber) {
        return this.msFbOrderFeignClient.findOrderByNumber(orderNumber).getBody();
    }

    @Override
    public List<OrderDTO> findOrdersByStatus(StatusOrder statusOrder) {
        return this.msFbOrderFeignClient.findOrdersByStatus(statusOrder).getBody();
    }

    @Override
    public OrderDTO updateStatusOrder(String orderNumber, Boolean wasPaid) {
        return (OrderDTO) this.msFbOrderFeignClient.updateStatusOrder(orderNumber, wasPaid).getBody();
    }

}
