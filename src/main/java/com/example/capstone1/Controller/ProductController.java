package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (productService.addProduct(product)) return ResponseEntity.ok(new ApiResponse("product is added"));
        else return ResponseEntity.status(400).body(new ApiResponse("Error : categoryId ("+product.getCategoryId()+") not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id ,@RequestBody @Valid  Product product , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));


        if (productService.updateProduct(id,product)) return ResponseEntity.ok(new ApiResponse("product is updated"));
        else return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id ){

        if (productService.deleteProduct(id)) return ResponseEntity.ok(new ApiResponse("product is deleted"));
        else return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }
}
