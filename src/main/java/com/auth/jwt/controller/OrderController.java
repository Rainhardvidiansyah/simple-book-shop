package com.auth.jwt.controller;

import com.auth.jwt.dto.request.OrderRequestDto;
import com.auth.jwt.dto.response.OrderReceiptResponseDto;
import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.dto.utils.ErrorUtils;
import com.auth.jwt.model.Order;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepo userRepo;

    @PostMapping("/create")
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> order(@RequestParam Long userid, @Valid @RequestBody OrderRequestDto orderRequestDto, Errors errors){
        if(errors.hasErrors()){
            return new ResponseEntity<>(generateFailedResponse(400, "POST", List.of(""), ErrorUtils.err(errors)), HttpStatus.BAD_REQUEST);
        }
        if(isUserIdValid(userid)){
            return new ResponseEntity<>(generateFailedResponse(400, "POST", List.of("User is not valid")), HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.makeAnOrder(userid, orderRequestDto.getPaymentMethod());
        Map<String, Object> maps = new HashMap<>();
        maps.put("Invoice", OrderReceiptResponseDto.From(order));
        return new ResponseEntity<>(generateSuccessResponse("POST", maps), HttpStatus.OK);
    }

    @GetMapping("/user")
    @ResponseBody
    @PreAuthorize("#userid == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOrder(@RequestParam Long userid){
        var user = userRepo.findById(userid);
        var allOrder = orderService.listOrders(user.get());
        List<OrderReceiptResponseDto> orderReceiptResponses = new ArrayList<>();
        for(Order order: allOrder){
            orderReceiptResponses.add(OrderReceiptResponseDto.From(order));
        }
        return new ResponseEntity<>(generateSuccessResponse("GET", orderReceiptResponses), HttpStatus.OK);
    }
    private ResponseEntity<?> headerResponses(String number_order, String totalCost, String payment_method){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Order number:", number_order);
        headers.add("Total Cost:", totalCost);
        headers.add("Payment Method: ", payment_method);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    private static boolean isUserIdValid(Long userId){
        return !userId.equals("") && userId==null;
    }


    private ResponseMessage<Object> generateSuccessResponse(String method, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(200);
        responseMessage.setMethod(method);
        responseMessage.setMessage(List.of("Success"));
        responseMessage.setData(object);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(int code, String method, List<String> message){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(null);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }

    private ResponseMessage<Object> generateFailedResponse(int code, String method, List<String> message, Object object){
        var responseMessage = new ResponseMessage<Object>();
        responseMessage.setCode(code);
        responseMessage.setMethod(null);
        responseMessage.setMessage(message);
        responseMessage.setData(object);
        return responseMessage;
    }
}
