package com.example.b07Project;

import android.widget.ArrayAdapter;

public class AddItemPresenter {
    private AddItemFragment view;
    private AddItemModel model;

    public AddItemPresenter(AddItemFragment view, AddItemModel model) {
        this.view = view;
        this.model = model;
    }
}
