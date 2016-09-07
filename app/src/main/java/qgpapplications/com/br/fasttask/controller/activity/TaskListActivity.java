package qgpapplications.com.br.fasttask.controller.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import qgpapplications.com.br.fasttask.R;
import qgpapplications.com.br.fasttask.controller.adapter.TaskListAdapter;
import qgpapplications.com.br.fasttask.controller.interfaces.TaskListListener;
import qgpapplications.com.br.fasttask.controller.receiver.AlarmReceiver;
import qgpapplications.com.br.fasttask.controller.service.NotifyService;
import qgpapplications.com.br.fasttask.model.TaskBusinessService;
import qgpapplications.com.br.fasttask.model.entity.Task;
import qgpapplications.com.br.fasttask.model.util.DateUtils;
import qgpapplications.com.br.fasttask.model.util.ServiceUtils;

/**
 * Created by c1284521 on 09/11/2015.
 */
public class TaskListActivity extends AppCompatActivity implements TaskListListener {

    public static final String PARAM_DAY = "day";
    public static final String PARAM_MONTH = "month";
    public static final String PARAM_YEAR = "year";
    public static final int RESULT_CREATING_TASK = 10;
    private RecyclerView taskList;
    private Toolbar actionBar;
    private List<Task> tasks;
    private TaskListAdapter adapter;
    private FloatingActionButton fab;
    private Bundle date;
    private Calendar calendar;


    private List<Task> tasksToDelete;
    private boolean isSelecting = false;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);
        initDate(false);
        bindActionBar();
        bindTaskList();
        bindFloatingButton();
        initTasksToDelete();

        findTasksByDate();


        startAlarm();


    }

    private void startAlarm() {
        if(!ServiceUtils.isMyServiceRunning(NotifyService.class,TaskListActivity.this)) {
            Intent intent = new Intent(TaskListActivity.this, NotifyService.class);
            startService(intent);
        }


    }

    private void initTasksToDelete() {
        tasksToDelete = new ArrayList<>();
    }

    private void initDate(boolean isTodayClick) {
        date = getIntent().getExtras();

        calendar = Calendar.getInstance();
        if (!(date != null && date.getInt(PARAM_DAY) != 0) || isTodayClick) {
            date = new Bundle();
            date.putInt(PARAM_DAY, calendar.get(Calendar.DAY_OF_MONTH));
            date.putInt(PARAM_MONTH, calendar.get(Calendar.MONTH));
            date.putInt(PARAM_YEAR, calendar.get(Calendar.YEAR));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isSelecting)
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        else
            getMenuInflater().inflate(R.menu.menu_task_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_calendar:
                startCalendarActivity();
                break;

            case R.id.menu_today:
                onMenuTodayClick();
                break;

            case R.id.menu_delete:
                onMenuDeleteClick();
                break;

            case android.R.id.home:
                onMenuHomeClick();
        }

        return super.onOptionsItemSelected(item);
    }

    private void onMenuHomeClick() {
        tasksToDelete.clear();
        isSelecting = false;
        ((TaskListAdapter) taskList.getAdapter()).setTasks(tasks);
        returnToDefaultMenu();

    }

    private void onMenuDeleteClick() {
        tasks.removeAll(tasksToDelete);
        ((TaskListAdapter) taskList.getAdapter()).setTasks(tasks);
        TaskBusinessService.deleteSelectedItems(tasksToDelete);
        returnToDefaultMenu();
    }

    private void onMenuTodayClick() {
        initDate(true);
        findTasksByDate();
    }

    private void startCalendarActivity() {
        Intent intent = new Intent(TaskListActivity.this, CalendarActivity.class);
        startActivity(intent);
        finish();
    }

    private void bindFloatingButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToTaskForm = new Intent(TaskListActivity.this, TaskFormActivity.class);
                goToTaskForm.putExtras(date);
                startActivityForResult(goToTaskForm, RESULT_CREATING_TASK);
            }
        });

    }

    private void bindTaskList() {
        taskList = (RecyclerView) findViewById(R.id.task_list);
        LinearLayoutManager llManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        taskList.setLayoutManager(llManager);

    }

    private void bindActionBar() {
        if (actionBar == null)
            actionBar = (Toolbar) findViewById(R.id.toolbar_task_list);

        actionBar.setTitle(DateUtils.getFormatedDate(date.getInt(PARAM_DAY), date.getInt(PARAM_MONTH), date.getInt(PARAM_YEAR)));
        actionBar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));

        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    @Override
    protected void onResume() {

        super.onResume();
    }

    private void findTasksByDate() {
        Integer[] dateArray;
        dateArray = new Integer[]{date.getInt(PARAM_DAY), date.getInt(PARAM_MONTH), date.getInt(PARAM_YEAR)};
        returnToDefaultMenu();

        tasks = TaskBusinessService.findTasksByDate(dateArray);
        adapter = new TaskListAdapter(tasks, TaskListActivity.this);
        adapter.setTaskListListener(this);
        taskList.setAdapter(adapter);

        //Change the action bar title
        bindActionBar();
    }


    @Override
    public void onUpdateSelectedTasks(List<Task> tasks) {
        this.tasksToDelete.clear();
        this.tasksToDelete.addAll(tasks);

        //invalidate when the user do the first long lick.
        if (!isSelecting) {
            invalidateOptionsMenu();
        }

        if (tasks.size() > 0) {
            isSelecting = true;
        } else {
            returnToDefaultMenu();
        }

        refreshActionBar(tasks);

    }

    private void returnToDefaultMenu() {
        isSelecting = false;
        invalidateOptionsMenu();
        tasksToDelete.clear();
        bindActionBar();
    }

    private void refreshActionBar(List<Task> tasks){
        if(tasks.size()>0) {
            actionBar.setTitle(String.valueOf(tasks.size()));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundColor(Color.GRAY);
        }
        else{
            bindActionBar();
        }
    }

    @Override
    public void onBackPressed() {
        if(isSelecting){
            onMenuHomeClick();
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_CREATING_TASK){
            if(resultCode == RESULT_OK){
                addNewTask((Task) data.getParcelableExtra(TaskFormActivity.PARAM_TASK));
            }
        }
    }

    private void addNewTask(Task task) {
        tasks.add(task);
        ((TaskListAdapter) taskList.getAdapter()).setTasks(tasks);
    }
}
