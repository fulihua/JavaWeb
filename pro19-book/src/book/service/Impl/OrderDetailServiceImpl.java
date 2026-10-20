package book.service.Impl;

import book.dao.OrderDetailDao;
import book.pojo.OrderDetail;
import book.service.OrderDetailService;

/**
 * @author fulihua
 * @date 2026/10/17 9:12 PM
 * @description
 */
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailDao orderDetailServiceDao;
    @Override
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailServiceDao.addOrderDetail(orderDetail);
    }
}