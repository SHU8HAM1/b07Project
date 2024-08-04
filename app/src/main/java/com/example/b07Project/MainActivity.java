
package com.example.b07project;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
//import android.widget.
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;




public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE = 1232;
    Button ReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ReportButton = findViewById(R.id.reportBtn);

        ReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(new ReportFragment(), "report");

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

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

        Button AddButton = findViewById(R.id.addButton);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new AddItemFragment());

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }





    public void search(View view) {
        Intent intent = new Intent(MainActivity.this, SearchFragment.class);
        startActivity(intent);

    }
    private void askPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

}
