package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class QueryTimeResponse implements Serializable {

    @JsonProperty("server_time")
    private String serverTime;

    @JsonProperty("skew_tolerance_seconds")
    private String skewToleranceSeconds;

    @JsonProperty("large_time_jink")
    private String largeTimeJink;

    @JsonProperty("probe_frequency_seconds")
    private Long probeFrequencySeconds;

    @JsonProperty("adjusted_time_probe_frequency_seconds")
    private Long adjustedTimeProbeFrequencySeconds;

    @JsonProperty("hint_probe_frequency_seconds")
    private Long hintProbeFrequencySeconds;

    @JsonProperty("sync_timeout")
    private Long syncTimeout;

    @JsonProperty("try_again_seconds")
    private Long tryAgainSeconds;

    @JsonProperty("max_attempts")
    private Integer maxAttempts;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getSkewToleranceSeconds() {
        return skewToleranceSeconds;
    }

    public void setSkewToleranceSeconds(String skewToleranceSeconds) {
        this.skewToleranceSeconds = skewToleranceSeconds;
    }

    public String getLargeTimeJink() {
        return largeTimeJink;
    }

    public void setLargeTimeJink(String largeTimeJink) {
        this.largeTimeJink = largeTimeJink;
    }

    public Long getProbeFrequencySeconds() {
        return probeFrequencySeconds;
    }

    public void setProbeFrequencySeconds(Long probeFrequencySeconds) {
        this.probeFrequencySeconds = probeFrequencySeconds;
    }

    public Long getAdjustedTimeProbeFrequencySeconds() {
        return adjustedTimeProbeFrequencySeconds;
    }

    public void setAdjustedTimeProbeFrequencySeconds(Long adjustedTimeProbeFrequencySeconds) {
        this.adjustedTimeProbeFrequencySeconds = adjustedTimeProbeFrequencySeconds;
    }

    public Long getHintProbeFrequencySeconds() {
        return hintProbeFrequencySeconds;
    }

    public void setHintProbeFrequencySeconds(Long hintProbeFrequencySeconds) {
        this.hintProbeFrequencySeconds = hintProbeFrequencySeconds;
    }

    public Long getSyncTimeout() {
        return syncTimeout;
    }

    public void setSyncTimeout(Long syncTimeout) {
        this.syncTimeout = syncTimeout;
    }

    public Long getTryAgainSeconds() {
        return tryAgainSeconds;
    }

    public void setTryAgainSeconds(Long tryAgainSeconds) {
        this.tryAgainSeconds = tryAgainSeconds;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}
