package qgpapplications.com.br.fasttask.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import qgpapplications.com.br.fasttask.R;

/**
 * Created by c1284521 on 09/11/2015.
 */
public class CalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Toolbar actionBar;
    private Calendar calendar;
    private Bundle date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        bindCalendarView();
        bindActionBar();
    }

    private void bindActionBar() {
        actionBar = (Toolbar) findViewById(R.id.toolbar_calendar);
        actionBar.setTitle(getString(R.string.toolbar_calendar));

        setSupportActionBar(actionBar);
    }

    private void bindCalendarView() {
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, boolean b) {
                date = new Bundle();
                date.putInt(TaskListActivity.PARAM_DAY, calendarDay.getDay());
                date.putInt(TaskListActivity.PARAM_MONTH, calendarDay.getMonth());
                date.putInt(TaskListActivity.PARAM_YEAR, calendarDay.getYear());
                Intent goToTaskActivity = new Intent(CalendarActivity.this, TaskListActivity.class);
                goToTaskActivity.putExtras(date);
                startActivity(goToTaskActivity);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_today:
                onMenuTodayClick();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void onMenuTodayClick() {
        calendar = Calendar.getInstance();
        date = new Bundle();
        date.putInt(TaskListActivity.PARAM_DAY, calendar.get(Calendar.DAY_OF_MONTH));
        date.putInt(TaskListActivity.PARAM_MONTH, calendar.get(Calendar.MONTH));
        date.putInt(TaskListActivity.PARAM_YEAR, calendar.get(Calendar.YEAR));

        Intent goToTaskActivity = new Intent(CalendarActivity.this, TaskListActivity.class);
        goToTaskActivity.putExtras(date);
        startActivity(goToTaskActivity);
        finish();

    }
}
