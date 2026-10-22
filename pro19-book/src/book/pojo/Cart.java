package book.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/19 12:29 PM
 * @description
 */
public class Cart {
    //这个类个CartITem的区别：
    //CartItem代表单个商品条目，而Cart代表购物车整体。Cart包含了多个CartItem
    private Map<Integer,CartItem> cartItemMap = new HashMap<>();
    private Integer totalCount = 0;
    private Double totalMoney = 0.0;

    public Cart() {
    }


    public Cart(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Map<Integer, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Integer getTotalCount() {
        totalCount=0;

        if(cartItemMap!=null&&cartItemMap.size()>0){
            cartItemMap.forEach((k,v)->{
                totalCount+=v.getBuyCount();
            });
        }else{
            totalCount=0;
        }
        return totalCount;
    }

    public Double getTotalMoney() {
        totalMoney=0.0;

       if(cartItemMap!=null&&cartItemMap.size()>0){
           cartItemMap.forEach((k,v)->{
               totalMoney+=(v.getPrice()*v.getBuyCount());
           });
       }else{
           totalMoney=0.0;
       }
       return totalMoney;
    }
}