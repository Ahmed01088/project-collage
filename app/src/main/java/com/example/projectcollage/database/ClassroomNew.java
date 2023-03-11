package com.example.projectcollage.database;

public class ClassroomNew {
    private int classroomId,messageId,studentId;
    private String name,level,semester;

    public ClassroomNew(int classroomId, int messageId, int studentId, String name, String level, String semester) {
        this.classroomId = classroomId;
        this.messageId = messageId;
        this.studentId = studentId;
        this.name = name;
        this.level = level;
        this.semester = semester;

    }

    public ClassroomNew(int messageId, int studentId, String name, String level, String semester) {
        this.messageId = messageId;
        this.studentId = studentId;
        this.name = name;
        this.level = level;
        this.semester = semester;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
