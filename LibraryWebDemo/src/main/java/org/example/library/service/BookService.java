package org.example.library.service;

import org.example.library.entity.Book;
import org.example.library.entity.BorrowRecord;
import org.example.library.exception.BookNotFoundException;
import org.example.library.mapper.BookMapper;
import org.example.library.mapper.BorrowRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/8/7 5:07 PM
 * @description
 */

@Service
public class BookService {
    @Autowired
    private BookMapper bookmapper;
    @Autowired
    private BorrowRecordMapper borrowrecordmapper;
    public List<Book> getAllBooks(){
        return bookmapper.getAll();
    }
    public int addBooks(Book book){
        return bookmapper.addBooks(book);
    }
    public Book getBooksById(int id){

         Book tempbook = bookmapper.getBooksById(id);
         if(tempbook == null){
             throw new BookNotFoundException("图书不存在+ID:"+id);
         }
         else {return tempbook;}
    }
    public int updateBooks(Book book){
        return bookmapper.updateBooks(book);
    }
    public int deleteBooks(int id){return bookmapper.deleteBooks(id);}
   @Transactional
public void borrowBook(Integer id) {
    System.out.println("=== 开始借书，图书ID: " + id);
    
    Book tbook = getBooksById(id);
    System.out.println("1. 查询图书完成，库存: " + tbook.getStock());
    
    int tstock = tbook.getStock();
    if (tstock > 0) {
        bookmapper.decreaseStock(id);
        System.out.println("2. 减库存完成");
        
        BorrowRecord record = new BorrowRecord();
        record.setBookId(id);
        record.setBorrowTime(LocalDateTime.now());
        borrowrecordmapper.insert(record);
        System.out.println("3. 插入借阅记录完成");
        
        System.out.println("借书成功！");
    } else {
        System.out.println("库存不足！");
        throw new RuntimeException("库存不足");
    }
}
    public void returnBook(Integer id){
        bookmapper.increaseStock(id);
        borrowrecordmapper.updateReturnTime(id);

    }
}