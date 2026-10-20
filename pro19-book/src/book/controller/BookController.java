package book.controller;

import book.pojo.Book;
import book.service.BookService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/18 8:54 PM
 * @description
 */
public class BookController {
    private  static final String PAGE_INDEX = "index";
    private BookService bookService;
    public String index(HttpSession session){
        List<Book> bookList = bookService.getBookList();
        session.setAttribute("bookList",bookList);
        return PAGE_INDEX;
    }

}