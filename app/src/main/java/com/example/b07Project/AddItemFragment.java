package com.example.b07Project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.b07project.R;

import java.util.ArrayList;

public class AddItemFragment extends Fragment{
    protected EditText editLotNumber, editName, editDescription;
    protected Spinner spinnerCategory, spinnerPeriod;
    protected Button buttonUpload, buttonSubmit;
    protected AddItemPresenter presenter;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        editLotNumber = view.findViewById(R.id.editLotNumber);
        editName = view.findViewById(R.id.editName);
        editDescription = view.findViewById(R.id.editDescription);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerPeriod = view.findViewById(R.id.spinnerPeriod);
        buttonUpload = view.findViewById(R.id.buttonUpload);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        presenter = new AddItemPresenter(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String lotNumString =  editLotNumber.getText().toString();
               String name = editName.getText().toString().trim();
               String description = editDescription.getText().toString().trim();
               String category = spinnerCategory.getSelectedItem().toString();
               String period = spinnerPeriod.getSelectedItem().toString();
               presenter.checkItem(lotNumString, name, description, category, period);
           }
        });

        return view;
    }

    public void addSuccess(boolean added){
        if (added) {
            displayMessage("Item added");
        } else {
            displayMessage("Failed to add item");
        }
    }

    public void displayMessage(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void popSpinnerCategory(ArrayList<String> cat){
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, cat);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
    }

    public void popSpinnerPeriod(ArrayList<String> per){
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, per);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(periodAdapter);
    }
}
