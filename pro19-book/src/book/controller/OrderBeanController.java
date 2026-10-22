package book.controller;

import book.pojo.*;
import book.service.BookService;
import book.service.CartItemService;
import book.service.OrderBeanService;
import book.service.OrderDetailService;
import book.util.OrderBeanUtil;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/19 1:24 PM
 * @description
 */
public class OrderBeanController {

    private final static String PAGE_ORDER_LIST="order/order";
    private OrderBeanService orderBeanService;
    private CartItemService cartItemService;
    private BookService bookService;
    private OrderDetailService orderDetailService;
    //结账
    public String checkout(HttpSession session){
        User currUser =(User) session.getAttribute("currUser");
        Cart cart = currUser.getCart();

        //向订单表添加一条记录
        OrderBean orderBean = new OrderBean(new Date(),cart.getTotalMoney(),cart.getTotalCount(),0,currUser);
        String orderNo = OrderBeanUtil.generateOrderNoStr(new Date());
        orderBean.setOrderNo(orderNo);
        orderBeanService.addOrderBean(orderBean);
        Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
        cartItemMap.forEach((bookId,cartItem)->{
            //2.向订单详情表添加n条记录
            OrderDetail orderDetail = new OrderDetail(cartItem.getBook(),cartItem.getBookName(),cartItem.getPrice(),cartItem.getBuyCount(),orderBean);
            orderDetailService.addOrderDetail(orderDetail);
            //3.更新图书信息（销量和库存量）
            Book book = bookService.getBook(bookId);
            book.setSaleCount(book.getSaleCount()+cartItem.getBuyCount());
            book.setBookCount(book.getBookCount()-cartItem.getBuyCount());
            bookService.updateBook(book);
            //4.删除购物车相关信息
            cartItemService.delCartItem(cartItem);
        });

        //5.清除内存中的数据信息
        currUser.getCart().getCartItemMap().clear();
        return "cart/checkout";
    }

    public String index(HttpSession session){
        User user = (User)session.getAttribute("currUser");
        List<OrderBean> orderBeanList = orderBeanService.getOrderBeanList(user);
        user.setOrderBeanList(orderBeanList);
        return PAGE_ORDER_LIST;
    }
}