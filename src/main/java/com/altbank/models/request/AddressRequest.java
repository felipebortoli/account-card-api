package com.altbank.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressRequest {
    @NotNull(message = "Street is mandatory")
    private String street;

    @NotNull(message = "City is mandatory")
    private String city;

    @NotNull(message = "State is mandatory")
    private String state;

    @NotNull(message = "ZipCode is mandatory")
    private String zipCode;

    @NotNull(message = "Country is mandatory")
    private String country;

    private String complement;

    @NotNull(message = "Number is mandatory")
    private String number;

    public AddressRequest(String newStreet, String newCity, String sc, String number, String brasil, String s, String number1) {
    }
}
