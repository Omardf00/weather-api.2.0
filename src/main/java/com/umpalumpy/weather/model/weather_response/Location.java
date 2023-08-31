package com.umpalumpy.weather.model.weather_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

    private String name;

    private String region;

    private String country;

    private String lat;

    private String lon;

    @JsonProperty("tz_id")
    private String tzId;

    @JsonProperty("localtime_epoch")
    private String localTimeEpoch;

    @JsonProperty("localtime")
    private String localTime;

}
