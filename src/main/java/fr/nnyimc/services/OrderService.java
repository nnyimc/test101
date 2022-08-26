package fr.nnyimc.services;

import fr.nnyimc.exceptions.PaymentException;
import fr.nnyimc.model.Order;
import fr.nnyimc.model.Payment;
import fr.nnyimc.dao.OrderRepository;
import fr.nnyimc.dao.PaymentRepository;

import javax.persistence.EntityNotFoundException;

public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment pay(long orderId, String creditCardNumber) {

        Order fetchedOrder = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        if(fetchedOrder.getPaid()) {
            throw new PaymentException();
        }

        orderRepository.save(fetchedOrder.markPaid());

        return new Payment(fetchedOrder, creditCardNumber);
    }
}
