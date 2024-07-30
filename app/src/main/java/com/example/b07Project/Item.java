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
        this.name = "Hosdgsgsdgdea";
        this.description = "lorem ksadj dklsjfdsalkj" +
                "afskjdfhjkhjfksa" +
                "afskjfklj fjas'fdjl sadkf j'asl dfjka jska" +
                " asfdj ajdsfk kdlsajf ldskfj kdslfj fslakj fs" +
                " asj fklasjdlk jf asfdj kdjasf k faskj fdsj" +
                "alf afjklas kafsdj klaksflj kasjfd k " +
                "afj ajkjfkdlsaj fkljlas. jfasdl f" +
                "f aslkjflkasjlfjla fsddsjkldsjfkljdsfajkf" +
                "f asdkjfdaskljfklsdj fkljd fkdjs ksjdf ksljd" +
                "f dskljfklsjdfkljdskfl sdklfj lkdsjf dskjdsf";
        this.lot_num = 2;
        this.category = "huhuhuhuh";
        this.period = "Shanghai";
        this.picture = "https://firebasestorage.googleapis.com/v0/b/b07project-d4d14.appspot.com/o/uploads%2F1721770737737.jpg?alt=media&token=3766bb66-c172-4a13-8e59-ca803407dfec";
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
