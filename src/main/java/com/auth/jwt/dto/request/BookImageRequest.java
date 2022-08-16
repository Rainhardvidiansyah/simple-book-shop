package com.auth.jwt.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class BookImageRequest {

    private byte[] imageData;
    private String imageName;
    private String description;
}
