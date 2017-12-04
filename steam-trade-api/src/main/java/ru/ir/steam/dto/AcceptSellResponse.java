package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AcceptSellResponse implements Serializable {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("requires_confirmation")
    private int requiresConfirmation;

    @JsonProperty("needs_mobile_confirmation")
    private Boolean needsMobileConfirmation;

    @JsonProperty("needs_email_confirmation")
    private Boolean needsEmailConfirmation;

    @JsonProperty("email_domain")
    private String emailDomain;

    @JsonProperty("strError")
    private String strError;

}
