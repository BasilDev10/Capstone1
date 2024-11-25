package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
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
    @NotNull(message = "Error: coupon Discount Percentage is null")
    @PositiveOrZero(message = "Error: coupon Discount Percentage is must be positive or zero")
    @Max(value = 100, message = "Error: coupon Discount Percentage the max is 100")
    private int couponDiscountPercentage;
    @NotNull(message = "Error: applyCoupon is null")
    private boolean applyCoupon;
}
