package qgpapplications.com.br.fasttask.controller.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by c1284521 on 18/11/2015.
 */
public class RemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListProvider(this.getApplicationContext(), intent));
    }
}
