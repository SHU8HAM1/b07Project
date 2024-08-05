package com.example.b07project;

import static com.example.b07project.R.id.button;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static ItemAdapter adapter;
    public static List<Item> itemList;
    public static FirebaseDatabase db;
    private DatabaseReference itemsRef;
    Button buttonAdmin ,buttonBack,buttonRemove,buttonAdd, buttonReport,buttonSearch;


    public static void updateData(List<Item> items){
        Log.d("RecyclerViewFragment", "Updating data in adapter");
        adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        buttonAdmin = view.findViewById(button);
        buttonBack = view.findViewById(R.id.back_button);
        buttonRemove = view.findViewById(R.id.remove_button);
        buttonAdd = view.findViewById(R.id.add_button);
        buttonReport = view.findViewById(R.id.report_button);
        buttonSearch = view.findViewById(R.id.search_button);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemList = new ArrayList<>();


        db = FirebaseDatabase.getInstance("https://b07project-d4d14-default-rtdb.firebaseio.com/");
        itemsRef = db.getReference().child("Items");
        loadData();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminFragmentView adminView = new AdminFragmentView();
                AdminFragmentModel model = new AdminFragmentModel();
                AdminFragmentPresenter presenter = new AdminFragmentPresenter(adminView, model);
                adminView.presenter = presenter;
                loadFragment(adminView);
                buttonAdmin.setVisibility(View.GONE);
                buttonSearch.setVisibility(View.GONE);
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPopup(new ReportFragment());
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPopup(new SearchFragment());
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddItemFragment());
            }
        });

        buttonAdd.setVisibility(View.GONE);
        buttonReport.setVisibility(View.GONE);
        buttonRemove.setVisibility(View.GONE);
        buttonBack.setVisibility(View.VISIBLE);
        buttonSearch.setVisibility(View.VISIBLE);
        if (AdminFragmentModel.isAdmin){
            buttonAdmin.setVisibility(View.GONE);
            buttonAdd.setVisibility(View.VISIBLE);
            buttonReport.setVisibility(View.VISIBLE);
            buttonRemove.setVisibility(View.VISIBLE);
            adapter.setAdminMode();
        }
        return view;
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
                updateData(itemList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDatabase", "Database error", error.toException());

            }
        });

    }
    public void back(View view) {
        AdminFragmentModel.isAdmin = false;
        if (getActivity() != null) {

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadPopup(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(fragment, "report");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}