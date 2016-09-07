package qgpapplications.com.br.fasttask.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import qgpapplications.com.br.fasttask.model.entity.Task;

/**
 * Created by c1284521 on 09/11/2015.
 */
public final class TaskContract {

    public static String DAY = "day";
    public static String MONTH = "month";
    public static String YEAR = "year";
    public static String TABLE = "task";
    public static String MESSAGE = "message";
    public static String PHOTO = "photo";
    public static String ID = "id";

    public static String[] columns = {ID,DAY,MONTH,YEAR,MESSAGE,PHOTO};


    public static String createTableTask(){
        StringBuilder table = new StringBuilder();

        table.append("create table "+ TABLE + "(");
        table.append(ID + " integer primary key, ");
        table.append(DAY + " text, ");
        table.append(MONTH + " text, ");
        table.append(YEAR + " text, ");
        table.append(MESSAGE + " text, ");
        table.append(PHOTO + " text );");

        return table.toString();
    }

    public static ContentValues getContentValues(Task task){
        ContentValues bundle = new ContentValues();

        bundle.put(DAY, task.getDay());
        bundle.put(MONTH, task.getMonth());
        bundle.put(YEAR, task.getYear());
        bundle.put(MESSAGE, task.getMessage());
        bundle.put(PHOTO , task.getPhoto());

        return bundle;
    }


    public static Task getTask(Cursor cursor) {
        while (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getLong(cursor.getColumnIndex(ID)));
            task.setDay(cursor.getString(cursor.getColumnIndex(DAY)));
            task.setMonth(cursor.getString(cursor.getColumnIndex(MONTH)));
            task.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
            task.setMessage(cursor.getString(cursor.getColumnIndex(MESSAGE)));
            task.setPhoto(cursor.getString(cursor.getColumnIndex(PHOTO)));

            return task;
        }
        return null;
    }


    public static List<Task> getTasks(Cursor cursor){
        List<Task> tasks  = new ArrayList<>();
        while(cursor.moveToNext()){
            tasks.add(getTask(cursor));
        }
        return tasks;
    }
}

