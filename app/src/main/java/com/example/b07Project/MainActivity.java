package com.example.b07Project;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07project.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity{

    DatabaseReference mDatabase;

    private static final String TAG = "MainActivity";

    List<Item> itemList = new ArrayList<>();
    List<Item> searchList = new ArrayList<>();

    TextView textLotNum;
    TextView textName;
    TextView textCategory;
    TextView textPeriod;

    EditText inputLotNum;
    EditText inputName;
    EditText inputCategory;
    EditText inputPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("Items");

        textLotNum = findViewById(R.id.textLotNum);
        inputLotNum = findViewById(R.id.inputLotNum);

        textName = findViewById(R.id.textName);
        inputName = findViewById(R.id.inputName);

        textCategory = findViewById(R.id.textCategory);
        inputCategory = findViewById(R.id.inputCategory);

        textPeriod = findViewById(R.id.textPeriod);
        inputPeriod = findViewById(R.id.inputPeriod);

        readData();

    }

    public void readData() {

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                itemList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String lotNumber = snapshot.getKey();
                    Item item = snapshot.getValue(Item.class);
                    assert lotNumber != null;
                    assert item != null;
                    item.lotNumber = parseInt(lotNumber);
                    itemList.add(item);
                }

                printItemList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error Reading");
            }

        });
    }



    public boolean searchItems(View view) {

        String lotNumberInput = inputLotNum.getText().toString();
        String nameInput = inputName.getText().toString().trim();
        String categoryInput = inputCategory.getText().toString().trim();
        String periodInput = inputPeriod.getText().toString().trim();

        if (!lotNumberInput.isEmpty()) {
            int lotNumber = Integer.parseInt(lotNumberInput);
            searchByLotNumber(mDatabase, lotNumber);
        } else {
            // If lot number is not provided, search by other criteria
            searchByOtherCriteria(mDatabase, nameInput, categoryInput, periodInput);
        }

        return !(searchList.isEmpty());
    }

    private void searchByLotNumber(DatabaseReference mDatabase, int lotNumber) {

        mDatabase.child(String.valueOf(lotNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchList.clear(); // Clear the list to avoid duplication
                Item item = dataSnapshot.getValue(Item.class);
                if (item != null) {
                    item.lotNumber = lotNumber;
                    searchList.add(item);
                    printSearchList();
                    // Update UI after this
                } else {
                    Log.w(TAG, "No item found with lot number: " + lotNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void searchByOtherCriteria(DatabaseReference mDatabase, String name, String category, String period) {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    if (snapshot.getKey() != null) {
                        assert item != null;
                        item.lotNumber = parseInt((snapshot.getKey()));
                    }
                    if (item != null) {
                        boolean matches = true;

                        if (name.isEmpty() && category.isEmpty() && period.isEmpty()) {
                            matches = false;
                        }

                        if (!name.isEmpty() && !item.name.equals(name)) {
                            matches = false;
                        }
                        if (!category.isEmpty() && !item.category.equals(category)) {
                            matches = false;
                        }
                        if (!period.isEmpty() && !item.period.equals(period)) {
                            matches = false;
                        }
                        if (matches) {
                            searchList.add(item);
                        }
                    } else {
                        Log.w(TAG, "Item is null for snapshot: " + snapshot.getKey());
                    }
                }
                printSearchList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void printSearchList() {

        if (!(searchList.isEmpty())) {
            for (Item item : searchList) {
                Log.d(TAG, "(Searched Item) LotNumber: " + item.lotNumber + ", Item name: " + item.name + ", description: " + item.description + ", category: " + item.category + ", period: " + item.period + ", uri: " + item.uri);
            }
        } else {
            Log.d(TAG, "No Items Found with that search");
        }

    }

    private void printItemList() {

        for (Item item : itemList) {
            Log.d(TAG, "LotNumber: " + item.lotNumber + ", Item name: " + item.name + ", description: " + item.description + ", category: " + item.category + ", period: " + item.period + ", uri: " + item.uri);
        }

    }

    public void remove(View view) {

        Intent intent = new Intent(MainActivity.this, RemoveActivity.class);
        intent.putExtra("lotNumber", inputLotNum.getText().toString());
        startActivity(intent);

    }

}

