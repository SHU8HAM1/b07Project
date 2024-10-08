package com.example.b07project;

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
        String emailVerify = email.getText().toString().trim();
        String passwordVerify = password.getText().toString().trim();
        model.verify(emailVerify, passwordVerify, new AuthenticationCallback() {
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
