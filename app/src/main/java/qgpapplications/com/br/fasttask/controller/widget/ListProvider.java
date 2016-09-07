package qgpapplications.com.br.fasttask.controller.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import qgpapplications.com.br.fasttask.R;
import qgpapplications.com.br.fasttask.model.TaskBusinessService;
import qgpapplications.com.br.fasttask.model.entity.Task;

/**
 * Created by c1284521 on 18/11/2015.
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<Task> listItemList = new ArrayList();
    private Context context;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent){
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        populateListItem();
    }

    private void populateListItem() {
        Calendar calendar = Calendar.getInstance();
        Integer[] date = new Integer[]{calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.YEAR)};
        listItemList = TaskBusinessService.findTasksByDate(date);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row=new RemoteViews(context.getPackageName(),
                R.layout.task_list_item);

        row.setTextViewText(R.id.textViewItem, listItemList.get(i).getMessage());


        Intent intent = new Intent();
        Bundle extras = new Bundle();

        extras.putString(WidgetProvider.ACTION_CLICK, listItemList.get(i).getMessage());
        intent.putExtras(extras);
        row.setOnClickFillInIntent(R.id.textViewItem, intent);

        return(row);

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
