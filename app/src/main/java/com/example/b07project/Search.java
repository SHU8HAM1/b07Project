package com.example.b07project;

import static java.lang.Integer.parseInt;

import com.example.b07project.Item;

import static java.lang.Integer.parseInt;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class Search {

    static String TAG = "SearchActivity";

    public static void readData(DatabaseReference db, final List<Item> itemList, DataReadCallback callback) {

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    itemList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String lotNumber = snapshot.getKey();
                        Item item = snapshot.getValue(Item.class);
                        Log.i("VALUEGOT", item.name);
                        assert lotNumber != null;
                        assert item != null;
                        item.lotNumber = Integer.parseInt(lotNumber);
                        itemList.add(item);
                    }
                    Log.i("YESIN", itemList.get(1).name);

                    printItemList(itemList);
                    callback.onDataRead(itemList);
                } catch (Exception e) {
                    Log.e(TAG, "Database Error.", e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error Reading Value", error.toException());
            }
        });
    }

    public static List<Item> searchByLotNumber(DatabaseReference db, int lotNumber, DataReadCallback callback) {

        List<Item> searchList = new ArrayList<>();

        db.child(String.valueOf(lotNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                searchList.clear(); // Clear the list to avoid duplication

                searchList.clear();

                Item item = dataSnapshot.getValue(Item.class);
                if (item != null) {
                    item.lotNumber = lotNumber;
                    searchList.add(item);
                    printSearchList(searchList);
                } else {
                    Log.w(TAG, "No item found with lot number: " + lotNumber);
                }
                callback.onDataRead(searchList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error Reading Value", error.toException());
            }
        });
        return searchList;
    }

    public static List<Item> searchByOther(DatabaseReference db, String name, String category,
                                    String period, DataReadCallback callback) {

        List<Item> searchList = new ArrayList<>();

        db.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                searchList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Item item = snapshot.getValue(Item.class);

                    if (snapshot.getKey() != null) {
                        assert item != null;
                        try {
                            item.lotNumber = parseInt((snapshot.getKey()));
                        } catch (Exception e) {
                            Log.w(TAG, "Lot Number is a String." + snapshot.getKey());
                        }

                    }
                    if (item != null) {
                        boolean matches = true;

                        if (name.isEmpty() && category.isEmpty() && period.isEmpty()) {
                            matches = false;
                        }
                        if (!name.isEmpty() && !item.name.equalsIgnoreCase(name)
                        ) {
                            matches = false;
                        }
                        if (!category.isEmpty() && !item.category.equalsIgnoreCase(category)) {
                            matches = false;
                        }
                        if (!period.isEmpty() && !item.period.equalsIgnoreCase(period)) {
                            matches = false;
                        }
                        if (matches) {
                            searchList.add(item);
                        }
                    } else {
                        Log.w(TAG, "Item is null" + snapshot.getKey());
                    }

                }
                callback.onDataRead(searchList);
                printSearchList(searchList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error Reading Value", error.toException());
            }
        });
        return searchList;
    }

    public static void printSearchList(List<Item> searchList) {

        if (!(searchList.isEmpty())) {
            for (Item item : searchList) {
                Log.d(TAG, "(Searched Item) LotNumber: " + item.lotNumber + ", Item name: " + item.name + ", description: " + item.description + ", category: " + item.category + ", period: " + item.period + ", uri: " + item.uri);
            }
        } else {
            Log.d(TAG, "No Items Found with that search");
        }

    }

    public static List<Item> sendSearchList(List<Item> searchList) {

        return searchList;

    }

    static void printItemList(List<Item> itemList) {

        for (Item item : itemList) {
            Log.d(TAG, "LotNumber: " + item.lotNumber + ", Item name: " + item.name + ", description: " + item.description + ", category: " + item.category + ", period: " + item.period + ", uri: " + item.uri);
        }

    }

    public interface DataReadCallback {
        void onDataRead(List<Item> itemList);
    }


}