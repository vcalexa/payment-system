package li.bitclear.payprocessor;

import lombok.*;

import java.math.BigDecimal;

@Value
public class Event {
    EventType eventType;
    BigDecimal amount;
}
