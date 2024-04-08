package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeList extends AppCompatActivity {

    private String[] name = {"Joe Smith", "Tom Smith"};
    private ImageView qrCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_list);
        qrCode = (ImageView) findViewById(R.id.imageView);
        MultiFormatWriter mWriter = new MultiFormatWriter();
        for(int i = 0; i < name.length; i++) {
            try {
                BitMatrix mMatrix = mWriter.encode(name[i], BarcodeFormat.QR_CODE, 400, 400);
                BarcodeEncoder mEncoder = new BarcodeEncoder();
                Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
                qrCode.setImageBitmap(mBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}