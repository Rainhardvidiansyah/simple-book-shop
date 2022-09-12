package com.auth.jwt.controller;


import com.auth.jwt.dto.shipping.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private static final String api_key = "cc4b19f3765051144c33953f64278067";
    private static final String baseUrl = "https://api.rajaongkir.com/starter/province";
    private static final String baseUrl2 = "https://api.rajaongkir.com/starter/city?city_id=";
    private final RestTemplate restTemplate;


    @GetMapping("/province")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getProvinceData(@RequestParam String id) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("key", api_key);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> data = restTemplate.exchange(baseUrl+"?id="+id, HttpMethod.GET, entity, JsonNode.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        OneProvincePojo oneProvincePojo = objectMapper.readValue(data.getBody().toString(),
                OneProvincePojo.class);
        List<Object> resultsList = oneProvincePojo.getRajaOngkir()
                .getResults().stream()
                .map(x -> {
                    return OneProvinceResponse.From(x);
                })
                .collect(Collectors.toList());
        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(resultsList), HttpStatus.OK);
    }


    @GetMapping("/all-provinces")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllProvinceData() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("key", api_key);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> data = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, JsonNode.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(String.valueOf(data.getBody()));
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        ShippingDataMaster shippingData = objectMapper.treeToValue(node, ShippingDataMaster.class);
        return new ResponseEntity<>(shippingData, HttpStatus.OK);
    }

    @GetMapping("/all-city")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity getAllCityData() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("key", api_key);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> data = restTemplate.exchange("https://api.rajaongkir.com/starter/city", HttpMethod.GET, entity, JsonNode.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(String.valueOf(data.getBody()));
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        var city = objectMapper.treeToValue(node, City.class);
        List<CityResponse> cityList = city.getRajaOngkir().getResults().stream()
                .map(city_data -> {
                   return CityResponse.From(city_data);
                })
                .collect(Collectors.toList());
        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(cityList), HttpStatus.OK);
    }


    @GetMapping("/one-city")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getOneCityData(String id) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("key", api_key);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> data = restTemplate.exchange("https://api.rajaongkir.com/starter/city?id="+id, HttpMethod.GET, entity, JsonNode.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(String.valueOf(data.getBody()));
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        var city = objectMapper.treeToValue(node, City.class);
        List<CityResponse> cityList = city.getRajaOngkir().getResults().stream()
                .map(city_data -> {
                    return CityResponse.From(city_data);
                })
                .collect(Collectors.toList());
        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(cityList), HttpStatus.OK);
    }
}
