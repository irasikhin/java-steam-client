package ru.ir.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
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

}
