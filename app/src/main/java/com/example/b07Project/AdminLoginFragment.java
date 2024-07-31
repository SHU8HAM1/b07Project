package com.example.b07Project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminLoginFragment extends Fragment {

    private Button buttonLogin, buttonRegister;
    private EditText editTextEmail, editTextPassword;
    private TextView email, password; // NOTE: email = email
    private FirebaseAuth mAuth;
    public static boolean isAdmin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            View view = inflater.inflate(R.layout.fragment_admin_login, container, false);
            editTextEmail = view.findViewById(R.id.editTextEmail);
            editTextPassword = view.findViewById(R.id.editTextPassword);
            buttonLogin = view.findViewById(R.id.buttonLogin);
            buttonRegister = view.findViewById(R.id.buttonRegister);
            email = view.findViewById(R.id.email);
            password = view.findViewById(R.id.password);
            mAuth = FirebaseAuth.getInstance();
            //to sign out user, use: FirebaseAuth.getInstance().signOut();
            buttonRegister.setVisibility(View.GONE);
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verify();
                }
            });


            return view;
        }
        return null;

    }
    private void verify(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if ((email == null) && (email.isEmpty()) || (password ==null && password.isEmpty()) ){
            return; // email or password is invalid
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                isAdmin = true;
                                //sign in was successful
                                FirebaseUser user = mAuth.getCurrentUser();

                                Log.d("ADMIN", "Successful");

                            } else {
                                isAdmin = false;
                                Log.d("ADMIN", "failed");

                            }
                        }
                    });
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if admin is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            isAdmin = false;
        }
    }



}