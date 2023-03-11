package com.example.projectcollage.models;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Post {
    private int id;
    private String nameOfPerson;
    private String question;
    private String comment;
    private String time;
    private Bitmap imagePost;
    public Post() {
    }

    public Post(String nameOfPerson, String question, String time, Bitmap imagePost) {
        this.nameOfPerson = nameOfPerson;
        this.question = question;
        this.time = time;
        this.imagePost = imagePost;
    }

    public Post(int id, String nameOfPerson, String question, String time, Bitmap imagePost) {
        this.id = id;
        this.nameOfPerson = nameOfPerson;
        this.question = question;
        this.time = time;
        this.imagePost = imagePost;
    }

    public Post(int id, String nameOfPerson, String question, String time) {
        this.id = id;
        this.nameOfPerson = nameOfPerson;
        this.question = question;
        this.time = time;
    }

    public Post(String nameOfPerson, String question, String time) {
        this.nameOfPerson = nameOfPerson;
        this.question = question;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return imagePost;
    }

    public void setBitmap(Bitmap imagePost) {
        this.imagePost = imagePost;
    }

    public String getNameOfPerson() {
        return nameOfPerson;
    }

    public void setNameOfPerson(String nameOfPerson) {
        this.nameOfPerson = nameOfPerson;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public byte[] getByte(){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        if (!(imagePost==null)) {
            imagePost.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imagePost.recycle();
        }
        return stream.toByteArray();
    }
}
