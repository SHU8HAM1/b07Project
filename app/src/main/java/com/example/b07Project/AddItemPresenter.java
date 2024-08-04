package com.example.b07project;

import java.util.ArrayList;

import android.net.Uri;
import android.webkit.MimeTypeMap;

public class AddItemPresenter{
    private AddItemFragment view;
    private AddItemModel model;

    public AddItemPresenter(AddItemFragment view) {
        this.view = view;
        this.model = new AddItemModel(this);
        model.getDataList("Categories");
        model.getDataList("Periods");
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
        model.addItemWithURI(lotNum, name, description, category, period, uri);
    }

    public String getFileExt(Uri uri){
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(
                view.getContext().getContentResolver().getType(uri));
    }

    public void setAdded(boolean exist){
        view.addSuccess(exist);
    }

    public void setUploaded(){
        view.displayMessage("Upload Successful");
    }

    public void setError(String msg){
        view.displayMessage(msg);
    }

    public void popList(String path, ArrayList<String> list) {
        view.popSpinner(path, list);
    }
}
