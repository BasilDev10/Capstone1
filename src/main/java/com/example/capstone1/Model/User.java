package com.example.capstone1.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class User {

    @NotEmpty(message = "Error: id is empty!")
    private String id;
    @NotEmpty(message = "Error: username is empty!")
    @Size(min = 6 , message = "Error: username length must is more then 6")
    private String username;
    @NotEmpty(message = "Error: password is empty!")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$" ,message = "Error:1-The password must be more than 6 characters long.\n" +
            "2-It must include both alphabetic characters (letters) and digits.")
    private String password;
    @NotEmpty(message = "Error: email is empty!")
    @Email(message = "Error: wrong email format")
    private String email;
    @NotEmpty(message = "Error: email is empty!")
    @Pattern(regexp = "Admin|Customer" , message = "Error: role only accept Admin or Customer")
    private String role;
    @NotNull(message = "Error: balance is null")
    @Positive(message = "Error: balance must be positive")
    private double balance;
}
