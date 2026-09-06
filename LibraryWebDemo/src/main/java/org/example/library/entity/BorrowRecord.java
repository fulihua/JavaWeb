package org.example.library.entity;
import java.time.LocalDateTime;

public class BorrowRecord {
    private Integer id;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
    private Integer bookId;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }
    public void setBorrowTime(LocalDateTime borrowTime) {
        this.borrowTime = borrowTime;
    }
    public LocalDateTime getReturnTime() {
        return returnTime;
    }
    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }
    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

        
}
