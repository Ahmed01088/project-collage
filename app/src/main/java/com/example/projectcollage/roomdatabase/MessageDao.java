package com.example.projectcollage.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages")
    LiveData<List<Message>>getAllMessage();
    @Insert
    void insertMessage(Message message);
    @Delete
    void deleteMessage(Message message);
    @Query("DELETE FROM messages where id =:id")
    void deleteMessageById(int id);

}
