package com.umpalumpy.weather.model.weather_response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SuccessResponse {

    private Location location;

    private Current current;

    public Boolean isEmpty(){
        try{
            return this.location == null || this.current == null;
        } catch (Exception e) {
            return true;
        }

    }

}
