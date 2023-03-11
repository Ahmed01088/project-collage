package com.example.projectcollage.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LecturerDao {
    @Query("SELECT * FROM lecturers")
    LiveData<List<Lecturer>> getAll();
    @Insert
    void insertLecturer(Lecturer lecturer);
    @Delete
    void deleteLecturer(Lecturer lecturer);
    @Query("DELETE FROM lecturers where lid =:id")
    void deleteLecturerById(int id);}
