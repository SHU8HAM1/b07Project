package com.example.b07Project;

import java.util.ArrayList;

public class AddItemPresenter{
    private AddItemFragment view;
    private AddItemModel model;
    private static final String TAG = "AddItemPresenter";
    public AddItemPresenter(AddItemFragment view) {
        this.view = view;
        this.model = new AddItemModel(this);
        model.getDataList("Categories");
        model.getDataList("Periods");
    }

    public void checkItem(String lotNumString, String name, String description, String category, String period){
        if (lotNumString.isEmpty() || name.isEmpty() || category.isEmpty() || period.isEmpty()) {
            view.displayMessage("Please fill out all fields");
            return;
        }
        int lotNum = Integer.parseInt(lotNumString);
        Item newItem = new Item(lotNum, category, description, name, period);

        model.addItem(newItem);
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
