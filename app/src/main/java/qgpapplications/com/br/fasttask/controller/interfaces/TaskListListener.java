package qgpapplications.com.br.fasttask.controller.interfaces;

import java.util.List;

import qgpapplications.com.br.fasttask.model.entity.Task;

/**
 * Created by dhb_s on 9/3/2016.
 */
public interface TaskListListener {

    void onUpdateSelectedTasks(List<Task> tasks);
}
