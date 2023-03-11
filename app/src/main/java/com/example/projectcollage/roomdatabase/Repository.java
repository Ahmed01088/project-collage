package com.example.projectcollage.roomdatabase;

import android.app.Application;


public class Repository {
    MessageDao messageDao;
    StudentDao studentDao;
    LecturerDao lecturerDao;
    ClassroomDao classroomDao;
    DepartmentDao departmentDao;
    public Repository(Application application){
                CollageSystemRoomDatabase database=CollageSystemRoomDatabase.getInstance(application);
        messageDao=database.messageDao();
        studentDao=database.studentDao();
        lecturerDao=database.lecturerDao();
        classroomDao=database.classroomDao();
        departmentDao=database.departmentDao();

    }
    public void insertStudent(Student student){
        CollageSystemRoomDatabase.databaseWriterExecutor.execute(() -> studentDao.insertStudent(student));
    }

}
