package com.altbank.models.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Document number is mandatory")
    private String documentNumber;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Address is mandatory")
    private AddressRequest address;

}
