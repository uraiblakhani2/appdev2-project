package com.example.healthpal;

public class Medicine {

    private String date;
    private String name;
    private String quantity;

    public Medicine(String date, String name, String quantity) {
        this.date = date;
        this.name = name;
        this.quantity = quantity;
    }

    public Medicine() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
