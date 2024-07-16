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

public class AddItemFragment extends Fragment{
    private EditText editLotNumber, editName, editDescription;
    private Spinner spinnerCategory, spinnerPeriod;
    private Button buttonUpload, buttonSubmit;
    private AddItemPresenter presenter;

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

        presenter = new AddItemPresenter(this, new AddItemModel());

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item), periodAdapter =
                ArrayAdapter.createFromResource(getContext(), R.array.periods_array,
                        android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerPeriod.setAdapter(periodAdapter);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addItem();
           }
        });
        return view;

    }

    private void addItem(){

    }


}
