package com.example.b07Project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import com.example.b07project.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();


        db = FirebaseDatabase.getInstance("https://b07project-d4d14-default-rtdb.firebaseio.com/");
        itemsRef = db.getReference().child("Items");
        loadData();
    }
    private void loadData () {
            Query query;
            query = itemsRef.orderByKey().startAfter("-1");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    itemList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Item item = snapshot.getValue(Item.class);
                        itemList.add(item);
                    }
                    adapter = new ItemAdapter(itemList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }