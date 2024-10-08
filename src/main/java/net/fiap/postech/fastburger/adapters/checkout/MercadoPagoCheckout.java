package net.fiap.postech.fastburger.adapters.checkout;

import net.fiap.postech.fastburger.adapters.checkout.dto.PayMentProcess;
import net.fiap.postech.fastburger.adapters.configuration.authentication.AuthenticationService;
import net.fiap.postech.fastburger.adapters.feignClients.mercadopago.MercadoPagoService;
import net.fiap.postech.fastburger.adapters.feignClients.dto.PaymentDTO;
import net.fiap.postech.fastburger.adapters.feignClients.dto.PaymentDataProcess;
import net.fiap.postech.fastburger.adapters.feignClients.dto.PaymentStatus;
import net.fiap.postech.fastburger.adapters.feignClients.order.MsFbOrderFeignClientService;
import net.fiap.postech.fastburger.adapters.persistence.dto.PaymentDataDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.PaymentMethodDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.enumerations.PayMentMethodEnum;
import net.fiap.postech.fastburger.adapters.persistence.mapper.OrderMapper;
import net.fiap.postech.fastburger.application.domain.Order;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class MercadoPagoCheckout implements CheckoutContract {
    private final MercadoPagoService mercadoPagoService;
    private final MsFbOrderFeignClientService msFbOrderFeignClientService;
    private final OrderMapper orderMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MercadoPagoCheckout(MercadoPagoService mercadoPagoService,
                               AuthenticationService authenticationService, MsFbOrderFeignClientService msFbOrderFeignClientService, OrderMapper orderMapper, RabbitTemplate rabbitTemplate
    ) {
        this.mercadoPagoService = mercadoPagoService;
        this.msFbOrderFeignClientService = msFbOrderFeignClientService;
        this.orderMapper = orderMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public PaymentDataDTO payOrder(String orderNumber, PaymentMethodDTO paymentMethodDTO) {
        PayMentProcess process = new PayMentProcess(orderNumber, paymentMethodDTO.getMethod().getType(), true);
        this.rabbitTemplate.convertAndSend("orderExchange", "order.created", process.toString());
        return getPaymentDataDTO(orderNumber, paymentMethodDTO);
    }

    @Override
    public PaymentStatus paymentStatys(String orderNumber) {
        return PaymentStatus.builder()
                .orderId(orderNumber)
                .paymentWasApproved(this.msFbOrderFeignClientService.findOrderByNumber(orderNumber).getWasPaid())
                .build();
    }

    @Override
    public void processFallbackPayment(PaymentDataProcess paymentDataProcess, String token) {
        PayMentProcess process = new PayMentProcess(paymentDataProcess.getOrderId(), null, paymentDataProcess.isPaymentWasApproved());
        this.rabbitTemplate.convertAndSend("orderExchange", "order.created", process.toString());
        Order order = this.orderMapper.orderDTOToOrder(this.msFbOrderFeignClientService.findOrderByNumber(paymentDataProcess.getOrderId()));
        Order update = this.orderMapper.orderDTOToOrder(this.msFbOrderFeignClientService.updateStatusOrder(paymentDataProcess.getOrderId(), true));
    }

    private PaymentDataDTO getPaymentDataDTO(String orderNumber, PaymentMethodDTO paymentMethodDTO) {
        PaymentDataDTO paymentDataDTO;
        if (PayMentMethodEnum.CASH.name().equals(paymentMethodDTO.getMethod().getType())) {
            paymentDataDTO = PaymentDataDTO.builder()
                    .ticketUrl("was paid with cash")
                    .method("card")
                    .QRCode("was paid with cash")
                    .createDate(Date.from(Instant.now()))
                    .operationType("regular_payment")
                    .checkoutId(orderNumber.concat(paymentMethodDTO.getMethod().getType().length() + "/G44"))
                    .method(paymentMethodDTO.getMethod().name())
                    .build();
        } else if (PayMentMethodEnum.CARD.name().equals(paymentMethodDTO.getMethod().getType())) {
            paymentDataDTO = PaymentDataDTO.builder()
                    .method("cash")
                    .ticketUrl("was paid with card")
                    .createDate(Date.from(Instant.now()))
                    .operationType("regular_payment")
                    .QRCode("was paid with card")
                    .method(paymentMethodDTO.getMethod().name())
                    .build();
        } else {
            paymentDataDTO = payWithPixOrCard(orderNumber, paymentMethodDTO);
        }
        return paymentDataDTO;
    }

    private PaymentDataDTO payWithPixOrCard(String orderNumber, PaymentMethodDTO paymentMethodDTO) {
        PaymentDTO paymentDTO;
        try {
            Order order = this.orderMapper.orderDTOToOrder(this.msFbOrderFeignClientService.findOrderByNumber(orderNumber));
            order.setStatus(StatusOrder.INPREPARATION);
            paymentDTO = this.mercadoPagoService.generateQRCode(this.orderMapper.orderToOrderDTO(order), paymentMethodDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return PaymentDataDTO.builder()
                .method(paymentDTO.getPaymentMethodId() + " /MERCADO_PAGO")
                .checkoutId(paymentDTO.getId().toString())
                .createDate(paymentDTO.getDateCreated())
                .lastUpdateDate(paymentDTO.getDateLastUpdated())
                .moneyReleaseStatus(paymentDTO.getMoneyReleaseStatus())
                .expirationDate(paymentDTO.getDateOfExpiration())
                .operationType(paymentDTO.getOperationType())
                .QRCode(paymentDTO.getPointOfInteractionDTO().getTransactionDataDTO().getQrCode())
                .ticketUrl(paymentDTO.getPointOfInteractionDTO().getTransactionDataDTO().getTicketUrl())
                .build();
    }
}
