package org.example.library.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.library.entity.Book;
import java.util.List;

@Mapper
public interface BookMapper {
     List<Book> getAll();
     int addBooks(Book book);
     Book getBooksById(int id);
     int updateBooks(Book book);
     int deleteBooks(int id);
     Integer getStockById(Integer id);
     void decreaseStock(Integer id);
     void increaseStock(Integer id);
}
