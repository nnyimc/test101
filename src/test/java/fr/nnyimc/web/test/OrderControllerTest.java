package fr.nnyimc.web.test;

import fr.nnyimc.model.Order;
import fr.nnyimc.model.Payment;
import fr.nnyimc.services.OrderService;
import fr.nnyimc.web.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void payOrderPostRequest_shouldCreateOrder() throws Exception {

        // Given
        Order order = new Order(1L, LocalDateTime.now(), 100.0, false);
        Payment payment = new Payment(10L, order, "4500231223329876");
        when(orderService.pay(any(), any())).thenReturn(payment);

        // When

        // Then
        mockMvc.perform(post("/order/{id}/payment", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"creditCardNumber\":\"4500231223329876\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void emptyCreditCardNumber_shouldBlockPayment() throws Exception {
        mockMvc.perform(post("/order/{id}/payment", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
