package com.example.b07project;


public class Item {
    public String category;
    public String description;
    public int lotNumber;
    public String name;
    public String period;
    public String uri;
    public Item() {
        // Default constructor
    }

    public Item(int lotNumber, String category, String description, String name, String period, String uri) {
        this.lotNumber = lotNumber;
        this.category = category;
        this.description = description;
        this.name = name;
        this.period = period;
        this.uri = uri;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {return this.uri;}

    public void setUri(String url) { this.uri = url;}
}
