package com.example.projectcollage.roomdatabase;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {Message.class, Student.class,Lecturer.class,Classroom.class},version = 1)
public abstract class CollageSystemRoomDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
    public abstract StudentDao studentDao();
    public abstract LecturerDao lecturerDao();
    public abstract ClassroomDao classroomDao();
    public abstract DepartmentDao departmentDao();
    private static volatile CollageSystemRoomDatabase INSTANCE;
    static final ExecutorService databaseWriterExecutor=
            Executors.newFixedThreadPool(4);
    public static CollageSystemRoomDatabase getInstance(final Context context){
        if (INSTANCE==null){
            synchronized (CollageSystemRoomDatabase.class){
                if (INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(), CollageSystemRoomDatabase.class , "collageSystem_db")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;

    }
  private static RoomDatabase.Callback callback=new Callback() {
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
          super.onCreate(db);
          databaseWriterExecutor.execute(() -> {

          });
      }
  };
}
