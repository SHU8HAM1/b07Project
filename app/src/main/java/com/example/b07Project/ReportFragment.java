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


    private void compilePdf(){
        PdfDocument document = new PdfDocument();
        URL url;

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


        for(int i = 0; i < len; i++) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        String fileName = "Description_" + timestamp + ".pdf";
        File filePath = new File(downloadsDir, fileName);


        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            document.writeTo(fos);
            document.close();
            fos.close();
            displayMessage("File name: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while converting to PDF
        }
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
            //TextViewCompat.setAutoSizeTextTypeWithDefaults(description, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);;
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
                nameSize = max(24 - item.getDescription().length()/40, 8);

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

