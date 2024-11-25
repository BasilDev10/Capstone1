package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    private static int merchantCounterId = 1;


    public ArrayList<Merchant> getAllMerchants(){
        return merchants;
    }

    public Merchant getMerchantById(String id){
        for(Merchant merchant : merchants){
            if (merchant.getId().equalsIgnoreCase(id)){
                return merchant;
            }
        }
        return null;
    }

    public void addMerchant(Merchant merchant){

        merchant.setId("M"+merchantCounterId);
        merchantCounterId++;
        merchants.add(merchant);
    }

    public boolean updateMerchant(String id, Merchant merchant){

        for (int i = 0 ; i < merchants.size();i++){
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }


    public boolean deleteMerchant(String id){

        for (int i = 0 ; i < merchants.size();i++){
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
}
