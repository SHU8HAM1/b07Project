package com.example.b07Project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import com.google.firebase.database.FirebaseDatabase;

import com.example.b07project.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private Button nextPageButton, prevPageButton;
    private int currentPage = 0;
    private final int itemsPerPage = 6;
    private FirebaseDatabase db;
//    private DocumentSnapshot lastVisible;
    private DatabaseReference itemsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();


//        nextPageButton = findViewById(R.id.next_page_button);
//        prevPageButton = findViewById(R.id.prev_page_button);

        db = FirebaseDatabase.getInstance("https://b07project-d4d14-default-rtdb.firebaseio.com/");

        loadData();

//        nextPageButton.setOnClickListener(v -> {
//            currentPage++;
//            loadData();
//        });
//
//        prevPageButton.setOnClickListener(v -> {
//            if (currentPage > 0) {
//                currentPage--;
//                loadData();
//            }
//        });

    }
    private void loadData () {
        itemsRef = db.getReference().child("Items");
//                .orderBy("lotNumber")
//                .limit(itemsPerPage);
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    itemList.add(item);
                }
                adapter = new ItemAdapter( itemList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }
    }