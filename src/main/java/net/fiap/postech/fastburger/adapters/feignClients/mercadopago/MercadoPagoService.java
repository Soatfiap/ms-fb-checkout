package net.fiap.postech.fastburger.adapters.feignClients.mercadopago;

import net.fiap.postech.fastburger.adapters.feignClients.dto.PayerDTO;
import net.fiap.postech.fastburger.adapters.feignClients.dto.PaymentDTO;
import net.fiap.postech.fastburger.adapters.feignClients.dto.PaymentRequestDTO;
import net.fiap.postech.fastburger.adapters.feignClients.order.MsFbOrderFeignClientService;
import net.fiap.postech.fastburger.adapters.persistence.dto.OrderDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.PaymentMethodDTO;
import net.fiap.postech.fastburger.application.domain.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MercadoPagoService {

    @Value("${MERCADO_TOKEN}")
    private String bearerToken;
    private final MercadoPagoFeignClient mercadoPagoFeignClient;
    private final MsFbOrderFeignClientService msFbClientFeignClientService;

    public MercadoPagoService(MercadoPagoFeignClient mercadoPagoFeignClient, MsFbOrderFeignClientService msFbClientFeignClientService) {
        this.mercadoPagoFeignClient = mercadoPagoFeignClient;
        this.msFbClientFeignClientService = msFbClientFeignClientService;
    }

    public PaymentDTO generateQRCode(OrderDTO orderDTO, PaymentMethodDTO paymentMethodDTO) {
        Client client = null;
        PayerDTO payerDTO = new PayerDTO();
        PaymentRequestDTO paymentRequestDTO = null;
        if (orderDTO.getTotalValue() == null) {
            paymentRequestDTO = new PaymentRequestDTO(paymentMethodDTO.getMethod().name().toLowerCase(), payerDTO);
        } else {
            paymentRequestDTO = new PaymentRequestDTO(orderDTO.getTotalValue().doubleValue(), paymentMethodDTO.getMethod().name().toLowerCase(), payerDTO);
        }
        generateDescription(orderDTO, paymentRequestDTO);

        if (orderDTO.getClientCPF() != null) {
            payerDTO.setEmail("4soatg44@gmail.com");
            payerDTO.setFirst_name("fastburger - data preservation");
            payerDTO.setLastName("fastburger - data preservation");
        } else {
            payerDTO.setEmail("4soatg44@gmail.com");
            payerDTO.setFirst_name("fastburger - not informed");
            payerDTO.setLastName("fastburger - not informed");
        }

        ResponseEntity<PaymentDTO> paymentDTOResponseEntity = this.mercadoPagoFeignClient.generateQRCode(bearerToken, orderDTO.getOrderNumber(), paymentRequestDTO);
        return paymentDTOResponseEntity.getBody();
    }

    private static void generateDescription(OrderDTO orderDTO, PaymentRequestDTO paymentRequestDTO) {
        StringBuilder description = new StringBuilder();
        description.append("FASTBURGER");
        if (orderDTO.getClientCPF() != null) {
            description.append(" \n | CPF: ").append(orderDTO.getClientCPF()).append(" \n ");
        }
        description.append(" STATUS: " + orderDTO.getStatus().statusItemOrder).append(" -- Nº ORDEM: ").append(orderDTO.getOrderNumber());
        paymentRequestDTO.setDescription(description.toString());
    }

    private String splitNome(String nomeComplete, Long parte) {
        String[] partiesDoNome = nomeComplete.split("\\s+", 2);
        if (parte == 0) {
            return partiesDoNome[1];
        } else {
            return partiesDoNome[0];
        }
    }
}
