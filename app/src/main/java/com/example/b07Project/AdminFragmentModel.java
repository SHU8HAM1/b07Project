package com.example.b07Project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.b07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminFragmentModel {

    FirebaseAuth mAuth;

    public static boolean isAdmin;
    public AdminFragmentModel(){
        this.mAuth =  FirebaseAuth.getInstance();
    }

    public void verify(String email, String password, AuthenticationCallback call){


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                AdminFragmentModel.isAdmin = true;
                                call.onSuccess();
                                return;
                            } else {
                                AdminFragmentModel.isAdmin= false;
                                call.onFail();
                                return;
                            }
                        }
                    });
    }




}
