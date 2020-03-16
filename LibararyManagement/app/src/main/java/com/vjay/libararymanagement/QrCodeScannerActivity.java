package com.vjay.libararymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScannerActivity extends AppCompatActivity  implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        intent.putExtra("qrcontent", result.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
