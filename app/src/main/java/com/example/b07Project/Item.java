package com.example.b07Project;

public class Item {
    public String category;
    public String description;
    public int lotNumber;
    public String name;
    public String period;
    public Item() {
        // Default constructor
    }

    public Item(String category, String desc, int lotNum, String name, String period) {
        this.lotNumber = lotNum;
        this.name = name;
        this.category = category;
        this.period = period;
        this.description = desc;
    }

    public int getLotNum() {
        return lotNumber;
    }

    public void setLotNum(int lotNum) {
        this.lotNumber = lotNum;
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
}
