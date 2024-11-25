package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "Error: id is empty")
    private String id;
    @NotEmpty(message = "Error: name is empty")
    @Size(min = 3 , message = "Error: name length must is more then 3")
    private String name;
    @NotNull(message = "Error: price is null")
    @Positive(message = "Error: price is must be positive")
    private double price;
    @NotEmpty(message = "Error: categoryId is empty")
    private String categoryId;
    @NotEmpty(message = "Error: coupon is empty")
    @Size(min = 4 , message = "Error: coupon length is more then 4")
    private String coupon;
    @NotNull(message = "Error: couponDiscountPercentage is null")
    @Positive(message = "Error: couponDiscountPercentage is must be positive")
    private int couponDiscountPercentage;
    @NotNull(message = "Error: applyCoupon is null")
    private boolean applyCoupon;
}
