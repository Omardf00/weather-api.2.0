package com.umpalumpy.weather.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SecretKey {

    @Value("0D7D63BFBFCD439511974E469264CB35F82F6A44431AB32521C7D31A44EACB58")
    private String secretKey;

}
