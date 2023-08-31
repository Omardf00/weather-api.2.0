package com.umpalumpy.weather.model.weather_response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SuccessResponse {

    private Location location;

    private Current current;

}
