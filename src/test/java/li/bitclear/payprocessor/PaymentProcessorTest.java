package li.bitclear.payprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class PaymentProcessorTest {
    private PaymentProcessor processor;

    @BeforeEach
    void setup(){
        processor = new PaymentProcessor();
    }

    @Test
    void noTransfersReceived_thenStateNew() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, new BigDecimal("100.00"));
        Assertions.assertEquals(PaymentState.NEW, processor.processPayments(List.of(event1)));
    }

    @Test
    void partialAmountReceived_thenStatePartiallyPaid() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, new BigDecimal("100.00"));
        Event event2 = new Event(EventType.TRANSFER_RECEIVED, new BigDecimal("50.00"));
        Assertions.assertEquals(PaymentState.PARTIALLY_PAID, processor.processPayments(List.of(event1, event2)));
    }
    @Test
    void partialAmountReceived_thenStatePaid() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, new BigDecimal("100.00"));
        Event event2 = new Event(EventType.TRANSFER_RECEIVED, new BigDecimal("50.00"));
        Assertions.assertEquals(PaymentState.PAID, processor.processPayments(List.of(event1, event2, event2)));
    }

    @Test
    void paymentCancelled_thenStateCancelled() {
        Event event1 = new Event(EventType.PAYMENT_CREATED, new BigDecimal("100.00"));
        Event event2 = new Event(EventType.TRANSFER_RECEIVED, new BigDecimal("50.00"));
        Event event3 = new Event(EventType.PAYMENT_CANCELLED, null);
        Assertions.assertEquals(PaymentState.CANCELLED, processor.processPayments(List.of(event1, event2, event3)));
    }
}