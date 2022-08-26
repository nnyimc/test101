package fr.nnyimc.services.test;

import fr.nnyimc.exceptions.PaymentException;
import fr.nnyimc.model.Order;
import fr.nnyimc.model.Payment;
import fr.nnyimc.dao.OrderRepository;
import fr.nnyimc.services.OrderService;
import fr.nnyimc.dao.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private OrderService orderService;

    @BeforeEach
    void setupService() {
        orderRepository = mock(OrderRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        orderService = new OrderService(orderRepository, paymentRepository);
    }

    @Test
    void payingOrder_shouldAffectThePaymentStatus() {

        // Given
        Order order = new Order(1L, false);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any())).then(returnsFirstArg());

        // When
        Payment payment = orderService.pay(1L, "4523234598654432312");

        // Then
        assertThat( payment.getOrder().getPaid().booleanValue() ).isTrue();

    }

    @Test
    void payingOrder_shouldRegisterCreditCardNumber() {

        // Given
        Order order = new Order( 1L, false);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any())).then(returnsFirstArg());

        // When
        Payment payment = orderService.pay(1L, "4523234598654432312");

        // Then
        assertThat( payment.getCreditCardNumber() ).isEqualTo("4523234598654432312");

    }

    @Test
    void payingTwiceTheSameOrder_shouldNotBePossible() {

        // Given
        Order paidOrder = new Order(1L, true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(paidOrder));

        // Then
        assertThrows(PaymentException.class, () -> orderService.pay(paidOrder.getId(), "4523234598654432312"));

    }

}
