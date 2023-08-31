package com.umpalumpy.weather.controller;

import com.umpalumpy.weather.configuration.WeatherApiConfig;
import com.umpalumpy.weather.configuration.WeatherApiCredentials;
import com.umpalumpy.weather.model.weather_response.Error;
import com.umpalumpy.weather.model.weather_response.ErrorResponse;
import com.umpalumpy.weather.model.weather_response.SuccessResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather/v1/consult")
public class WeatherController {

    final
    WeatherApiConfig apiConfig;

    final
    WeatherApiCredentials credentials;

    public WeatherController(WeatherApiConfig apiConfig, WeatherApiCredentials credentials) {
        this.apiConfig = apiConfig;
        this.credentials = credentials;
    }

    @PostMapping
    public ResponseEntity<?> getWeather(@RequestParam(name = "Location") String location){

        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", credentials.getApiKey());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<SuccessResponse> responseOk;

        try {
                responseOk = apiConfig.restTemplate().exchange(
                    credentials.getFullUrl() + "&q=" + location,
                    HttpMethod.GET,
                    entity,
                    SuccessResponse.class
            );
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            assert errorResponse != null;
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse.getError());
        }

        return new ResponseEntity<>(responseOk.getBody(), HttpStatus.OK);

    }

}
