package com.example.b07Project;

import androidx.annotation.NonNull;

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

    private DatabaseReference getRef(String path){
        return db.getReference().child(path);
    }

    public AddItemModel(AddItemPresenter presenter){
        db = FirebaseDatabase.getInstance("https://b07project-d4d14-default-rtdb.firebaseio.com/");
        itemRef = getRef("Items");
        this.presenter = presenter;
    }

    public void addItem(Item newItem){

        itemRef.child(String.valueOf(newItem.lotNumber)).setValue(newItem).addOnCompleteListener(task -> {
            updatePathValue("Categories", newItem.category);
            updatePathValue("Periods", newItem.period);
            presenter.setAdded(task.isSuccessful());
        });
    }

    private void updatePathValue(String path, String subpath){
        DatabaseReference ref = getRef(path).child(subpath);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer value = snapshot.getValue(Integer.class);
                if (value == null) {
                    value = 0;
                }
                ref.setValue(value + 1);
            }

            public void onCancelled(@NonNull DatabaseError error) {
                presenter.setDataBaseError(error.getMessage());
            }
        });
    }

    public void getDataList(String path){
        ArrayList<String> list = new ArrayList<>();
        getRef(path).addValueEventListener(new ValueEventListener() {
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
    }
}
