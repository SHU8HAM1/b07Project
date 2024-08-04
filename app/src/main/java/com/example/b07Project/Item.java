package com.example.b07Project;

public class Item {
    private String category;
    private String description;
    private int lotNumber;
    private String name;
    private String period;
    private String uri;
    public Item() {
        // Default constructor
    }

    public Item(String category, String desc, int lotNumber, String name, String period, String uri) {
        this.lotNumber = lotNumber;
        this.name = name;
        this.category = category;
        this.period = period;
        this.description = desc;
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
