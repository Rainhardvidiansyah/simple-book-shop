package com.auth.jwt.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter @Setter @ToString
public class ResponseMessage<T> {

    private int code;

    private List<String> message = new ArrayList<>();

    private T data;
}
