package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AcceptResponse implements Serializable {

    @JsonProperty("tradeid")
    private Long tradeId;

    @JsonProperty("needs_mobile_confirmation")
    private Boolean needsMobileConfirmation;

    @JsonProperty("needs_email_confirmation")
    private Boolean needsEmailConfirmation;

    @JsonProperty("email_domain")
    private String emailDomain;

    @JsonProperty("strError")
    private String strError;

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Boolean getNeedsMobileConfirmation() {
        return needsMobileConfirmation;
    }

    public void setNeedsMobileConfirmation(Boolean needsMobileConfirmation) {
        this.needsMobileConfirmation = needsMobileConfirmation;
    }

    public Boolean getNeedsEmailConfirmation() {
        return needsEmailConfirmation;
    }

    public void setNeedsEmailConfirmation(Boolean needsEmailConfirmation) {
        this.needsEmailConfirmation = needsEmailConfirmation;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }
}
