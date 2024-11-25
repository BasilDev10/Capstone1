package com.example.capstone1.Service;

import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    private static int userCounterId = 1;


    public ArrayList<User> getAllUsers(){
        return users;
    }

    public User getByUserId(String id){
        for (User user : users){
            if (user.getId().equalsIgnoreCase(id)) return user;
        }
        return null;
    }
    public void addUser(User user){

        user.setId("U"+userCounterId);
        userCounterId++;
        users.add(user);
    }
    public boolean updateUser(String id , User user){
        for (int i = 0; i < users.size();i++){
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id ){
        for (int i = 0; i < users.size();i++){
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

}
