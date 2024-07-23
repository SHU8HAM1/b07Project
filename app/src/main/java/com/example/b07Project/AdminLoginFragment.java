package com.example.b07Project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminLoginFragment extends Fragment {

    protected Button buttonLogin;
    protected EditText editTextUsername, editTextPassword;
    protected TextView username, password; //username = email
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        //to sign out user, use: FirebaseAuth.getInstance().signOut();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
        return view;
    }
    private void verify(){
        String email = editTextUsername.getText().toString().trim();
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
                                //sign in was successful, what next?
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                return;
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
        /*
        * if *currentUser != null {
        *  update UI for admin }
        * */

    }



}