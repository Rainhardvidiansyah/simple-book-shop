package com.auth.jwt.dto.response;

import com.auth.jwt.model.History;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class HistoryResponseDto {

    private Long userId;
    private Long history_id;
    private String orderNumber;
    private Date orderDate;
    private List<String> book_collection = new ArrayList<>();

    public static HistoryResponseDto from(History history){
        return new HistoryResponseDto(
                history.getUser().getId(),
                history.getId(),
                history.getOrder().getId(),
                history.getOrder().getCreatedDate(),
                history.getOrder().getOrderItems()
                        .stream().map(item -> item.getBook().getTitle()).collect(Collectors.toList()));
    }

}
