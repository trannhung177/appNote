package com.example.myapplication;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GhiChuAdapter extends ArrayAdapter<GhiChu> implements View.OnCreateContextMenuListener {
    public GhiChuAdapter(@NonNull Context context, int resource, @NonNull List<GhiChu> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.layout,parent,false);
        }
        GhiChu ghiChu=getItem(position);
        TextView tieude=view.findViewById(R.id.textView);
        TextView ngaythang=view.findViewById(R.id.textView2);
        TextView noidung=view.findViewById(R.id.textView3);
        //Update thêm cột giờ
        TextView gio = view.findViewById(R.id.textView4);


        tieude.setText(ghiChu.getTieude());
        ngaythang.setText(ghiChu.getNgaythang());
        noidung.setText(ghiChu.getNoidung());

        //Update thêm cột giờ
        gio.setText(ghiChu.getGio());

        view.setOnCreateContextMenuListener(this::onCreateContextMenu);
        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }
}
