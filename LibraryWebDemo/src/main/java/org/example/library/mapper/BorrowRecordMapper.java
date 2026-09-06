 package org.example.library.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.library.entity.*;;

@Mapper
public interface BorrowRecordMapper {
    void insert(BorrowRecord record);
    void updateReturnTime(Integer id);

    
}