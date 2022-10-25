package com.auth.jwt.controller;

import com.auth.jwt.dto.request.OrderNumberRequestDto;
import com.auth.jwt.dto.request.VerifyOrderRequestDto;
import com.auth.jwt.dto.response.AdminOrderResponse;
import com.auth.jwt.dto.response.AdminTransactionResponse;
import com.auth.jwt.dto.response.ResponseMessage;
import com.auth.jwt.model.Order;
import com.auth.jwt.model.Transaction;
import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final HttpServletRequest servletRequest;
    private final UserRepo userRepo;

    @GetMapping("/all-order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllOrder(){
        String email = servletRequest.getUserPrincipal().getName();
        var user = userRepo.findAppUserByEmail(email);
        if(user.isEmpty()){
            return new ResponseEntity<>(generateFailedResponse(403, "GET", List.of("USER NOT VALID")),
                    HttpStatus.BAD_GATEWAY);
        }
        List<AdminOrderResponse> adminOrderResponses = new ArrayList<>();
        var allOrder = adminService.getAllOrder();
        for(Order order: allOrder){
            adminOrderResponses.add(AdminOrderResponse.from(order));
        }
        if(allOrder.isEmpty()){
            return new ResponseEntity<>(generateFailedResponse(404, "GET", List.of("No Order")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(generateSuccessResponse("GET", adminOrderResponses), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findId(@PathVariable("id")String id){
        var orderNumber = adminService.findOrderNumber(id);
        return new ResponseEntity<>(generateSuccessResponse("GET", AdminOrderResponse.from(orderNumber)), HttpStatus.OK);
    }

    @GetMapping("/true-order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findOrderedTrue(){
        var orderedTrue = adminService.findOrderedTrue();
        List<AdminOrderResponse> adminOrderResponses = new ArrayList<>();
        for(Order order : orderedTrue){
            adminOrderResponses.add(AdminOrderResponse.from(order));
        }
        return new ResponseEntity<>(generateSuccessResponse("GET", adminOrderResponses), HttpStatus.OK);
    }

    @GetMapping("/false-order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findOrderedFalse(){
        var orderedTrue = adminService.findOrderedFalse();
        List<AdminOrderResponse> adminOrderResponses = new ArrayList<>();
        for(Order order : orderedTrue){
            adminOrderResponses.add(AdminOrderResponse.from(order));
        }
        return new ResponseEntity<>(generateSuccessResponse("GET", adminOrderResponses), HttpStatus.OK);
    }

    @GetMapping("/all-transaction")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllTransaction(){
        var transactions = adminService.getAllTransaction();
        List<AdminTransactionResponse> adminTransactionResponse = new ArrayList<>();
        for(Transaction transaction: transactions){
            adminTransactionResponse.add(AdminTransactionResponse.from(transaction));
        }
        if(transactions.isEmpty()){
            return new ResponseEntity<>(generateFailedResponse(404, "GET", List.of("No Transaction")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(generateSuccessResponse("GET", adminTransactionResponse), HttpStatus.OK);
    }

    @PostMapping("/verify-order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> verifyOrderByAdmin(@RequestBody VerifyOrderRequestDto verifyOrderRequestDto){
        adminService.verifyOrder(verifyOrderRequestDto.getOrderNumber(), verifyOrderRequestDto.isOrderedStatus());
        return new ResponseEntity<>(generateSuccessResponse("POST", "Order Confirmed"), HttpStatus.OK);
    }

    @PostMapping("/order-number")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchOrderNumber(@RequestBody OrderNumberRequestDto orderNumberRequestDto){
        var order = adminService.findOrderNumber(orderNumberRequestDto.getOrderNumber());
        if(order == null){
            return new ResponseEntity<>(generateFailedResponse(404, "POST", List.of("Order Not Found")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(generateSuccessResponse("POST", AdminOrderResponse.from(order)), HttpStatus.OK);
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
        responseMessage.setMethod(method);
        responseMessage.setMessage(message);
        responseMessage.setData(null);
        return responseMessage;
    }


}
