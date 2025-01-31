package com.altbank.models.request;

import com.altbank.models.enums.Reason;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReissuePhysicalCardRequest {
    private String cardNumber;
    private Reason reason;
    private AddressRequest address;
}
