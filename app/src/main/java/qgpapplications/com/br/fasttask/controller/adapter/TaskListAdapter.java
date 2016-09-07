package qgpapplications.com.br.fasttask.controller.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import qgpapplications.com.br.fasttask.R;
import qgpapplications.com.br.fasttask.controller.interfaces.TaskListListener;
import qgpapplications.com.br.fasttask.model.entity.Task;

/**
 * Created by c1284521 on 09/11/2015.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private List<Task> tasks;
    private Activity context;

    private boolean isSelecting = false;
    private List<Task> selectedItems;


    private TaskListListener taskListListener;

    public TaskListAdapter(List<Task> tasks, Activity context) {
        this.tasks = tasks;
        this.context = context;

        selectedItems = new ArrayList<>();

    }

    public void setTaskListListener(TaskListListener taskListListener) {
        this.taskListListener = taskListListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(context).inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Task task = tasks.get(position);
        if (task.getPhoto() == null) {
            task.setPhoto("");
        }

        holder.btnShowImage.setColorFilter(context.getResources().getColor(R.color.colorAccent));
        holder.btnShowImage.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
        holder.imageViewMessage.setVisibility(View.GONE);

        holder.textViewMessage.setText(task.getMessage());

        if (!isSelecting) {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }

        if (!task.getPhoto().isEmpty()) {
            Glide.with(context).load(task.getPhoto()).fitCenter().centerCrop().into(holder.imageViewMessage);
            holder.btnShowImage.setVisibility(View.VISIBLE);

        } else {
            holder.btnShowImage.setVisibility(View.GONE);

        }
        holder.btnShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationClick(holder.btnShowImage, holder.imageViewMessage, task);


            }
        });



        //click to handle the button animation and the selection mode
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationClick(holder.btnShowImage, holder.imageViewMessage, task);

                handleSelection(task, holder);
            }
        });

        //LongClick to select the item
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (selectedItems.size() == 0 || !isSelecting) {
                    isSelecting = true;
                    holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorBackgroundSelected));
                    selectedItems.add(task);
                    notifyDataSetChanged();
                }
                taskListListener.onUpdateSelectedTasks(selectedItems);
                return true;
            }
        });

    }

    private void handleSelection(Task task, ViewHolder holder) {
        if (isSelecting) {
            if (selectedItems.contains(task)) {
                selectedItems.remove(task);
                holder.cardView.setCardBackgroundColor(Color.WHITE);
                if (selectedItems.size() == 0) {
                    isSelecting = false;
                }
            } else {
                selectedItems.add(task);
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorBackgroundSelected));
            }
            notifyDataSetChanged();
        }

        taskListListener.onUpdateSelectedTasks(selectedItems);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void animationClick(ImageButton btnShowImage, ImageView imageViewMessage, Task task) {
        Animation ranim = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        btnShowImage.setAnimation(ranim);
        if (new File(task.getPhoto()).exists()) {
            if (imageViewMessage.getVisibility() == View.GONE) {
                imageViewMessage.setVisibility(View.VISIBLE);
                btnShowImage.setImageResource(R.drawable.ic_hardware_keyboard_arrow_up);
            }
            else {
                btnShowImage.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                imageViewMessage.setVisibility(View.GONE);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;
        private ImageButton btnShowImage;
        private ImageView imageViewMessage;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewMessage = (TextView) itemView.findViewById(R.id.textViewItem);
            imageViewMessage = (ImageView) itemView.findViewById(R.id.imageViewItem);
            btnShowImage = (ImageButton) itemView.findViewById(R.id.buttonShowImage);
            cardView = (CardView) itemView.findViewById(R.id.cardViewItem);
        }
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        isSelecting = false;
        selectedItems.clear();

        notifyDataSetChanged();
    }
}
