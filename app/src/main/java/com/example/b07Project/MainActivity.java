package com.example.b07Project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.b07project.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button buttonAdmin = findViewById(R.id.buttonAdmin);
        Button buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                back(v);
            }
        });
        if (!AdminFragmentModel.isAdmin){
            AdminFragmentView adminView = new AdminFragmentView();
            AdminFragmentModel model = new AdminFragmentModel();
            AdminFragmentPresenter presenter = new AdminFragmentPresenter(adminView, model);
            adminView.presenter = presenter;
            buttonAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentManager.beginTransaction()
                            .add(android.R.id.content, presenter.view, "adminFragment")
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                    buttonAdmin.setVisibility(View.GONE);
                }
            });
        }


    }
    public void back(View view) {

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

}