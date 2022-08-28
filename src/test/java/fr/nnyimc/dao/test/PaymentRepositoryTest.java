package fr.nnyimc.dao.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import fr.nnyimc.dao.PaymentRepository;
import fr.nnyimc.model.Order;
import fr.nnyimc.model.Payment;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = { "spring.datasource.url=jdbc:tc:postgresql:13.2-alpine://payment" })
public class PaymentRepositoryTest {
	
	
	private PaymentRepository paymentRepository;
	private TestEntityManager testEntityManager;
	
	@Autowired
	public void setPaymentRepository(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	@Autowired
	public void setTestEntityManager(TestEntityManager testEntityManager) {
		this.testEntityManager = testEntityManager;
	}
	
	@Test
	void madePayment_canBeFoundInDatabase() {
		
		// GIVEN
		Order order = new Order(LocalDateTime.now(), BigDecimal.valueOf(250.25).doubleValue(), true);
		Payment payment = new Payment(order, "4785652365110100");
		
		Long orderId = testEntityManager.persist(order).getId();
		testEntityManager.persistAndFlush(payment);
		testEntityManager.clear();
		
		// WHEN
		Optional<Payment> madePayment = paymentRepository.findByOrderId(orderId);
		
		// THEN
		assertThat(madePayment).isPresent();
		
	}
	
	@Test
	void madePayment_isSavedAsPaid() {
		
		// GIVEN
		Order order = new Order(LocalDateTime.now(), BigDecimal.valueOf(541.20).doubleValue(), true);
		Payment payment = new Payment(order, "4150236620221411");
		
		Long orderId = testEntityManager.persist(order).getId();
		testEntityManager.persistAndFlush(payment);
		testEntityManager.clear();
		
		// WHEN
		Optional<Payment> madePayment = paymentRepository.findByOrderId(orderId);
		
		// THEN
		assertThat(madePayment.get().getOrder().getPaid()).isTrue();

	}
}
