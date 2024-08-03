package com.example.b07Project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        setContentView(R.layout.fragment_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button buttonAdmin = findViewById(R.id.button);
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonRemove = findViewById(R.id.remove_button);
        Button buttonAdd = findViewById(R.id.add_button);
        Button buttonReport = findViewById(R.id.report_button);
        Button buttonSearch = findViewById(R.id.search_button);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                back(v);
            }
        });

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
                    buttonSearch.setVisibility(View.GONE);
                }

            });
        buttonAdd.setVisibility(View.GONE);
        buttonReport.setVisibility(View.GONE);
        buttonRemove.setVisibility(View.GONE);
        buttonBack.setVisibility(View.GONE);
        buttonSearch.setVisibility(View.VISIBLE);

        Log.d("isAdmin", Boolean.toString(AdminFragmentModel.isAdmin));
        if (AdminFragmentModel.isAdmin){
            buttonAdmin.setVisibility(View.GONE);
            buttonAdd.setVisibility(View.VISIBLE);
            buttonReport.setVisibility(View.VISIBLE);
            buttonRemove.setVisibility(View.VISIBLE);
            buttonBack.setVisibility(View.VISIBLE);

        }

    }
    public void back(View view) {
        AdminFragmentModel.isAdmin = false;
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

}