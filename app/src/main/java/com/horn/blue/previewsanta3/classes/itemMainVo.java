package com.horn.blue.previewsanta3.classes;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class itemMainVo {

    private String titulo;
    private Bitmap img;

    public itemMainVo(String titulo) {
        this.titulo = titulo;
    }
    public itemMainVo(Bitmap img) {
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
