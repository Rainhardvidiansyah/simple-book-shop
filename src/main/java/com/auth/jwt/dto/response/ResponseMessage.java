package com.auth.jwt.dto.response;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter @ToString
public class ResponseMessage<T> {
    private int code;
    private String method;
    private List<String> message = new ArrayList<>();
    private T data;


}
