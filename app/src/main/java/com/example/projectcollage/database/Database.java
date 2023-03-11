package com.example.projectcollage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.projectcollage.models.Message;
import com.example.projectcollage.models.MessageUser;
import com.example.projectcollage.models.Post;
import com.example.projectcollage.models.User;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="collage_system.db";
    public static final int VERSION=1;
    String string;
    String tableName;
    String tableNameOfPost;
    Context context;
    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }
    public void createDB(SQLiteDatabase sqLiteDatabase,String string ){
        this.string=string;
        tableName = string.replaceAll(" ", "_").toLowerCase();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "message TEXT NOT NULL , time TEXT,image BLOB)");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user(uid Text NOT NULL," +
                "name TEXT NOT NULL ,image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void createTablePosts(SQLiteDatabase sqLiteDatabase,String tableName){
        this.tableNameOfPost=tableName;
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "question TEXT NOT NULL , name TEXT,time Text,image BLOB)");

    }
    public void createTableChatUsers(SQLiteDatabase sqLiteDatabase,String uid){
        String tableUid = "chat_user_"+uid;
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+tableUid+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "message TEXT NOT NULL , time TEXT,image BLOB)");
    }
    public boolean insertUser(User user){
        SQLiteDatabase insert=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("uid",user.getUid());
        values.put("image", user.getByte());
        long result=insert.insert("user",null,values);
        return result!=-1;

    }
    public boolean updateUser(User user){
        SQLiteDatabase insert=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("uid",user.getUid());
        values.put("image", user.getByte());
        String[] args =new String[]{user.getUid()+""};
        long result=insert.update("user",values,"uid=?",args);
        return result!=-1;

    }
    public boolean deleteUser(String uid){
        SQLiteDatabase q=getWritableDatabase();
        String[] args =new String[]{uid+""};
        int result=q.delete("user","uid=?",args);
        return result>0;
    }
    public ArrayList<User> getAllUsers(){
        ArrayList<User>users=new ArrayList<>();
        SQLiteDatabase q=getReadableDatabase();
        Cursor c=q.rawQuery("SELECT * FROM user ORDER BY uid",null);
        if (c.moveToFirst()){
            do {
                String uid=c.getString(0);
                String name=c.getString(1);
                byte []data=c.getBlob(2);
                Bitmap bitmap = null;
                if (data!=null){
                    bitmap= BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
                User user=new User(uid,name,bitmap);
                users.add(user);
            }while (c.moveToNext());
            c.close();
        }
        return users;
    }


    public boolean insertMessage(Message message){
        SQLiteDatabase insert=getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("message",message.getMessage());
            values.put("time",message.getTime());
            values.put("image", message.getByte());
            long result=insert.insert(tableName,null,values);
            return result!=-1;

}
    public boolean insertMessageUser(MessageUser message,String uid){
        SQLiteDatabase insert=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("message",message.getMessage());
        values.put("time",message.getTime());
        values.put("image", message.getByte());//
        //edit
        long result=insert.insert("chat_user_"+uid,null,values);
        return result!=-1;

    }
    public boolean insertPost(Post post){
        SQLiteDatabase insert=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("question",post.getQuestion());
        values.put("time",post.getTime());
        values.put("name", post.getNameOfPerson());
        values.put("image", post.getByte());

        long result=insert.insert("posts",null,values);
        return result!=-1;

    }
    public ArrayList<Message> getAllMessage(){
        ArrayList<Message>messages=new ArrayList<>();
        SQLiteDatabase q=getReadableDatabase();
        Cursor c=q.rawQuery("SELECT * FROM "+ tableName +" ORDER BY id",null);
        if (c.moveToFirst()){
            do {
                int id=c.getInt(0);
                String message=c.getString(1);
                String time=c.getString(2);
                byte []data=c.getBlob(3);
                Bitmap bitmap = null;
                if (data!=null){
                     bitmap= BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
                Message note=new Message(id,message,time,bitmap);
                messages.add(note);
            }while (c.moveToNext());
            c.close();
        }
        return messages;
    }
    public ArrayList<MessageUser> getAllMessageUser(String tableUid){
        ArrayList<MessageUser>messages=new ArrayList<>();
        SQLiteDatabase q=getReadableDatabase();
        Cursor c=q.rawQuery("SELECT * FROM chat_user_"+ tableUid +" ORDER BY id",null);
        if (c.moveToFirst()){
            do {
                int id=c.getInt(0);
                String message=c.getString(1);
                String time=c.getString(2);
                byte []data=c.getBlob(3);
                Bitmap bitmap = null;
                if (data!=null){
                    bitmap= BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
                MessageUser note=new MessageUser(id,message,time,bitmap);
                messages.add(note);
            }while (c.moveToNext());
            c.close();
        }
        return messages;
    }
    public ArrayList<Post> getAllPosts(){
        ArrayList<Post>posts=new ArrayList<>();
        SQLiteDatabase q=getReadableDatabase();
        Cursor c=q.rawQuery("SELECT * FROM "+ tableNameOfPost +" ORDER BY id desc",null);
        if (c.moveToFirst()){
            do {
                int id=c.getInt(0);
                String question=c.getString(1);
                String name=c.getString(2);
                String time=c.getString(3);
                byte []data=c.getBlob(4);
                Bitmap bitmap = null;
                if (data!=null){
                    bitmap= BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
                Post post=new Post(id,name,question,time,bitmap);
                posts.add(post);
            }while (c.moveToNext());
            c.close();
        }
        return posts;
    }
    public boolean deletePost(int id){
        SQLiteDatabase q=getWritableDatabase();
        String[] args =new String[]{id+""};
        int result=q.delete("posts","id=?",args);
        return result>0;
    }
    public boolean deleteMessage(int id,String nameTableUser){
        String name=nameTableUser.replaceAll(" ", "_").toLowerCase();
        SQLiteDatabase q=getWritableDatabase();
        String[] args =new String[]{id+""};
        int result=q.delete("chat_classroom_"+name,"id=?",args);
        return result>0;
    }
    public boolean deleteMessageUser(int id,String uid){
        SQLiteDatabase q=getWritableDatabase();
        String[] args =new String[]{id+""};
        int result=q.delete("chat_user_"+uid,"id=?",args);
        return result>0;
    }


}
