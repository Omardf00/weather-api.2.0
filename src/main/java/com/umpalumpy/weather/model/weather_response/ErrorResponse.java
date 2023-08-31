package com.umpalumpy.weather.model.weather_response;

import lombok.Data;

@Data
public class ErrorResponse {

    private Error error;

    public Boolean isEmpty(){
        try{
            return this.error== null;
        } catch (Exception e) {
            return true;
        }

    }

}
