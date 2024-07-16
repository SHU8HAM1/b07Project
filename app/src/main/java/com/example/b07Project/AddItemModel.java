package com.example.b07Project;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemModel {
    private FirebaseDatabase db;

    public AddItemModel(){
        db = FirebaseDatabase.getInstance("https://b07project-d4d14-default-rtdb.firebaseio.com");
    }
    //Need a method to get arraylist of category and period
}
