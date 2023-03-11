package com.example.projectcollage.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students")
    LiveData<List<Student>>getAllStudents();
    @Insert
    void insertStudent(Student student);
    @Delete
    void deleteStudent(Student student);
    @Query("DELETE FROM students where uid =:id")
    void deleteStudentByUid(String id);
}
