package com.horn.blue.previewsanta3.adapters;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.horn.blue.previewsanta3.R;
import com.horn.blue.previewsanta3.classes.itemMainVo;

import java.util.ArrayList;

public class adapterMain extends RecyclerView.Adapter<adapterMain.ViewHolderDatos> {

    ArrayList<itemMainVo> items;

    public adapterMain(ArrayList<itemMainVo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.conten_grid_main, null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.imageView.setImageBitmap(items.get(i).getImg());
        viewHolderDatos.textView.setText(items.get(i).getTitulo());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgGridMain);
            textView = itemView.findViewById(R.id.txtvGridMain);
        }
    }
}
