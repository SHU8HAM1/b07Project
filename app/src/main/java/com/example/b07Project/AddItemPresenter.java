package com.example.b07Project;

import android.widget.Toast;

import java.util.ArrayList;

public class AddItemPresenter{
    private AddItemFragment view;
    private AddItemModel model;

    public AddItemPresenter(AddItemFragment view) {
        this.view = view;
        this.model = new AddItemModel(this);
        model.getDataList("Categories");
        model.getDataList("Periods");
    }

    public void checkItem(String lotNum, String name, String description, String category, String period){
        if (lotNum.isEmpty() || name.isEmpty() || description.isEmpty() || category.isEmpty() || period.isEmpty()) {
            view.displayMessage("Please fill out all fields");
            return;
        }
        lotNum = String.valueOf(Integer.parseInt(lotNum));

        Item newItem = new Item(category, description, name, period);

        model.addItem(lotNum, newItem);
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
