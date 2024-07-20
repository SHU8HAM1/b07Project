package com.example.b07Project;

import androidx.annotation.NonNull;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddItemModel{
    protected AddItemPresenter presenter;
    private FirebaseDatabase db;
    private DatabaseReference itemRef;

    public AddItemModel(AddItemPresenter presenter){
        db = FirebaseDatabase.getInstance("https://b07project-d4d14-default-rtdb.firebaseio.com/");
        itemRef = db.getReference().child("Items");
        this.presenter = presenter;
    }

    public void addItem(String lotNum, Item newItem){
        itemRef.child(lotNum).setValue(newItem).addOnCompleteListener(task -> {
            presenter.setAdded(task.isSuccessful());
        });
    }

    public void getDataList(String path){
        ArrayList<String> list = new ArrayList<>();
        DatabaseReference ref = db.getReference(path);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    list.add(child.getKey());
                }
                presenter.popList(path, list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                presenter.setDataBaseError(error.getMessage());
            }
        });
        //return list;
    }

    //Need a method to get arraylist of category and period
    /*public ArrayList<String> getCategoryList(){
        ArrayList<String> categoryList = new ArrayList<>();
        DatabaseReference categoryRef = db.getReference("Categories");

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    categoryList.add(child.getKey());
                }
                presenter.getCategoryList(categoryList);
                //logList(categoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                presenter.setDataBaseError(error.getMessage());
            }
        });
        return categoryList;
    }

    public ArrayList<String> getPeriodList(){
        ArrayList<String> periodList = new ArrayList<>();
        DatabaseReference categoryRef = db.getReference("Periods");

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                periodList.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    periodList.add(child.getKey());
                }
                presenter.getPeriodList(periodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                presenter.setDataBaseError(error.getMessage());
            }
        });
        return periodList;
    }*/
}
