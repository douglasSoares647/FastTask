package qgpapplications.com.br.fasttask.controller.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.List;

import qgpapplications.com.br.fasttask.R;
import qgpapplications.com.br.fasttask.controller.activity.TaskListActivity;
import qgpapplications.com.br.fasttask.model.TaskBusinessService;
import qgpapplications.com.br.fasttask.model.entity.Task;

/**
 * Created by dhb_s on 9/4/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();

        Integer[] dateArray = new Integer[]{calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)};
        List<Task> tasks = TaskBusinessService.findTasksByDate(dateArray);

        if(tasks.size()>0){
            createNotificiation(context);
        }

    }

    private void createNotificiation(Context context) {
        Intent notificationIntent = new Intent(context,TaskListActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_action_calendar)
                .setContentTitle("Fast Task")
                .setContentText("You have peding tasks today!")
                .setContentIntent(pending)
                .setVibrate(new long[]{1000,1000})
                .build();

        notification.ledARGB = 0xFF03A9F4;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        notification.ledOnMS = 2000;
        notification.ledOffMS = 4000;

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }


    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 3600000 , pi);
    }
}
