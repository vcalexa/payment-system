package li.bitclear.payprocessor;

import java.math.BigDecimal;
import java.util.List;

public class PaymentProcessor {

    public PaymentState processPayments(List<Event> events) {
        BigDecimal totalExpected = BigDecimal.ZERO;
        BigDecimal totalReceived = BigDecimal.ZERO;

        for (Event event : events) {
            switch (event.getEventType()) {
                case PAYMENT_CREATED -> totalExpected = totalExpected.add(event.getAmount());
                case TRANSFER_RECEIVED -> totalReceived = totalReceived.add(event.getAmount());
                case PAYMENT_CANCELLED -> {
                    return PaymentState.CANCELLED;
                }
            }
        }

        if (totalReceived.compareTo(totalExpected) >= 0) {
            return PaymentState.PAID;
        } else if (totalReceived.compareTo(BigDecimal.ZERO) > 0) {
            return PaymentState.PARTIALLY_PAID;
        } else {
            return PaymentState.NEW;
        }
    }
}
