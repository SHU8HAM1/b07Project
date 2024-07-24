package com.example.b07project;

import android.media.Image;
import android.widget.ImageView;

public class Item {
    String name;
    String description;
    int lot_num;
    String category;
    String period;
    String picture;

    public Item(String name, String description, int lot, String category, String period, String pic){
        this.name = name;
        this.description = description;
        this.lot_num = lot;
        this.category = category;
        this.period = period;
        this.picture = pic;
    }

    public Item(){
        this.name = "Holkdsjfjkldasjklfjkljaa";
        this.description = "lorem ksadj dklsjf lkdsjfkl skdfjlksdjf kf dsklajfj afkljdsj fkdj fkf alkj afskljipsum";
        this.lot_num = 2;
        this.category = "huhuhuhuh";
        this.period = "Shanghai";
        this.picture = "https://imgur.com/tGbaZCY";
    }

    public String getPicture() {
        return picture;
    }

    public int getLot_num() {
        return lot_num;
    }

    public String getPeriod() {
        return period;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
