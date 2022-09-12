package com.auth.jwt.dto.shipping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CityResponse {

    private String city_id;
    private String city_name;

    public static CityResponse From(Results results){
        return new CityResponse(results.getCity_id(), results.getCity_name());
    }

}
