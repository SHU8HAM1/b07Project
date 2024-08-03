package com.example.b07Project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07project.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button SearchButton = findViewById(R.id.searchButton);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(new SearchFragment(), "search");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }

    public void search(View view) {
        Intent intent = new Intent(MainActivity.this, SearchFragment.class);
        startActivity(intent);

    }

}