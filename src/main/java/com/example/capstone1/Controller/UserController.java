package com.example.capstone1.Controller;


import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if(userService.addUser(user))return ResponseEntity.status(201).body(new ApiResponse("User is added"));
        else return ResponseEntity.status(400).body(new ApiResponse("User is duplicated"));

    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id , @RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if(userService.updateUser(id,user)) return ResponseEntity.ok(new ApiResponse("user is updated"));
        else return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id ){

        if(userService.deleteUser(id)) return ResponseEntity.ok(new ApiResponse("user is deleted"));
        else return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }
    // Extra endpoint 5
    //This endpoint allows a user to initiate a transfer request to another user, specifying the amount, reason, and other transfer details.
    @PutMapping("/request-transfer-amount")
    public ResponseEntity requestTransferAmount(@RequestBody Map<String, Object> transferRequest){
        String result = userService.requestTransfer(transferRequest);
        if (result.equalsIgnoreCase("success")) return ResponseEntity.ok(new ApiResponse(result));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }
    // Extra endpoint 6
    //This endpoint allows an admin to update the status of a transfer request made by a user. The request can either be approved or rejected based on the admin's action.
    @PutMapping("/update-request-status/{adminId}/{userIdFrom}/{status}/{requestId}")
    public ResponseEntity updateRequestStatus(@PathVariable String adminId ,@PathVariable String userIdFrom,@PathVariable String status,@PathVariable String requestId){
        String result = userService.updateRequestStatus(adminId,userIdFrom,status,requestId);
        if (result.equalsIgnoreCase("success")) return ResponseEntity.ok(new ApiResponse(result));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }


}
