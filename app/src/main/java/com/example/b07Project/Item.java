package com.example.b07Project;

public class Item {
    public int lotNumber;
    public String category;
    public String description;
    public String name;
    public String period;
    public String uri;

    public Item(String category, String description, int lotNumber, String name, String period, String uri) {
        this.category = category;
        this.description = description;
        this.name = name;
        this.period = period;
        this.lotNumber = lotNumber;
        this.uri = uri;
    }
}

