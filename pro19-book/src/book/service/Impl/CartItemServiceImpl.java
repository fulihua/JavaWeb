package book.service.Impl;

import book.dao.BookDao;
import book.dao.CartItemDao;
import book.pojo.Book;
import book.pojo.Cart;
import book.pojo.CartItem;
import book.pojo.User;
import book.service.CartItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/17 9:22 PM
 * @description
 */
public class CartItemServiceImpl implements CartItemService {
    private CartItemDao cartItemDao;
    private BookDao bookDao;
    @Override
    public List<CartItem> getCartItemList(Integer userId) {
        return cartItemDao.getCartItemList(userId);
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemDao.addCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDao.updateCartItem(cartItem);
    }

    @Override
    public void delCartItem(CartItem cartItem) {
        cartItemDao.delCartItem(cartItem);
    }

    @Override
    public void getCartInfo(User user) {
        List<CartItem> list = getCartItemList(user.getId());
        Map<Integer,CartItem> cartItemMap = new HashMap<>();
        for(CartItem cartItem:list){
            if(cartItem != null && cartItem.getBook() != null){
                Book book = bookDao.getBook(cartItem.getBook().getId());
                if(book!=null){
                cartItem.setBook(book);}
            cartItemMap.put(cartItem.getBook().getId(),cartItem);
            }
        }
        Cart cart = new Cart(cartItemMap);
        user.setCart(cart);
    }
}