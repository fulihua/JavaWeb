package org.example.library.controller;

import org.example.library.entity.Book;
import org.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/8/7 5:06 PM
 * @description
 */
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookservice;
    @GetMapping
    public List<Book> listAll(){
        return bookservice.getAllBooks();
    }
    @PostMapping
    public int addBooks(@RequestBody Book book){
        return bookservice.addBooks(book);
    }
    @GetMapping("/{id}")
    public Book getBooksById(@PathVariable int id){
        return bookservice.getBooksById(id);
    }  
    @PutMapping("/{id}")
    public int updateBooks(@PathVariable int id,@RequestBody Book book){
        book.setId(id);
        return bookservice.updateBooks(book);
    }
    @DeleteMapping("/{id}")
    public int deleteBooks(@PathVariable int id){return bookservice.deleteBooks(id);}
    @PostMapping("/borrow/{id}")
    public String borrowBook(@PathVariable int id){bookservice.borrowBook(id);return"借书成功!";}
    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable int id){bookservice.returnBook(id);return"还书成功!";}
}