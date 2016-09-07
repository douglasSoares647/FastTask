package qgpapplications.com.br.fasttask.model;

import java.util.List;

import qgpapplications.com.br.fasttask.model.entity.Task;
import qgpapplications.com.br.fasttask.model.persistence.TaskRepository;
import qgpapplications.com.br.fasttask.model.util.FileUtils;

/**
 * Created by c1284521 on 09/11/2015.
 */
public final class TaskBusinessService {

    public static long save(Task task){
       return TaskRepository.save(task);
    }


    public static List<Task> findTasksByDate(Integer[] date){
        return TaskRepository.findTasksByDate(date);
    }

    public static void deleteSelectedItems(List<Task> tasksToDelete) {

        for(Task task : tasksToDelete) {

            if(!task.getPhoto().trim().isEmpty())
            FileUtils.deletePhotoFromPath(task.getPhoto());

            TaskRepository.deleteSelectedTasks(task);
        }
    }
}
