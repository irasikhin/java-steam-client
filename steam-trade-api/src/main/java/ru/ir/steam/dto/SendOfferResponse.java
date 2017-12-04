package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendOfferResponse {

    @JsonProperty("tradeofferid")
    private String tradeOfferId;

    @JsonProperty("needs_mobile_confirmation")
    private Boolean needsMobileConfirmation;

    @JsonProperty("needs_email_confirmation")
    private Boolean needsEmailConfirmation;

    @JsonProperty("email_domain")
    private String emailDomain;

    @JsonProperty("strError")
    private String strError;

}
