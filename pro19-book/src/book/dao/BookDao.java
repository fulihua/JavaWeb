package book.dao;

import book.pojo.Book;

import java.util.List;

public interface BookDao {
    List<Book> getBookList();
    Book getBook(Integer id);
    void updateBook(Book book);
}
