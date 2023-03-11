package com.example.projectcollage.models;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
public class User {
    private String uid;
    private String name;
    private Bitmap imageBitmap;
    public User(String uid, String name, Bitmap imageURL) {
        this.uid = uid;
        this.name = name;
        this.imageBitmap = imageURL;
    }

    public User() {
    }

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public byte[] getByte(){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        if (!(imageBitmap ==null)) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageBitmap.recycle();
        }
        return stream.toByteArray();
    }
}
