package book.service.Impl;

import book.dao.BookDao;
import book.pojo.Book;
import book.service.BookService;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 8:10 PM
 * @description
 */
public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    @Override
    public List<Book> getBookList() {
        return bookDao.getBookList();
    }

    @Override
    public Book getBook(Integer id) {
        return bookDao.getBook(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }
}