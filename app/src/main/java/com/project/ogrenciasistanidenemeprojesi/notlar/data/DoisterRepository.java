package com.project.ogrenciasistanidenemeprojesi.notlar.data;
//güncelleme silme işlemi oluşturun ve depoların ve test odası veritabanı
//yaratıldı, hepsi birlikte çalışıyor.
//V şu andaki görevimizle, depomuz(repository) ile iç altyapımız arasındaki bağlantı da budur.
//roomdatabase ile veri tabanı ile kodumuzu çok daha basit hale getirir,  bir şeyler silmek, eklemek,güncellemek basitleşir.

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.project.ogrenciasistanidenemeprojesi.notlar.model.Task;
import com.project.ogrenciasistanidenemeprojesi.notlar.util.TaskRoomDatabase;

import java.util.List;

public class DoisterRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
         TaskRoomDatabase.databaseWriterExecutor.execute( () -> taskDao.insertTask(task));
    }
    public LiveData<Task> get(long id) { return taskDao.get(id); }

    public void update(Task task) {
         TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.update(task));
    }

    public void delete(Task task) {
         TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.delete(task));
    }

}
