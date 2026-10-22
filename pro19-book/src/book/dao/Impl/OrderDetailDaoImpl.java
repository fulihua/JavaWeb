package book.dao.Impl;

import book.dao.OrderDetailDao;
import book.pojo.OrderDetail;
import myssm.base.BaseDao;

/**
 * @author fulihua
 * @date 2026/10/17 9:13 PM
 * @description
 */
public class OrderDetailDaoImpl extends BaseDao<OrderDetail> implements OrderDetailDao {
    @Override
    public void addOrderDetail(OrderDetail orderDetail) {
        executeUpdate("insert into t_order_detail values(0,?,?,?,?,?)",orderDetail.getBook().getId(),orderDetail.getBookName(),orderDetail.getPrice(),orderDetail.getBuyCount(),orderDetail.getOrderBean().getId());
    }
}