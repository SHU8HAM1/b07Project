package com.example.b07project;

import android.media.Image;
import android.widget.ImageView;

public class Item {
    String name;
    String description;
    int lotNumber;
    String category;
    String period;
    String uri;

    public Item(String name, String description, int lot, String category, String period, String pic){
        this.name = name;
        this.description = description;
        this.lotNumber = lot;
        this.category = category;
        this.period = period;
        this.uri = pic;
    }

    public Item(){
        this.name = "Hosdgsgsdgdea";
        this.description = "";
        this.lotNumber = 2;
        this.category = "huhuhuhuh";
        this.period = "Shanghai";
        this.uri = "";//"https://firebasestorage.googleapis.com/v0/b/b07project-d4d14.appspot.com/o/uploads%2F1721770737737.jpg?alt=media&token=3766bb66-c172-4a13-8e59-ca803407dfec";
    }
}
