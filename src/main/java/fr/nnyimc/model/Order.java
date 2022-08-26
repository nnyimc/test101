package fr.nnyimc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Boolean paid;
    private LocalDateTime date;
    private Double value;
    public Order(long id, boolean paid) {
        this.id = id;
        this.paid = paid;
    }

    public Order(long id, LocalDateTime date, double value, boolean paid) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.paid = paid;
    }

    public Order markPaid() {
        paid = true;
        return this;
    }

}
