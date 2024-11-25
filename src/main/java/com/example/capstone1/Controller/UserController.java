package com.example.capstone1.Controller;


import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
    // Extra 5 endpoint
    // admin transfer a specified balance from one user to another
    @PutMapping("/transfer-balance/{adminId}/{userIdFrom}/{userIdTo}/{amount}")
    public ResponseEntity transferBalance(@PathVariable String adminId, @PathVariable String userIdFrom , @PathVariable String userIdTo , @PathVariable int amount){
        String result = userService.transferBalance(adminId,userIdFrom,userIdTo,amount);
        if (result.equalsIgnoreCase("success")) return ResponseEntity.ok(new ApiResponse(result));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }
}
