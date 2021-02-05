package com.alexlbz.collecothque.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alexlbz.collecothque.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private final static Integer REQUEST_CODE_PERMISION_CAMERA = 0;
    public final static String BUNDLE_EXTRA_CODE_ISBN = "BUNDLE_EXTRA_CODE_ISBN";
    public static final String BUNDLE_EXTRA_COLLECTION = "BUNDLE_EXTRA_COLLECTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        init();
    }

    private void init() {
        if (ContextCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(ScannerActivity.this, new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_PERMISION_CAMERA);
        } else {
            startScanning();
        }
    }

    private void startScanning() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final com.google.zxing.Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        returnISBN(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    private void returnISBN(String isbn){
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_EXTRA_CODE_ISBN, isbn);
        intent.putExtra(BUNDLE_EXTRA_COLLECTION, getIntent().getSerializableExtra(BookListActivity.INTENT_EXTRA_COLLECTION));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                Toast.makeText(this, "Veuillez autoriser la caméra", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}