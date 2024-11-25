package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;
    private static int ProductCounterId = 1;


    public ArrayList<Product> getAllProducts(){
        return products;
    }
    public Product getProductById(String id){
        for (Product product : products){
            if (product.getId().equalsIgnoreCase(id)){
                return product;
            }
        }
        return null;
    }
    public boolean addProduct(Product product){

        if(categoryService.getCategoryById(product.getCategoryId()) == null) return false;

        product.setId("P"+ProductCounterId);
        ProductCounterId++;
        products.add(product);
        return true;
    }

    public boolean updateProduct(String id, Product product){
        for (int i = 0 ; i < products.size() ; i++){
            if (products.get(i).getId().equalsIgnoreCase(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for (int i = 0 ; i < products.size() ; i++){
            if (products.get(i).getId().equalsIgnoreCase(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
