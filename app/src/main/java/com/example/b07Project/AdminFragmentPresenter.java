package com.example.b07Project;

import android.widget.EditText;

public class AdminFragmentPresenter {

    private AdminFragmentView view;
    private AdminFragmentModel model;

    public AdminFragmentPresenter(AdminFragmentView view, AdminFragmentModel model){
        this.view = view;
        this.model = model;

    }

    public void onClickLogin(EditText email, EditText password){
        if (email == null || email.toString().isEmpty() || password == null || password.toString().isEmpty()){
            view.showLoginFailure();
            return;
        }
        model.verify(email, password);

        if (AdminFragmentModel.isAdmin){
            view.showLoginSuccess();
            return;
        }
        view.showLoginFailure();
    }

}
