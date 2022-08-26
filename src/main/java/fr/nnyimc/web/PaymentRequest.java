package fr.nnyimc.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PaymentRequest {

    @NonNull
    private String creditCardNumber;

}
