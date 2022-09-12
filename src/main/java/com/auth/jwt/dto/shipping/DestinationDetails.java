package com.auth.jwt.dto.shipping;


import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class DestinationDetails {

    private String city_id;
    private String province_id;
    private String type;
    private String city_name;
    private String postal_code;
}
