package com.example.b07Project;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

public class AdminFragmentPresenter {

    public AdminFragmentView view;
    public AdminFragmentModel model;

    public AdminFragmentPresenter(AdminFragmentView view, AdminFragmentModel model){
        this.view = view;
        this.model = model;

    }

    public void onClickLogin(EditText email, EditText password){
        if (email == null || email.toString().isEmpty() || password == null || password.toString().isEmpty()){
            view.showLoginFailure();
            return;
        }
        model.verify(email, password, new AuthenticationCallback() {
            @Override
            public void onSuccess() {
                view.showLoginSuccess();
                view.back();

            }

            @Override
            public void onFail() {
                view.showLoginFailure();
            }
        });


    }


}
