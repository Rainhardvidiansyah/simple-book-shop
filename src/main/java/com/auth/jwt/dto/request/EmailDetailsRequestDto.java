package com.auth.jwt.dto.request;


import lombok.*;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class EmailDetailsRequestDto {

    private String to;
    private String from;
    private String emailBody;
    private Date date;
}
