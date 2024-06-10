package li.bitclear.payprocessor;

import java.math.BigDecimal;
import java.util.List;

public class PaymentProcessor {

    public Payment processPayments(List<Event> events) {
        BigDecimal totalExpected = BigDecimal.ZERO;
        BigDecimal totalReceived = BigDecimal.ZERO;

        for (Event event : events) {
            switch (event.getEventType()) {
                case PAYMENT_CREATED -> totalExpected = totalExpected.add(event.getAmount());
                case TRANSFER_RECEIVED -> totalReceived = totalReceived.add(event.getAmount());
                case PAYMENT_CANCELLED -> {
                    return new Payment(PaymentState.CANCELLED, totalExpected, totalReceived);
                }
            }
        }

        if (totalReceived.compareTo(totalExpected) >= 0) {
            Payment payment =  new Payment();
            payment.setState(PaymentState.CANCELLED);
            return new Payment(PaymentState.PAID, totalExpected, totalReceived);
        } else if (totalReceived.compareTo(BigDecimal.ZERO) > 0) {
            return new Payment(PaymentState.PARTIALLY_PAID, totalExpected, totalReceived);
        } else {
            return new Payment(PaymentState.NEW, totalExpected, totalReceived);
        }
    }
}
