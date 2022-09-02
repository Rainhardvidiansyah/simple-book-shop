package com.auth.jwt.dto.request;


import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class EmailDetailsRequestDto {

    private String to;
    private String from;
    private String subject;
    private String emailBody;
    private Object object;
    private String attachment;
}
