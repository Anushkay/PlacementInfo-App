package com.project.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ViewPdfActivity extends AppCompatActivity {
    private PDFView pdfView;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ProgressDialog progressDialog;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfView = (PDFView) findViewById(R.id.pdf_view);


         filename = getIntent().getStringExtra("filename");
        String url = getIntent().getStringExtra("url");

        progressDialog = new ProgressDialog(this);


        progressDialog.setTitle("PDF - " + filename);
        progressDialog.setMessage("Opening...");
        progressDialog.show();

        new RetrivePdfStream().execute(url);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewPdfActivity.this,AdminDashBoard.class);

        intent.putExtra("Check",1);
        startActivity(intent);

    }


    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream = new ByteArrayInputStream(filename.getBytes());
            try {

                // adding url
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // if url connection response code is 200 means ok the execute
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            // if error return null
            catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        // Here load the pdf and dismiss the dialog box
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
            progressDialog.dismiss();
        }
    }
}


