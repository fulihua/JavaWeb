package book.dao.Impl;

import book.dao.BookDao;
import book.pojo.Book;
import myssm.base.BaseDao;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 8:15 PM
 * @description
 */
public class BookDaoImpl extends BaseDao<Book> implements BookDao {
    @Override
    public List<Book> getBookList() {
        return executeQuery("select * from t_book");
    }

    @Override
    public Book getBook(Integer id) {
        return load("select * from t_book where id = ?",id);
    }

    @Override
    public void updateBook(Book book) {
        executeUpdate("update t_book set saleCount = ?,bookCount = ?,where id = ?",book.getSaleCount(),book.getBookCount(),book.getId());
    }
}