package li.bitclear.payprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class PaymentProcessorTest {
    private PaymentProcessor processor;
    private static final BigDecimal PAYMENT100 = new BigDecimal("100.00");
    private static final BigDecimal PAYMENT50 = new BigDecimal("50.00");

    @BeforeEach
    void setup(){
        processor = new PaymentProcessor();
    }

    @Test
    void noTransfersReceived_thenStateNew() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, PAYMENT100);
        Payment payment = new Payment(PaymentState.NEW, PAYMENT100, BigDecimal.ZERO);
        Assertions.assertEquals(payment, processor.processPayments(List.of(event1)));
    }

    @Test
    void partialAmountReceived_thenStatePartiallyPaid() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, PAYMENT100);
        Event event2 = new Event(EventType.TRANSFER_RECEIVED, PAYMENT50);
        Payment payment = new Payment(PaymentState.PARTIALLY_PAID, PAYMENT100, PAYMENT50);
        Assertions.assertEquals(payment, processor.processPayments(List.of(event1, event2)));
    }
    @Test
    void partialAmountReceived_thenStatePaid() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, PAYMENT100);
        Event event2 = new Event(EventType.TRANSFER_RECEIVED, PAYMENT50);
        Payment payment = new Payment(PaymentState.PAID, PAYMENT100, PAYMENT100);
        Assertions.assertEquals(payment, processor.processPayments(List.of(event1, event2, event2)));
    }

    @Test
    void paymentCancelled_thenStateCancelled() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, new BigDecimal("100.00"));
        Event event2 = new Event(EventType.TRANSFER_RECEIVED, new BigDecimal("50.00"));
        Event event3 = new Event(EventType.PAYMENT_CANCELLED, null);
        Payment payment = new Payment(PaymentState.CANCELLED, PAYMENT100, PAYMENT50);
        Assertions.assertEquals(payment, processor.processPayments(List.of(event1, event2, event3)));
    }
}