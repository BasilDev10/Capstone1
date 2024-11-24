package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "Error: id is empty")
    private String id;
    @NotEmpty(message = "Error: productId is empty")
    private String productId;
    @NotEmpty(message = "Error: merchantId is empty")
    private String merchantId;
    @NotNull(message = "Error : stock is null")
    @Positive(message = "Error: stock must be positive")
    @Min(value = 10 , message = "Error: stock must be at least 10 at start")
    private int stock;
}
