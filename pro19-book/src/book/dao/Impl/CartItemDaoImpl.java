package book.dao.Impl;

import book.dao.CartItemDao;
import book.pojo.CartItem;
import book.pojo.User;
import myssm.base.BaseDao;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 9:28 PM
 * @description
 */
public class CartItemDaoImpl extends BaseDao<CartItem> implements CartItemDao {
    @Override
    public List<CartItem> getCartItemList(Integer userId) {
        return executeQuery("select * from t_cart where id = ?",userId);
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        executeUpdate("insert into t_cart values(0,?,?,?,?,?)",cartItem.getBook().getId(),cartItem.getBookName(),cartItem.getPrice(),cartItem.getBuyCount(),cartItem.getUser().getId());
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        executeUpdate("update t_cart set buyCount = ? where id = ?",cartItem.getBuyCount(),cartItem.getId());
    }

    @Override
    public void delCartItem(CartItem cartItem) {
        executeUpdate("delete from t_cart where id = ?",cartItem.getId());
    }
}