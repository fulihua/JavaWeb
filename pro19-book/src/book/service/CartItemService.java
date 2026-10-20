package book.service;

import book.pojo.CartItem;
import book.pojo.User;

import java.util.List;

public interface CartItemService {
    //查询用户的所有购物车项
    List<CartItem> getCartItemList(Integer userid);
    //添加购物车项
    void addCartItem(CartItem cartItem);
    //更新购物车项
    void updateCartItem(CartItem cartItem);
    //删除特定的购物车项
    void delCartItem(CartItem cartItem);

    void getCartInfo(User user);
}
