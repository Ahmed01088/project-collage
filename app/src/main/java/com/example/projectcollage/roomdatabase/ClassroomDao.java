package com.example.projectcollage.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ClassroomDao {
    @Query("SELECT * FROM classrooms")
    LiveData<List<Classroom>> getAll();
    @Insert
    void insert(Classroom... classroom);
    @Delete
    void delete(Classroom... classrooms);
    @Query("delete from classrooms where id =:id")
    void deleteClassById(int id);
}
