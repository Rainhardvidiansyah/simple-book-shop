package com.auth.jwt.dto.shipping;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CostBody {

    private String destination_Id;
    private int weight;
    private String courier;
}
