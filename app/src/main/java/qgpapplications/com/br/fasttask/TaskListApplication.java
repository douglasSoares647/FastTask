package qgpapplications.com.br.fasttask;

import android.app.Application;

import qgpapplications.com.br.fasttask.model.util.ApplicationUtil;

/**
 * Created by c1284521 on 09/11/2015.
 */
public class TaskListApplication extends Application {

    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setApplicationContext(getApplicationContext());

    }
}
