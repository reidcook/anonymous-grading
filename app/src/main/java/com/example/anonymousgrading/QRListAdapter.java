package com.example.anonymousgrading;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class QRListAdapter extends BaseAdapter {
    private List<Student> students;
    private Context context;
    private LayoutInflater layoutInflater;

    public QRListAdapter(Context context, List<Student> students){
        this.context = context;
        this.students = students;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_layout, null);
        TextView text = (TextView) convertView.findViewById(R.id.textView3);
        ImageView qrCode = (ImageView) convertView.findViewById(R.id.imageView2);
        text.setText(students.get(position).name);
        if(students.get(position).barcode == null){
            MultiFormatWriter mWriter = new MultiFormatWriter();
            try {
                BitMatrix mMatrix = mWriter.encode(students.get(position).Id, BarcodeFormat.CODE_39, 600, 300);
                BarcodeEncoder mEncoder = new BarcodeEncoder();
                Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
                qrCode.setImageBitmap(mBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
