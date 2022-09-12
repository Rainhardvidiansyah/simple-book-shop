package com.auth.jwt.dto.shipping;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {

        @JsonProperty("city_id")
        private String city_id;

        @JsonProperty("province_id")
        private String province_id;

        @JsonProperty("province")
        private String province;

        @JsonProperty("type")
        private String type;
        @JsonProperty("city_name")
        private String city_name;

        @JsonProperty("postal_code")
        private String postal_code;

}
