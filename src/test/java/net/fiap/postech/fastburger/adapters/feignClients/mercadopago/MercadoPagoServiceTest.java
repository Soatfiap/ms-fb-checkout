package net.fiap.postech.fastburger.adapters.feignClients.mercadopago;

import net.fiap.postech.fastburger.adapters.feignClients.dto.PaymentDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.OrderDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.PaymentMethodDTO;
import net.fiap.postech.fastburger.adapters.persistence.dto.enumerations.PayMentMethodEnum;
import net.fiap.postech.fastburger.application.domain.enums.StatusOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MercadoPagoServiceTest {

    @Mock
    private MercadoPagoFeignClient mercadoPagoFeignClient;

    @InjectMocks
    private MercadoPagoService mercadoPagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should generate QRCode")
    void testGenerateQRCode() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber("12345");
        orderDTO.setTotalValue(new BigDecimal("100"));
        orderDTO.setStatus(StatusOrder.INPREPARATION);
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setMethod(PayMentMethodEnum.CARD);
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        when(mercadoPagoFeignClient.generateQRCode(any(), any(), any())).thenReturn(ResponseEntity.ok(paymentDTO));
        PaymentDTO result = mercadoPagoService.generateQRCode(orderDTO, paymentMethodDTO);
        assertEquals(paymentDTO.getId(), result.getId());
    }
}