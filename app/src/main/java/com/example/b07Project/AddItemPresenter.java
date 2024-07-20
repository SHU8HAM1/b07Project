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
            Toast.makeText(view.getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Item newItem = new Item(category, description, name, period);
        lotNum = String.valueOf(Integer.parseInt(lotNum));
        model.addItem(lotNum, newItem);
    }

    public void setAdded(boolean exist){
        view.addSuccess(exist);
    }
    public void setDataBaseError(String msg){
        view.displayMessage("Database error: " + msg);
    }
    public void getList(String path, ArrayList<String> list){
        if(path.equals("Categories")){
            view.popSpinnerCategory(list);
        }
        if(path.equals("Periods")){
            view.popSpinnerPeriod(list);
        }
    }
    public void getPeriodList(ArrayList<String> per){
        view.popSpinnerPeriod(per);
    }
}
