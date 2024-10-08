package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.Item;
import com.example.b07project.R;
import com.example.b07project.Search;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends DialogFragment {

    DatabaseReference db;

    private static final String TAG = "SearchActivity";

    static List<Item> itemList = new ArrayList<>();
    static List<Item> searchList = new ArrayList<>();

    TextView textLotNum;
    TextView textName;
    TextView textCategory;
    TextView textPeriod;

    EditText inputLotNum;
    EditText inputName;
    EditText inputCategory;
    EditText inputPeriod;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_search, container, false);

        db = FirebaseDatabase.getInstance().getReference("Items");

        textLotNum = view.findViewById(R.id.textLotNum);
        inputLotNum = view.findViewById(R.id.inputLotNum);

        textName = view.findViewById(R.id.textName);
        inputName = view.findViewById(R.id.inputName);

        textCategory = view.findViewById(R.id.textCategory);
        inputCategory = view.findViewById(R.id.inputCategory);

        textPeriod = view.findViewById(R.id.textPeriod);
        inputPeriod = view.findViewById(R.id.inputPeriod);



        Search.readData(db, itemList, new Search.DataReadCallback() {
            @Override
            public void onDataRead(List<Item> itemList) {
                Search.printSearchList(itemList);
            }
        });

        Button searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItems();
                dismiss();
            }

        });



        return view;
    }

    public void searchItems() {

        String lotNumberInput = inputLotNum.getText().toString();
        String nameInput = inputName.getText().toString().trim();
        String categoryInput = inputCategory.getText().toString().trim();
        String periodInput = inputPeriod.getText().toString().trim();

        Search s = new Search();

        if (!lotNumberInput.isEmpty()) {
            int lotNumber = Integer.parseInt(lotNumberInput);
            s.searchByLotNumber(db, lotNumber, new Search.DataReadCallback() {
                @Override
                public void onDataRead(List<Item> itemList) {
                    if(itemList.isEmpty()){
                        Toast.makeText(getContext(), "Item Not Found", Toast.LENGTH_SHORT).show();
                    }else {
                        RecyclerViewFragment.itemList = itemList;
                        RecyclerViewFragment.updateData(itemList);
                    }
                }
            });
        } else {
            s.searchByOther(db, nameInput, categoryInput, periodInput, new Search.DataReadCallback() {
                @Override
                public void onDataRead(List<Item> itemList) {
                    if(itemList.isEmpty()){
                        Toast.makeText(getContext(), "Item Not Found", Toast.LENGTH_SHORT).show();
                    }else {
                        RecyclerViewFragment.itemList = itemList;
                        RecyclerViewFragment.updateData(itemList);
                    }
                }
            });
        }

    }

}