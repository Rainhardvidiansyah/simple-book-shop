package com.auth.jwt.dto.shipping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OneProvincePojo {

    @JsonProperty("rajaongkir")
    private RajaOngkir rajaOngkir;
}
