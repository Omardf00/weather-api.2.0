package com.umpalumpy.weather.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SecretKey {

    @Value("MS649eV41fvCWnK8NekOa3VAiBQemlI4")
    private String secretKey;

}
