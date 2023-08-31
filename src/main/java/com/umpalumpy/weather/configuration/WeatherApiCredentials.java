package com.umpalumpy.weather.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class WeatherApiCredentials {

    @Value("${weather_api_url}")
    private String apiUrl;

    @Value("${weather_api_key}")
    private String apiKey;

    public String getFullUrl() {
        return this.apiUrl + this.apiKey;
    }

}
