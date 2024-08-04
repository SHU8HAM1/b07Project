package com.example.b07project;

public class Item {
    public int lotNumber;
    public String category;
    public String description;
    public String name;
    public String period;
    public String uri;

    public Item() {

    }

    public Item(int lotNumber, String category, String description, String name, String period) {
        this.lotNumber = lotNumber;
        this.category = category;
        this.description = description;
        this.name = name;
        this.period = period;
        this.uri = uri;
    }
}