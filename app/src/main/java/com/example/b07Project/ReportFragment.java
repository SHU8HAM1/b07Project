package com.example.b07project;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Locale;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ReportFragment extends DialogFragment{

    private EditText query;
    private Spinner type;
    private Button noDesc;
    private Button withDesc;
    private boolean description;

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



        noDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = false;
                searchItem();
            }
        });



        withDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = true;
                createDescPDF();
            }
        });

        noDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = true;
                createPdf();
            }
        });

        return view;
    }

    private ArrayList<Item> searchItem(){

        String search = query.getText().toString().trim();
        String criteria = type.getSelectedItem().toString().toLowerCase();

        Item a = new Item();
        ArrayList<Item> listed = new ArrayList<Item>();
        listed.add(a);
        return listed;
    }

    private void createDescPDF() {
        PdfDocument document = new PdfDocument();

        //PdfDocument.Page page = document.startPage(pageInfo);

        View picView = LayoutInflater.from(getContext()).inflate(R.layout.pdf_pic, null);
        DisplayMetrics dm = new DisplayMetrics();

        dm = getResources().getDisplayMetrics();

        picView.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));

        picView.layout(0, 0, dm.widthPixels, dm.heightPixels);

        int viewWidth = 1200;
        int viewHeight = 800;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create();

        // Start a new page
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the Canvas object to draw on the page
        Canvas canvas = page.getCanvas();

        // Create a Paint object for styling the view
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC));

        String text = "Report: Pictures and Description";
        float x = 300;
        float y = 200;

        canvas.drawText(text, x, y, paint);

        // Finish the page
        document.finishPage(page);

        ArrayList<Item> itemList = new ArrayList<Item>();
        Item item = new Item();
        itemList.add(item);

        int len = itemList.size();

        TextView description = picView.findViewById(R.id.body);
        TextView title = picView.findViewById(R.id.title_view);
        ImageView picture = picView.findViewById(R.id.picture);

        paint.setColor(Color.WHITE);

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://b07project-d4d14.appspot.com/uploads/background.jpg");
        String img = "https://firebasestorage.googleapis.com/v0/b/b07project-d4d14.appspot.com/o/uploads%2F1721535525982.jpg?alt=media&token=923650c4-6b88-4c61-bf33-decae337711a";

        Picasso.get().setLoggingEnabled(true);

//        Picasso.get()
//                .load(img)
//                .resize(100, 100)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(picture);


        Picasso.get().load(img).fetch(new Callback() {
            @Override
            public void onSuccess() {
                                                   // Now load the image into the ImageView
                Picasso.get()
                        .load(img)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(picture);
            }
            @Override
            public void onError(Exception e) {
                Log.e("Picasso", "Error pre-fetching image", e);
            }
        });

//        Picasso.get().setLoggingEnabled(true);


//
//        storageReference.getDownloadUrl().addOnSuccessListener(
//                new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get()
//                                .load(img)
//                                .resize(100, 100)
//                                .placeholder(R.mipmap.ic_launcher)
//                                .into(picture);
//                    }
//                }
//        );



        for(int i = 0; i < len; i++){
            pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 2).create();
            page = document.startPage(pageInfo);
            canvas = page.getCanvas();
            description.setText(itemList.get(0).description);
            title.setText(itemList.get(0).name);
            picView.draw(canvas);
            document.finishPage(page);
        }

        // Specify the path and filename of the output PDF file
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "exampleXML.pdf";
        File filePath = new File(downloadsDir, fileName);


        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            document.writeTo(fos);
            document.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while converting to PDF
        }
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
        int startY = margin + rowHeight + 20;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Draw table header
        drawRow(canvas, paint, header, startY - rowHeight, columnWidths, margin, true);

        int currentRowNumber = 0;
        for (int i = 0; i < itemList.size(); i++) {

            row[0] = "" + itemList.get(i).getLot_num();
            row[1] = itemList.get(i).getName();
            row[2] = itemList.get(i).getCategory();
            row[3] = itemList.get(i).getPeriod();

            if (currentRowNumber == ROWS_PER_PAGE) {
                pdfDocument.finishPage(page);
                currentPageNumber++;
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create();
                page = pdfDocument.startPage(pageInfo);
                canvas = page.getCanvas();

                // Draw table header on new page

                drawRow(canvas, paint, header, startY - rowHeight, columnWidths, margin, true);

                startY = margin + rowHeight;
                currentRowNumber = 0;
            }

            drawRow(canvas, paint, row, startY, columnWidths, margin, false);
            startY += rowHeight;
            currentRowNumber++;
        }

        pdfDocument.finishPage(page);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "exampleTable.pdf";
        File filePath = new File(downloadsDir, fileName);


        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while converting to PDF
        }

    }



    private static void drawRow(Canvas canvas, Paint paint, String[] row, int y, int[] width,
                                int margin, boolean head) {
        paint.setTextSize(16);
        paint.setFakeBoldText(head);

        int x = margin + 15;
        for (int i = 0; i < row.length; i++) {
            int columnWidth = width[i];
            canvas.drawText(row[i], x, y, paint);
            x += columnWidth + margin;
            canvas.drawLine (x - margin/2, 0, x - margin/2, 800, paint);
        }
        canvas.drawLine (0, y + 5,595, y + 5, paint);
    }

}

