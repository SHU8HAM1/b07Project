package com.example.b07Project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    private Uri uri;
    private ActivityResultLauncher<Intent> launcher;

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

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK
                            && result.getData() != null) {
                        uri = result.getData().getData();
                        displayMessage("File Selected: " + uri.getLastPathSegment());
                    }
                }
        );

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               submit();
           }
        });

        return view;
    }

    private void chooseFile(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/* video/*");
        String[] mimes = {"image/*", "video/*"};
        i.putExtra(Intent.EXTRA_MIME_TYPES, mimes);
        launcher.launch(i);
    }

    private void submit(){
        String lotNumString =  editLotNumber.getText().toString();
        String name = editName.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String period = spinnerPeriod.getSelectedItem().toString();

        presenter.checkItem(lotNumString, name, description, category, period, uri);
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

    public void popSpinner(String path, ArrayList<String> list){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(path.equals("Categories")){
            spinnerCategory.setAdapter(adapter);
        }
        if (path.equals("Periods")){
            spinnerPeriod.setAdapter(adapter);
        }
    }
}
