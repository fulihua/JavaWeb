package book.service;

import book.pojo.Book;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 8:09 PM
 * @description
 */
public interface BookService {
    List<Book> getBookList();
    Book getBook(Integer id);
    void updateBook(Book book);

}