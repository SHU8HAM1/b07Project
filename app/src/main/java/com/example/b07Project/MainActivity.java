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
        //if (savedInstanceState == null){AdminLoginFragment.isAdmin = false;}
        FragmentManager fragmentManager = getSupportFragmentManager();

        Button buttonAdmin = findViewById(R.id.buttonAdmin);
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
        if (!AdminLoginFragment.isAdmin){
            buttonAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment adminFragment = new AdminLoginFragment();
                    fragmentManager.beginTransaction()
                            .add(android.R.id.content, adminFragment, "adminFragment")
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
            /*
        if (AdminLoginFragment.isAdmin){
            //buttonAdmin.setVisibility(View.GONE);
            Fragment fragment = fragmentManager.findFragmentByTag("adminFragment");
            if (fragment != null) {
                fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
            }
        }*/

    }
    public void back(View view) {

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        //intent.putExtra("lotNumber", inputLotNum.getText().toString());
        startActivity(intent);
    }

}