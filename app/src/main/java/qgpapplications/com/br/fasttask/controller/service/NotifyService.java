package qgpapplications.com.br.fasttask.controller.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import qgpapplications.com.br.fasttask.controller.receiver.AlarmReceiver;

/**
 * Created by dhb_s on 9/5/2016.
 */
public class NotifyService extends Service {

    AlarmReceiver alarm = new AlarmReceiver();

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarm.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        alarm.setAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
