package com.example.projectcollage.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.ByteArrayOutputStream;

public class Message {
    int id;
    private String sender;
    private String receiver;
    private String message;
    private String time;
    private Bitmap messageImage;
    private Uri imageUri;
    private boolean isSeen;

    public Message(int id, String message, String time) {
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public Message(int id, String message, String time, Bitmap messageImage) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.messageImage = messageImage;
    }

    public Message(String sender, String receiver, String message, String time, boolean isSeen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
        this.isSeen = isSeen;
    }

    public Message() {
    }

    public Message(String message, String time, Uri imageUri) {
        this.message = message;
        this.time = time;
        this.imageUri = imageUri;
    }

    public Message(String message, String time) {
        this.message = message;
        this.time = time;
    }

    public Message(String message, String time, Bitmap messageImage) {
        this.message = message;
        this.time = time;
        this.messageImage = messageImage;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Bitmap getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(Bitmap messageImage) {
        this.messageImage = messageImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
