package com.example.projectcollage.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.ByteArrayOutputStream;

public class MessageUser {
    int id;
    private String sender;
    private String receiver;
    private String message;
    private String time;
    private Bitmap messageImage;
    private Uri imageUri;
    private boolean isSeen;

    public MessageUser() {
    }

    public MessageUser(String message, String time, Bitmap messageImage) {
        this.message = message;
        this.time = time;
        this.messageImage = messageImage;
    }

    public MessageUser(String message, String time, Uri imageUri) {
        this.message = message;
        this.time = time;
        this.imageUri = imageUri;
    }

    public MessageUser(int id, String message, String time, Bitmap messageImage, Uri imageUri) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.messageImage = messageImage;
        this.imageUri = imageUri;
    }

    public MessageUser(int id, String message, String time, Bitmap messageImage) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.messageImage = messageImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Bitmap getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(Bitmap messageImage) {
        this.messageImage = messageImage;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
    public byte[] getByte(){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        if (!(messageImage==null)) {
            messageImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            messageImage.recycle();
        }
        return stream.toByteArray();
    }
}
