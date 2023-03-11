package com.example.projectcollage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.projectcollage.models.Message;
import com.example.projectcollage.models.User;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="collage_system_local.db";
    public static final int VERSION=1;
    private Context context;
    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS classrooms(classroom_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name VARCHAR(80),level VARCHAR(20),semester VARCHAR(30),student_id INTEGER,message_id INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS messages(message_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "sender_id INTEGER NOT NULL,receiver_id INTEGER NOT NULL,classroom_id INTEGER,message TEXT,timestamp VARCHAR(20))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS students(uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name INTEGER NOT NULL,phoneNumber VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS classrooms");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS messages");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
    }
    public boolean insertUser(String name,String phoneNumber){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name", name);
        values.put("phoneNumber", name);
        long result=db.insert("users", null, values);
        db.close();
        return result!=-1;

    }
    public boolean insertMessage(String name,int senderId,int receiverId,String message,String timestamp){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("sender_id", senderId);
        values.put("receiver_id", receiverId);
        values.put("message", message);
        values.put("timestamp", timestamp);
        long result=db.insert("messages", null, values);
        db.close();
        return result!=-1;
    }
    public boolean insertClassroom(String name,String level,String semester,int message_id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name", name);
        values.put("level", level);
        values.put("semester", semester);
        values.put("message_id", message_id);
        long result=db.insert("users", null, values);
        db.close();
        return result!=-1;
    }
    public ArrayList<MessageNew> getAllMessage(int classroomId) {
        ArrayList<MessageNew> messages = new ArrayList<>();
        SQLiteDatabase q = getReadableDatabase();
        Cursor c = q.rawQuery("SELECT * FROM messages where classroom_id=:classroomId ORDER BY timstamp", null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                int sender_id = c.getInt(1);
                int receiver_id = c.getInt(2);
                int classroom_id=c.getInt(3);
                String messageText = c.getString(4);
                String timestamp=c.getString(5);
                MessageNew message=new MessageNew(id,sender_id,receiver_id,classroom_id,messageText,timestamp);
                messages.add(message);
            } while (c.moveToNext());
            c.close();
        }
        return messages;

    }
    public ArrayList<ClassroomNew> getAllClassrooms(int studentId) {
        ArrayList<ClassroomNew> classrooms = new ArrayList<>();
        SQLiteDatabase q = getReadableDatabase();
        Cursor c = q.rawQuery("SELECT * FROM classrooms where uid=:studentId ", null);
        if (c.moveToFirst()) {
            do {
                int classroom_id = c.getInt(0);
                String  name = c.getString(1);
                String level = c.getString(2);
                String  semester=c.getString(3);
                int student_id = c.getInt(4);
                int messageId=c.getInt(5);
                ClassroomNew classroom=new ClassroomNew(classroom_id,messageId,student_id,name,level,semester);
                classrooms.add(classroom);
            } while (c.moveToNext());
            c.close();
        }
        return classrooms;

    }

}
