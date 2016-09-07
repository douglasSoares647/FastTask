package qgpapplications.com.br.fasttask.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import qgpapplications.com.br.fasttask.model.entity.Task;

/**
 * Created by c1284521 on 09/11/2015.
 */
public final class TaskRepository {

    public static long save(Task task){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = TaskContract.getContentValues(task);
        Long id = null;
        if(task.getId()==null){
           id =  db.insert(TaskContract.TABLE,null,values);
        }

        databaseHelper.close();
        db.close();

        return id;
    }


    public static List<Task> findTasksByDate(Integer[] date){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = TaskContract.DAY + " = "+ date[0] + " and " + TaskContract.MONTH + " = " + date[1] + " and "
                + TaskContract.YEAR + " = " + date[2] ;

        Cursor cursor = db.query(TaskContract.TABLE,TaskContract.columns,where,null,null,null,null);

        List<Task> tasks = TaskContract.getTasks(cursor);

        cursor.close();
        databaseHelper.close();
        db.close();

        return tasks;
    }

    public static void deleteSelectedTasks(Task task) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String where = TaskContract.ID + " = " + task.getId();

        db.delete(TaskContract.TABLE, where, null);

        databaseHelper.close();
        db.close();

    }
}
