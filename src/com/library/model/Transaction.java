package com.library.model;

import java.util.Date;

public class Transaction {
    private int id;
    private int bookId;
    private int borrowerId;
    private Date borrowDate;
    private Date returnDate;
    private String status;
    private int quantity; // BARU: Jumlah salinan yang dipinjam dalam transaksi ini

    public Transaction() {}

    public Transaction(int id, int bookId, int borrowerId, Date borrowDate, Date returnDate, String status, int quantity) {
        this.id = id;
        this.bookId = bookId;
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public int getBorrowerId() { return borrowerId; }
    public void setBorrowerId(int borrowerId) { this.borrowerId = borrowerId; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getQuantity() { return quantity; } // BARU
    public void setQuantity(int quantity) { this.quantity = quantity; } // BARU
}