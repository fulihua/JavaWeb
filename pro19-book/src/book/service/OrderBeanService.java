package book.service;

import book.pojo.OrderBean;
import book.pojo.User;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 8:31 PM
 * @description
 */
public interface OrderBeanService {
    void addOrderBean(OrderBean orderBean);

    List<OrderBean> getOrderBeanList(User user);
}