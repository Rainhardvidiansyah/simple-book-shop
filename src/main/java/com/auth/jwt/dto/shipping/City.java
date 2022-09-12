package com.auth.jwt.dto.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
public class City {


    @JsonProperty("rajaongkir")
    private RajaOngkir rajaOngkir;
}
