package com.umpalumpy.weather.model.weather_response;

import lombok.Data;

@Data
public class Error {

    private String code;

    private String message;

}
