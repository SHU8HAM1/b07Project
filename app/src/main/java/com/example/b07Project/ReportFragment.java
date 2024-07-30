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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.storage.images.FirebaseImageLoader;

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
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.request.RequestOptions;



public class ReportFragment extends DialogFragment{

    private EditText query;
    private Spinner type;
    private Button noDesc;
    private Button withDesc;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.report_popup, container, false);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        query = view.findViewById(R.id.report_query);
        type = view.findViewById(R.id.spinnerCategory);
        noDesc = view.findViewById(R.id.noDesc);
        withDesc = view.findViewById(R.id.Desc);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);



        withDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(query.getText().toString().trim() == "" &&
                        type.getSelectedItem().toString() != "All Items"){
                    displayMessage("Query cannot be empty");
                }
                else if(searchItem().size() == 0){
                    displayMessage("No item found matching the search");
                }
                else{
                    List<Item> itemList = new ArrayList<Item>();
                    itemList.add(new Item());
                    executorService.execute(new GeneratePdfTask(getContext(), itemList));
                    //DescriptionPdf.newPdf();
                    Log.d("GENERATED", "generated the new pdf");
                }
            }
        });

        noDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf();
            }
        });

        return view;
    }

    public void displayMessage(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Item> searchItem(){

        String search = query.getText().toString().trim();
        String criteria = type.getSelectedItem().toString().toLowerCase();

        Item a = new Item();
        ArrayList<Item> listed = new ArrayList<Item>();
        listed.add(a);
        return listed;
    }


    public void createPdf(){
        int pageWidth = 595; // A4 dimensions
        int pageHeight = 842;
        int rowHeight = 50;
        int margin = 7;
        int[] columnWidths = {80,160,160,160};
        String[] row = {"Lot", "Name", "Category", "Period"};
        String[] header = {"Lot #", "Name", "Category", "Period"};
        final int ROWS_PER_PAGE = 15;

        ArrayList<Item> itemList = new ArrayList<Item>();

        Item item = new Item();
        for(int num = 0; num < 19; num++) {
            itemList.add(item);
        }



        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();



        int currentPageNumber = 1;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int currentHeight = 0;

        currentHeight = 10 + drawRow(canvas, paint, header, 10, true, getContext());

        for (int i = 0; i < itemList.size(); i++) {

            row[0] = "" + itemList.get(i).getLot_num();
            row[1] = itemList.get(i).getName();
            row[2] = itemList.get(i).getCategory();
            row[3] = itemList.get(i).getPeriod();

            if (currentHeight >= (pageHeight - 100)) {
                pdfDocument.finishPage(page);
                currentPageNumber++;
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create();
                page = pdfDocument.startPage(pageInfo);
                canvas = page.getCanvas();

                currentHeight = 10 + drawRow(canvas, paint, header, 10,true, getContext());

            }

            currentHeight += drawRow(canvas, paint, row, currentHeight, false, getContext());
        }

        pdfDocument.finishPage(page);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        String fileName = "No_Description_" + timestamp + ".pdf";
        File filePath = new File(downloadsDir, fileName);


        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();
            displayMessage("Report Generated: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while converting to PDF
        }

    }



    private static int drawRow(Canvas canvas, Paint paint, String[] row, int y, boolean head, Context context) {
        TableRow tableRow = new TableRow(context);

        TextView col1 = new TextView(context);
        TextView col2 = new TextView(context);
        TextView col3 = new TextView(context);
        TextView col4 = new TextView(context);

        if(head) {
            tableRow.setBackgroundColor(Color.GRAY);
        }

        col1.setWidth(80);
        col2.setWidth(160);
        col3.setWidth(160);
        col4.setWidth(160);

        col1.setText(row[0]);
        col2.setText(row[1]);
        col3.setText(row[2]);
        col4.setText(row[3]);

        col1.setTextSize(9);
        col2.setTextSize(9);
        col3.setTextSize(9);
        col4.setTextSize(9);


        TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2f);

        params1.setMargins(5, 5, 5, 5);
        params2.setMargins(5, 5, 5, 5);

        col1.setLayoutParams(params1);
        col2.setLayoutParams(params2);
        col3.setLayoutParams(params2);
        col4.setLayoutParams(params2);

        col1.setPadding(5, 5, 5, 5);
        col2.setPadding(5, 5, 5, 5);
        col3.setPadding(5, 5, 5, 5);
        col4.setPadding(5, 5, 5, 5);

        tableRow.addView(col1);
        tableRow.addView(col2);
        tableRow.addView(col3);
        tableRow.addView(col4);

        tableRow.measure(590, View.MeasureSpec.UNSPECIFIED);
        tableRow.layout(0, 0, 590, tableRow.getMeasuredHeight());

        canvas.save();
        canvas.translate(10, y);
        tableRow.draw(canvas);
        canvas.restore();

        return tableRow.getMeasuredHeight();
    }







    private static class GeneratePdfTask implements Runnable {
        private final List<Item> items;
        private final Context context;

        GeneratePdfTask(Context context, List<Item> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public void run() {
            PdfDocument pdfDocument = new PdfDocument();


            TextView description = new TextView(context);
            description.setHeight(750);
            description.setWidth(600);
            description.setTextColor(Color.BLACK);

            int descriptionSize = 18;
            int nameSize = 24;

            TextView name = new TextView(context);
            name.setHeight(200);
            name.setWidth(425);
            name.setTextSize(18);
            //name.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            name.setTextColor(Color.BLUE);
            name.setAllCaps(true);
            name.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC));


            for (Item item : items) {
                Log.i("In here", "fashkj");

                Bitmap bitmap = loadImage(item.getPicture());
                Log.w("BITMAP", bitmap.toString());

                description.setText(item.getDescription());
                name.setText(item.getName());

                descriptionSize = max(20 - item.getDescription().length()/120, 2);
                nameSize = max(28 - item.getDescription().length()/40, 12);

                description.setTextSize(descriptionSize);
                name.setTextSize(nameSize);

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 900, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                name.layout(0, 0, 425, 200);
                Canvas canvas = page.getCanvas();
                canvas.save();
                canvas.translate(730, 30);
                name.draw(canvas);
                canvas.restore();

                description.layout(0, 0, 600, 750);

                canvas.save();
                canvas.translate(40, 30);
                description.draw(canvas);
                canvas.restore();
                canvas.drawBitmap(bitmap, 690, 275, null);

                pdfDocument.finishPage(page);
            }

            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = dateFormat.format(new Date());

            String fileName = "Description_" + timestamp + ".pdf";
            File filePath = new File(downloadsDir, fileName);

            try {
                // Save the document to a file
                Log.i("YEES", "sdfjkhkjf");
                FileOutputStream fos = new FileOutputStream(filePath);
                pdfDocument.writeTo(fos);
                pdfDocument.close();
                fos.close();
            } catch (IOException e) {
                Log.e("NOFILE", "Maaannn");
                e.printStackTrace();
                // Error occurred while converting to PDF
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                // Notify UI thread about the completion, e.g., display a message or update the UI
                Toast.makeText(context, "PDF Generated: " + fileName, Toast.LENGTH_SHORT).show();
            });
        }

        private Bitmap loadImage(String url) {
            try {
                URL connection = new URL(url);
                Log.e("URLCONNECT", connection.toString());
                InputStream set = (InputStream) connection.getContent();
                Log.d("STREAM", set.toString());
                Bitmap stream = BitmapFactory.decodeStream(set);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(stream, 450, 550, true);
                Log.i("BITMAPPP", stream.toString());
                return resizedBitmap;
            } catch (Exception e) {
                Log.e("WRONGACCESS", "Not good");
                return null;
            }
        }
    }

}

