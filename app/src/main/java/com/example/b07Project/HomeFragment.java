package com.example.b07Project;

import static com.example.b07project.R.id.button;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.b07project.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Button buttonAdmin ,buttonBack,buttonRemove,buttonAdd, buttonReport,buttonSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_main, container, false);
         buttonAdmin = view.findViewById(button);
         buttonBack = view.findViewById(R.id.back_button);
         buttonRemove = view.findViewById(R.id.remove_button);
         buttonAdd = view.findViewById(R.id.add_button);
         buttonReport = view.findViewById(R.id.report_button);
         buttonSearch = view.findViewById(R.id.search_button);

        buttonAdd.setVisibility(View.GONE);
        buttonReport.setVisibility(View.GONE);
        buttonRemove.setVisibility(View.GONE);
        buttonBack.setVisibility(View.GONE);
        buttonSearch.setVisibility(View.VISIBLE);

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
                /*fragmentManager.beginTransaction()
                        .add(android.R.id.content, presenter.view, "adminFragment")
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();*/
                buttonAdmin.setVisibility(View.GONE);
                buttonSearch.setVisibility(View.GONE);
            }
        });

        if (AdminFragmentModel.isAdmin){
            buttonAdmin.setVisibility(View.GONE);
            buttonAdd.setVisibility(View.VISIBLE);
            buttonReport.setVisibility(View.VISIBLE);
            buttonRemove.setVisibility(View.VISIBLE);
            buttonBack.setVisibility(View.VISIBLE);

        }


        return view;
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
}
