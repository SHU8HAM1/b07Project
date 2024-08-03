package com.example.b07Project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import static java.lang.Integer.parseInt;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.b07project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {

    DatabaseReference db;

    private static final String TAG = "SearchActivity";

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
        setContentView(R.layout.activity_search);

        db = FirebaseDatabase.getInstance().getReference("Items");

        textLotNum = findViewById(R.id.textLotNum);
        inputLotNum = findViewById(R.id.inputLotNum);

        textName = findViewById(R.id.textName);
        inputName = findViewById(R.id.inputName);

        textCategory = findViewById(R.id.textCategory);
        inputCategory = findViewById(R.id.inputCategory);

        textPeriod = findViewById(R.id.textPeriod);
        inputPeriod = findViewById(R.id.inputPeriod);

        Search.readData(db);
    }



    public void searchItems(View view) {

        String lotNumberInput = inputLotNum.getText().toString();
        String nameInput = inputName.getText().toString().trim();
        String categoryInput = inputCategory.getText().toString().trim();
        String periodInput = inputPeriod.getText().toString().trim();

        if (!lotNumberInput.isEmpty()) {
            int lotNumber = Integer.parseInt(lotNumberInput);
            Search.searchByLotNumber(db, lotNumber);
        } else {
            Search.searchByOther(db, nameInput, categoryInput, periodInput);
        }

    }


    public void remove(View view) {

        Intent intent = new Intent(SearchActivity.this, RemoveActivity.class);
        intent.putExtra("lotNumber", inputLotNum.getText().toString());
        startActivity(intent);

    }

}