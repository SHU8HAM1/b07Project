package com.example.b07Project;

public class Item {
    public int lotNumber;
    public String category;
    public String description;
    public String name;
    public String period;

    public Item(int lotNumber, String category, String description, String name, String period) {
        this.category = category;
        this.description = description;
        this.name = name;
        this.period = period;
        this.lotNumber = lotNumber;
    }
}

