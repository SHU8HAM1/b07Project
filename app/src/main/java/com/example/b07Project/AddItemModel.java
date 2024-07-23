package com.example.b07Project;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AddItemModel{
    private AddItemPresenter presenter;
    private FirebaseDatabase db;
    private DatabaseReference itemRef;
    private StorageReference storageRef;
    private FirebaseStorage sr;

    private DatabaseReference getDbRef(String path){
        return db.getReference().child(path);
    }

    private StorageReference getSrRef(String path){
        return sr.getReference().child(path);
    }

    public AddItemModel(AddItemPresenter presenter){
        db = FirebaseDatabase.getInstance("https://b07project-d4d14-default-rtdb.firebaseio.com/");
        sr = FirebaseStorage.getInstance("gs://b07project-d4d14.appspot.com");
        storageRef = getSrRef("uploads");
        itemRef = getDbRef("Items");
        this.presenter = presenter;
    }

    public void addItem(Item newItem){

        itemRef.child(String.valueOf(newItem.lotNumber)).setValue(newItem).addOnCompleteListener(
                task -> {
                    updatePathValue("Categories", newItem.category);
                    updatePathValue("Periods", newItem.period);
                    presenter.setAdded(task.isSuccessful());
        });
    }

    private void updatePathValue(String path, String subpath){
        DatabaseReference ref = getDbRef(path).child(subpath);

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
                presenter.setError("Database error: " + error.getMessage());
            }
        });
    }

    public void getDataList(String path){
        ArrayList<String> list = new ArrayList<>();
        getDbRef(path).addValueEventListener(new ValueEventListener() {
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
                presenter.setError("Database error: " + error.getMessage());
            }
        });
    }

    public void lotNumExist(String lotNumString, String name, String description, String category,
                            String period, Uri uri) {
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist = false;
                for(DataSnapshot child: snapshot.getChildren()){
                    if(child.getKey().equals(lotNumString)){
                        exist = true;
                    }
                }
                presenter.addItem(exist, lotNumString, name, description, category, period, uri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                presenter.setError("Database error: " + error.getMessage());
            }
        });
    }

    public void addItemWithURI(int lotNum, String name, String description, String category,
                               String period, Uri uri){
        if (uri != null){
            StorageReference fileRef = storageRef.child(
                    System.currentTimeMillis() + "." + presenter.getFileExt(uri));
            fileRef.putFile(uri).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    fileRef.getDownloadUrl().addOnCompleteListener(urlTask -> {
                        if (urlTask.isSuccessful()){
                            String uriString = task.getResult().toString();
                            presenter.setUploaded();
                            addItem(new Item(lotNum, category, description, name, period,
                                    uriString));
                        }
                        else{
                            presenter.setError("Failed to get download URL" +
                                    urlTask.getException().getMessage());
                        }
                    });
                }
                else{
                    presenter.setError("Upload Failed: " + task.getException().getMessage());
                }
            });
        }
        addItem(new Item(lotNum, category, description, name, period, ""));
    }
}
