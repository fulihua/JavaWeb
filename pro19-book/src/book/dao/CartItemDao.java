package book.dao;

import book.pojo.CartItem;
import book.pojo.User;

import java.util.List;

public interface CartItemDao {

    List<CartItem> getCartItemList(Integer userId);

    void addCartItem(CartItem cartItem);

    void updateCartItem(CartItem cartItem);

    void delCartItem(CartItem cartItem);
}
