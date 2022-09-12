package com.auth.jwt.dto.shipping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class OneProvinceResponse {

    private String province_id;
    private String province_name;

    public OneProvinceResponse(String province_id, String province_name) {
        this.province_id = province_id;
        this.province_name = province_name;
    }

    public static OneProvinceResponse From(Results results){
        return new OneProvinceResponse(results.getProvince_id(), results.getProvince());
    }

}
