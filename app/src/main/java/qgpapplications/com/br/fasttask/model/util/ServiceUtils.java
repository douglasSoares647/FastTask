package qgpapplications.com.br.fasttask.model.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by dhb_s on 9/5/2016.
 */
public class ServiceUtils {

    public static boolean isMyServiceRunning(Class<?> serviceClass, Activity context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
