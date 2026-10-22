package book.controller;

import book.pojo.Book;
import book.pojo.Cart;
import book.pojo.CartItem;
import book.pojo.User;
import book.service.BookService;
import book.service.CartItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.window;

/**
 * @author fulihua
 * @date 2026/10/18 9:00 PM
 * @description
 */
public class CartController {
    private BookService bookService;
    private CartItemService cartItemService;
    /*
    点击添加到购物车的思路：
    1.检查是否已登录,如果没有登录,则重定向到登录界面
    2.查询当前用户的登录信息 -> Cart,Cart和CartItem之间的关系通过一个Map集合来维护
    3.将用户点击的这本书加入到购物车中，如果车中已经存在这本书，则update，反之则insert
     */

    public String addToCart(Integer id,HttpServletRequest request, HttpServletResponse response)throws IOException {
        // 不要直接用参数session！！自己从request获取
        HttpSession sess = request.getSession(false);
        if(sess == null || sess.getAttribute("currUser") == null){
            return "redirect:user.do?oper=toLogin";
        }
        if(id == null){
            System.out.println("错误：id参数为空！");
            return "redirect:book.do";
        }
        User user = (User)sess.getAttribute("currUser");
        cartItemService.getCartInfo(user);
        Map<Integer,CartItem> cartItemMap = user.getCart().getCartItemMap();

                if(cartItemMap.containsKey(id)){
                    //update
                    CartItem cartItem = cartItemMap.get(id);
                    cartItem.setBuyCount(cartItem.getBuyCount()+1);
                    cartItemService.updateCartItem(cartItem);
                }else{
                    //add
                    Book book = bookService.getBook(id);
                    CartItem cartItem = new CartItem(book,book.getBookName(),book.getPrice(),1,user);
                    cartItemService.addCartItem(cartItem);
                    System.out.println("准备加入的bookId：" + id);
                    System.out.println("当前map里面所有key：" + cartItemMap.keySet());

                }
                return "redirect:book.do";
        }

    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sess = request.getSession(false);
        if(sess == null || sess.getAttribute("currUser") == null){
            return "redirect:user.do?oper=toLogin";
        }
        User user = (User)sess.getAttribute("currUser");
        cartItemService.getCartInfo(user);
        return "cart/cart";
    }
    public String delCartItem(Integer id,HttpSession session){
        User user = (User)session.getAttribute("currUser");
        if(user==null){
            return "redirect:user.do?oper=login";
        }
        Cart cart = user.getCart();
        if(cart!=null&&cart.getCartItemMap().containsKey(id)){
            CartItem cartItem = cart.getCartItemMap().get(id);
            cartItemService.delCartItem(cartItem);
            cart.getCartItemMap().remove(id);
        }
        return "redirect:cart.do";
    }
}

