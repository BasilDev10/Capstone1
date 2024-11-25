package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();
    private static int categoryCounterId = 1;

    public ArrayList<Category> getAllCategories(){
        return categories;
    }

    public Category getCategoryById(String id ){
        for(Category category : categories){
            if (category.getId().equalsIgnoreCase(id)){
                return category;
            }
        }
        return null;
    }

    public void addCategory(Category category){

        category.setId("C"+categoryCounterId);
        categoryCounterId++;
        categories.add(category);
    }

    public boolean updateCategory(String id , Category category){

        for(int i = 0 ; i < categories.size(); i++){
            if(categories.get(i).getId().equalsIgnoreCase(id)){
                categories.set(i,category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id ){

        for(int i = 0 ; i < categories.size(); i++){
            if(categories.get(i).getId().equalsIgnoreCase(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
