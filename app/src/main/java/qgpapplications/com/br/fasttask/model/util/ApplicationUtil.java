package qgpapplications.com.br.fasttask.model.util;

import android.content.Context;

/**
 * Created by c1284521 on 09/11/2015.
 */
public final class ApplicationUtil {

    public static Context applicationContext;

    public ApplicationUtil() {
        super();
    }

    public static Context getContext() {
        return applicationContext;
    }


    public static void setApplicationContext(Context context) {
        applicationContext = context;
    }

}
