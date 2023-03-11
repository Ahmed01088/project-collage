package com.example.projectcollage.roomdatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "classrooms",
        foreignKeys = {
               @ForeignKey(entity = Lecturer.class,
        parentColumns ={"id"},childColumns = {"senderId"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Student.class,
        parentColumns ={"id"},childColumns = {"senderId"},onDelete = ForeignKey.CASCADE,onUpdate = ForeignKey.CASCADE)})
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int senderId;
    private int receiverId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
