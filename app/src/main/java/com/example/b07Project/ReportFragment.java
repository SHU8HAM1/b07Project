package com.example.b07project;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.StaticLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.DialogFragment;
import android.graphics.pdf.PdfDocument;


import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static java.lang.Integer.max;
import static java.lang.Integer.min;




public class ReportFragment extends DialogFragment{

    private EditText query;
    private Spinner type;
    private Button noDesc;
    private Button withDesc;
    private ReportPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.report_popup, container, false);

        query = view.findViewById(R.id.report_query);
        type = view.findViewById(R.id.spinnerCategory);
        noDesc = view.findViewById(R.id.noDesc);
        withDesc = view.findViewById(R.id.Desc);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);


        presenter = new ReportPresenter(this, getContext());


        withDesc.setOnClickListener(v -> presenter.withDescClicked(query.getText().toString().trim(),
                type.getSelectedItem().toString()));

        noDesc.setOnClickListener(v -> presenter.createPdf());

        return view;
    }

    public void displayMessage(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}

