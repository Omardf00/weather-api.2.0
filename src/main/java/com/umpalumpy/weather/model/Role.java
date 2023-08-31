package com.umpalumpy.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    private int roleId;

    @NotNull
    @NotBlank
    @Column(length = 6)  //There will only be two roles available: Writer & Reader
    private String roleName;

}
