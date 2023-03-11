package com.example.projectcollage.roomdatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses",
        foreignKeys = {
                @ForeignKey(entity = Lecturer.class,
                        parentColumns ={"id"},childColumns = {"lecturerId"},
                        onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Department.class,
                        parentColumns ={"id"},childColumns = {"departmentId"},
                        onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String courseCode;
    private int lecturerId;
    private Level level;
    private int departmentId;
    private Semester semester;

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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
