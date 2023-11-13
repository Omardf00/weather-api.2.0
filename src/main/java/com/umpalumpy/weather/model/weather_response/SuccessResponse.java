package com.umpalumpy.weather.model.weather_response;

import lombok.Data;

@Data
public class SuccessResponse {

    private Location location;

    private Current current;

}
