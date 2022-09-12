package com.auth.jwt.dto.shipping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor @Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class RajaOngkir {


    @JsonIgnore
    private Query query;
    @JsonIgnore
    private Status status;

//    private OriginDetails originDetails;
//
//    private DestinationDetails destinationDetails;

    //jangan lupa diganti lagi dengan Results, tanpa List<E>
    private List<Results> results = new ArrayList<>();
}
