package com.example.b07project;

import static java.lang.Integer.max;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportPresenter {
    Context context;
    private ReportFragment fragment;
    private ExecutorService executorService;
    private Handler mainHandler;


    public ReportPresenter(ReportFragment fragment, Context context){
        this.fragment = fragment;
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.executorService = Executors.newSingleThreadExecutor();
    }


    public void withDescClicked(String query, String selectedItem){
        if(selectedItem == "All Items"){
            List<Item> itemList = new ArrayList<Item>();
            itemList.add(new Item());
            itemList.add(new Item());
            itemList.add(new Item());
            itemList.add(new Item());
            itemList.add(new Item());
            executorService.execute(new GeneratePdfTask(context, itemList));
            Log.d("GENERATED", "generated the new pdf");
        }
        else if(Objects.equals(query, "")){
            fragment.displayMessage(context, "Query cannot be empty");
        }
//        else if(searchItem().isEmpty()){
//            fragment.displayMessage(context, "No data found that matches your search");
//        }
        else{
            List<Item> itemList = new ArrayList<Item>();
            itemList.add(new Item());
            itemList.add(new Item());
            itemList.add(new Item());
            itemList.add(new Item());
            itemList.add(new Item());
            executorService.execute(new GeneratePdfTask(context, itemList));
            Log.d("GENERATED", "generated the new pdf");
        }

    }

    private ArrayList<Item> searchItem() {
        return new ArrayList<Item>();
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

        currentHeight = 10 + drawRow(canvas, paint, header, 10, true, context);

        for (int i = 0; i < itemList.size(); i++) {

            row[0] = "" + itemList.get(i).lotNumber;
            row[1] = itemList.get(i).name;
            row[2] = itemList.get(i).category;
            row[3] = itemList.get(i).period;

            if (currentHeight >= (pageHeight - 100)) {
                pdfDocument.finishPage(page);
                currentPageNumber++;
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create();
                page = pdfDocument.startPage(pageInfo);
                canvas = page.getCanvas();

                currentHeight = 10 + drawRow(canvas, paint, header, 10,true, context);

            }

            currentHeight += drawRow(canvas, paint, row, currentHeight, false, context);
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
            fragment.displayMessage(context,"Report Generated: " + fileName);
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
            int pageNum = 0;

            for (Item item : items) {

                name = new TextView(context);
                description = new TextView(context);

                description.setHeight(750);
                description.setWidth(600);
                description.setTextColor(Color.BLACK);

                name.setHeight(200);
                name.setWidth(425);
                name.setTextSize(18);
                name.setTextColor(Color.BLACK);
                name.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

                Bitmap bitmap;
                Log.i("In here", "fashkj");
                if(item.uri.trim().isEmpty()){
                    bitmap = loadImage(
                            "https://firebasestorage.googleapis.com/v0/b/b07project-d4d14.appspot." +
                                    "com/o/uploads%2Fno-image-available.jpg?alt=media&token=" +
                                    "7c5b44ac-7f76-459c-a8d2-a6376961505f");
                } else {
                    bitmap = loadImage(item.uri);
                }
                Log.w("BITMAP", "Generated");

                if(!item.description.trim().isEmpty()) {
                    description.setText(item.description);
                    descriptionSize = max(18 - item.description.length() / 120, 2);
                } else{
                    description.setText("No description available");
                }
                if(!item.name.trim().isEmpty()) {
                    name.setText(item.name);
                    nameSize = max(24 - item.name.length() / 40, 12);
                } else{
                    name.setText("Unnamed");
                }


                description.setTextSize(descriptionSize);
                name.setTextSize(nameSize);

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 900, pageNum).create();
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
                pageNum++;
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
