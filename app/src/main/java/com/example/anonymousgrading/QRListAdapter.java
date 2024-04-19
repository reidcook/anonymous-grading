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

import java.util.ArrayList;

public class QRListAdapter extends BaseAdapter {
    private ArrayList<String> names;
    private ArrayList<String> ids;
    private Context context;
    private LayoutInflater layoutInflater;

    public QRListAdapter(Context context, ArrayList<String> names, ArrayList<String> ids){
        this.context = context;
        this.names = names;
        this.ids = ids;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_layout, null);
        TextView text = (TextView) convertView.findViewById(R.id.studentInfoTxt);
        ImageView qrCode = (ImageView) convertView.findViewById(R.id.imageView2);
        text.setText(names.get(position));
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            BitMatrix mMatrix = mWriter.encode(ids.get(position), BarcodeFormat.CODE_39, 600, 300);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
            qrCode.setImageBitmap(mBitmap);
        } catch (WriterException e) {
                e.printStackTrace();
        }
        return convertView;
    }
}
