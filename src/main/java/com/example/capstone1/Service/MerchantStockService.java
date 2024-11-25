package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;
    // for id counter
    private static int merchantStockCounter = 1;

    public ArrayList<MerchantStock> getAllMerchantStocks(){
        return merchantStocks;
    }


    public MerchantStock getMerchantStockById(String id){
        for (MerchantStock merchantStock : merchantStocks){
            if (merchantStock.getId().equalsIgnoreCase(id))return merchantStock;
        }
        return null;
    }


    public MerchantStock getMerchantStockById(String productId,String merchantId){
        for (MerchantStock merchantStock : merchantStocks){
            if (merchantStock.getProductId().equalsIgnoreCase(productId) && merchantStock.getMerchantId().equalsIgnoreCase(merchantId))return merchantStock;
        }
        return null;
    }

    public ArrayList<MerchantStock> suggestionProductByCategory(String productId){
        ArrayList<MerchantStock> suggestedProduct = new ArrayList<>();

        Product product = productService.getProductById(productId);
        if(product == null) return suggestedProduct;

        for (MerchantStock merchantStock : merchantStocks){
            if (productService.getProductById(merchantStock.getProductId()).getCategoryId().equalsIgnoreCase(product.getCategoryId())){
                suggestedProduct.add(merchantStock);
            }
        }
        return suggestedProduct;
    }


    public ArrayList<MerchantStock> suggestionProductByMerchant(String merchantId){
        ArrayList<MerchantStock> suggestedProduct = new ArrayList<>();

        Merchant merchant = merchantService.getMerchantById(merchantId);
        if(merchant == null) return suggestedProduct;

        for (MerchantStock merchantStock : merchantStocks){
            if (merchantService.getMerchantById(merchantStock.getMerchantId()).getId().equalsIgnoreCase(merchantId)){
                suggestedProduct.add(merchantStock);
            }
        }
        return suggestedProduct;
    }

    public String addMerchantStock(MerchantStock merchantStock){

        if (productService.getProductById(merchantStock.getProductId()) == null) return "Error: productId ("+merchantStock.getProductId()+") not found ";
        else if(merchantService.getMerchantById(merchantStock.getMerchantId()) == null) return "Error: merchantId ("+merchantStock.getMerchantId()+") not found ";
        else if(getMerchantStockById(merchantStock.getProductId() , merchantStock.getMerchantId()) != null) return "Error : duplicated merchant stock";
        else {
            merchantStock.setId("MS"+merchantStockCounter);
            merchantStockCounter++;
            merchantStocks.add(merchantStock);
            return "added";
        }


    }

    public String addMerchantStockToProduct(String productId,String merchantId , int stock){

        MerchantStock merchantStock = null;

        if (productService.getProductById(productId) == null) return "Error: productId ("+productId+") not found ";
        else if(merchantService.getMerchantById(merchantId) == null) return "Error: merchantId ("+merchantId+") not found ";
        else if(getMerchantStockById(merchantStock.getProductId() , merchantStock.getMerchantId()) != null) return "Error : duplicated merchant stock";
        else {
            merchantStock.setId("MS"+merchantStockCounter);
            merchantStock.setProductId(productId);
            merchantStock.setMerchantId(merchantId);
            merchantStock.setStock(stock);
            merchantStockCounter++;
            merchantStocks.add(merchantStock);
            return "added";
        }


    }



    public boolean updateMerchantStock(String id, MerchantStock merchantStock){
        for (int i = 0 ; i < merchantStocks.size();i++){
            if(merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                merchantStocks.set(i,merchantStock);
                return true;
            }
        }
        return false;
    }

    public String userBuyProduct(String userId,String productId , String merchantId){

        MerchantStock merchantStock = getMerchantStockById(productId,merchantId);
        User user = userService.getByUserId(userId);
        Product product = productService.getProductById(productId);
        Merchant merchant = merchantService.getMerchantById(merchantId);

        if(merchantStock == null )return "Error: Merchant stock with productId ("+productId+") and merchantId ("+merchantId+")  not found";
        else if ( user == null) return "Error: userId ("+userId+") not found";
        else if(product == null) return "Error: productId ("+productId+") not found";
        else if(merchant == null ) return  "Error: merchantId ("+merchantId+") not found";
        else if(merchantStock.getStock() <= 0) return "Error: product is out of stock";
        else if (user.getBalance() < product.getPrice()) return "Error: user balance is less then product price";
        else{
            user.setBalance(user.getBalance() - product.getPrice());
            merchantStock.setStock(merchantStock.getStock()-1);
            updateMerchantStock(merchantStock.getId(),merchantStock);
            return "success";
        }
    }

    public String userReturnOrder(String userId,String productId , String merchantId){

        MerchantStock merchantStock = getMerchantStockById(productId,merchantId);
        User user = userService.getByUserId(userId);
        Product product = productService.getProductById(productId);
        Merchant merchant = merchantService.getMerchantById(merchantId);

        if(merchantStock == null )return "Error: Merchant stock with productId ("+productId+") and merchantId ("+merchantId+")  not found";
        else if ( user == null) return "Error: userId ("+userId+") not found";
        else if(product == null) return "Error: productId ("+productId+") not found";
        else if(merchant == null ) return  "Error: merchantId ("+merchantId+") not found";
        else{
            user.setBalance(user.getBalance() + product.getPrice());
            merchantStock.setStock(merchantStock.getStock()+1);
            updateMerchantStock(merchantStock.getId(),merchantStock);
            return "success";
        }
    }
    public boolean deleteMerchantStock(String id){
        for (int i = 0 ; i < merchantStocks.size();i++){
            if(merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

}
