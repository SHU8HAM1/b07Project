package com.example.b07project;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.b07project.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminFragmentView extends Fragment {

    private Button buttonLogin, buttonRegister;
    private EditText editTextEmail, editTextPassword;
    private TextView email, password;
    public AdminFragmentPresenter presenter;
    public AdminFragmentView(){
        presenter = null;
    }

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

            //to sign out user, use: FirebaseAuth.getInstance().signOut();
            buttonRegister.setVisibility(View.GONE);
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    presenter.onClickLogin(editTextEmail, editTextPassword);

                }
            });
            return view;
        }
        return null;
    }

    public void showLoginSuccess() {
        Log.d("ADMIN", "Successful");
    }

    public void showLoginFailure() {
        Log.d("ADMIN", "Failed");
    }
    public void back() {
        if (getActivity() != null) {

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }



}
