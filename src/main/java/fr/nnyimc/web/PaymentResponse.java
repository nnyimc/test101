package fr.nnyimc.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private String creditCardNumber;
}
