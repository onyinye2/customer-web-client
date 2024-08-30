package com.example.webclient;

public class Customer {
    private Long id;
    private String name;
    private Double total_sales;
    private Double balance_due;
    
    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
        this.balance_due = 0.0;
        this.total_sales = 0.0;
    }

    public Customer(){}

    public Double getTotal_sales() {
        return total_sales;
    }
    public void setTotal_sales(Double total_sales) {
        this.total_sales = total_sales;
    }
    public Double getBalance_due() {
        return balance_due;
    }
    public void setBalance_due(Double balance_due) {
        this.balance_due = balance_due;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
