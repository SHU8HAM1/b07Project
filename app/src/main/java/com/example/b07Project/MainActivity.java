package com.example.b07project;

import static com.example.b07project.R.id.button;

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
import androidx.fragment.app.FragmentTransaction;

import com.example.b07project.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    /*
        Button buttonAdmin = findViewById(button);
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonRemove = findViewById(R.id.remove_button);
        Button buttonAdd = findViewById(R.id.add_button);
        Button buttonReport = findViewById(R.id.report_button);
        Button buttonSearch = findViewById(R.id.search_button);*/

        if(savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }


    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void back(View view) {
        AdminFragmentModel.isAdmin = false;
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

}