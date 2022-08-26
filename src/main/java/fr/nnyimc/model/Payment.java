package fr.nnyimc.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @NonNull
    private Order order;

    @NonNull
    private String creditCardNumber;

    public Payment(long id, Order order, String creditCardNumber) {
        this.id = id;
        this.order = order;
        this.creditCardNumber = creditCardNumber;
    }
}
