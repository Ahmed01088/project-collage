package com.example.projectcollage.database;

public class MessageNew {
    private int id ,senderId,receiverId,classroomId;
    private String message,timestamp;

    public MessageNew(int id, int senderId, int receiverId, int classroomId, String message, String timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.classroomId = classroomId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageNew(int senderId, int receiverId, int classroomId, String message, String timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.classroomId = classroomId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
