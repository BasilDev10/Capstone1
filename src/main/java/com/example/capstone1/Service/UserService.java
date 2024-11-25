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
    public User getByUserName(String username){
        for (User user : users){
            if (user.getUsername().equalsIgnoreCase(username)) return user;
        }
        return null;
    }
    public boolean addUser(User user){

        if(getByUserName(user.getUsername()) != null) return false;

        user.setId("U"+userCounterId);
        userCounterId++;
        users.add(user);
        return true;
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

    public String transferBalance(String adminId , String userIdFrom,String userIdTo, double amount){
        User admin = getByUserId(adminId);
        User userFrom = getByUserId(userIdFrom);
        User userTo = getByUserId(userIdTo);
        if(admin == null) return "Error:Admin not found";
        if(!admin.getRole().equalsIgnoreCase("admin")) return "Error: user not admin";
        if(userFrom == null || userTo == null) return "Error:User not found";
        if(userFrom.getBalance() < amount) return "Error: user balance is not enough";
        userFrom.setBalance(userFrom.getBalance() - amount);
        userTo.setBalance(userTo.getBalance() + amount);
        return "success";
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
