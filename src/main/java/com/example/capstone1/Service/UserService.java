package com.example.capstone1.Service;

import com.example.capstone1.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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


    public String updateRequestStatus(String adminId , String userIdFrom,String status, String requestId){

        User userFrom = getByUserId(userIdFrom);

        if (!status.equalsIgnoreCase("approved") && !status.equalsIgnoreCase("rejected")) return "Error: status must be approved or rejected";

        for (int i = 0 ; i < userFrom.getTransferRequests().size() ; i++){
            if(userFrom.getTransferRequests().get(i).get("requestId").toString().equals(requestId)){
                if(status.equalsIgnoreCase("approved") && userFrom.getTransferRequests().get(i).get("status").toString().equalsIgnoreCase("pending")){
                    userFrom.getTransferRequests().get(i).put("status",status);
                    return transferBalance(adminId,userIdFrom, (String) userFrom.getTransferRequests().get(i).get("userIdTo"), (double) userFrom.getTransferRequests().get(i).get("amount"));
                }else if(userFrom.getTransferRequests().get(i).get("status").toString().equalsIgnoreCase("rejected")){
                    return "Error: request is already rejected";
                }else if(userFrom.getTransferRequests().get(i).get("status").toString().equalsIgnoreCase("approved")){
                    return "Error: request is already approved";
                } else{
                    userFrom.getTransferRequests().get(i).put("status",status);
                    return "success";
                }
            }
        }
        return "Request not found";
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


    public  String requestTransfer(Map<String, Object> transferRequest){

        String userIdFrom = (String) transferRequest.get("userIdFrom");
        String userIdTo = (String) transferRequest.get("userIdTo");
        double amount = Double.parseDouble(transferRequest.get("amount").toString());
        String reason = (String) transferRequest.get("reason");


        if(userIdFrom.equalsIgnoreCase(userIdTo)) return "Error: you transfer to yourself";


        User userFrom = getByUserId(userIdFrom);
        User userTo = getByUserId(userIdTo);

        if (userFrom == null || userTo == null) {
            return "Error: User not found";
        }

        if (amount <= 0) {
            return "Error: Amount must be greater than zero";
        }


        Map<String, Object> requestDetails = new HashMap<>();
        requestDetails.put("userIdFrom", userIdFrom);
        requestDetails.put("userIdTo", userIdTo);
        requestDetails.put("amount", amount);
        requestDetails.put("reason", reason);
        requestDetails.put("requestId", System.currentTimeMillis());
        requestDetails.put("status", "pending");

        if (userFrom.getTransferRequests() == null) {
            userFrom.setTransferRequests(new ArrayList<>());
        }
        userFrom.getTransferRequests().add(requestDetails);
        updateUser(userIdFrom, userFrom);
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
