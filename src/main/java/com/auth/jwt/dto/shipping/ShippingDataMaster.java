package com.auth.jwt.dto.shipping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingDataMaster {

    @JsonProperty("rajaongkir")
    private RajaOngkir rajaOngkir;
}
