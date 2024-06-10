package li.bitclear.payprocessor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private final UUID id;

    private BigDecimal totalExpected = BigDecimal.ZERO;
    private BigDecimal totalReceived = BigDecimal.ZERO;
    private PaymentState state;

    public Payment() {
        this.id = UUID.randomUUID();
    }

    public Payment(PaymentState state, BigDecimal totalExpected, BigDecimal totalReceived) {
        this.id = UUID.randomUUID();
        this.totalExpected = totalExpected;
        this.totalReceived = totalReceived;
        this.state = state;
    }

}
