package com.example.b07Project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.b07project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemoveActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public String lotNumber;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        Intent intent = getIntent();
        lotNumber = intent.getStringExtra("lotNumber");

        mDatabase = FirebaseDatabase.getInstance().getReference("Items");

    }

    public void removeConfirm(View view) {

        mDatabase.child(lotNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to remove value.", error.toException());
            }
        });

        removeDeny(view);
    }

    public void removeDeny(View view) {

        Intent intent = new Intent(RemoveActivity.this, SearchActivity.class);
        startActivity(intent);
    }

}