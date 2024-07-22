package com.example.b07Project;

import java.util.ArrayList;

import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddItemPresenter{
    private AddItemFragment view;
    private AddItemModel model;
    private StorageReference storageRef;

    public AddItemPresenter(AddItemFragment view) {
        this.view = view;
        this.model = new AddItemModel(this);
        model.getDataList("Categories");
        model.getDataList("Periods");
        storageRef = FirebaseStorage.getInstance().getReference("uploads");
    }

    public void checkItem(String lotNumString, String name, String description, String category,
                          String period, Uri uri){
        if (lotNumString.isEmpty() || name.isEmpty() || category.isEmpty() || period.isEmpty()) {
            view.displayMessage("Please fill out all fields");
            return;
        }
        model.lotNumExist(lotNumString, name, description, category, period, uri);
    }

    public void addItem(boolean exists, String lotNumString, String name, String description, String category,
                        String period, Uri uri) {
        if (exists){
            view.displayMessage("Item with this lot number already exists");
            return;
        }
        int lotNum = Integer.parseInt(lotNumString);
        if(uri != null){
            StorageReference fileRef = storageRef.child(
                    System.currentTimeMillis() + "." + getFileExt(uri));
            fileRef.putFile(uri).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    fileRef.getDownloadUrl().addOnCompleteListener(urlTask -> {
                        if (urlTask.isSuccessful()){
                            String uriString = task.getResult().toString();
                            model.addItem(new Item(lotNum, category, description, name, period, uriString));
                            view.displayMessage("Upload Successful");
                        }
                        else{
                            view.displayMessage("Failed to get download URL" + urlTask.getException().getMessage());
                        }
                    });
                }
                else{
                    view.displayMessage("Upload Failed: " + task.getException().getMessage());
                }
            });
        }
        else{
            model.addItem(new Item(lotNum, category, description, name, period, ""));
        }
    }

    private String getFileExt(Uri uri){
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(
                view.getContext().getContentResolver().getType(uri));
    }

    public void setAdded(boolean exist){
        view.addSuccess(exist);
    }

    public void setDataBaseError(String msg){
        view.displayMessage("Database error: " + msg);
    }

    public void popList(String path, ArrayList<String> list) {
        view.popSpinner(path, list);
    }
}
