package com.auth.jwt.dto.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CostDataMaster {

    @JsonProperty("origin_details")
    private OriginDetails originDetails;

    @JsonProperty("destination_details")
    private DestinationDetails destinationDetails;
}
