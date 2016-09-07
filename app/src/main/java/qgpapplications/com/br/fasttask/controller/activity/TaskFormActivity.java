package qgpapplications.com.br.fasttask.controller.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import qgpapplications.com.br.fasttask.R;
import qgpapplications.com.br.fasttask.model.TaskBusinessService;
import qgpapplications.com.br.fasttask.model.entity.Task;
import qgpapplications.com.br.fasttask.model.util.FileUtils;
import qgpapplications.com.br.fasttask.model.util.StringUtils;

/**
 * Created by c1284521 on 09/11/2015.
 */
public class TaskFormActivity extends AppCompatActivity {
    public static final String PARAM_TASK = "task";
    private static final int CAMERA_RESULT_OK = 2;
    private EditText editTextMessage;
    private Toolbar actionBar;
    private ImageButton btnTakePhoto;
    private ImageButton btnRemovePhoto;
    private String pathPhoto;
    private Bundle date;
    private Task task;
    private ImageView photo;
    private TextView textViewPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_form_activity);
        initDate();
        bindActionBar();
        bindEditTextMessage();
        bindButtonTakePhoto();
        bindTextViewPreview();
        bindButtonRemovePhoto();

    }

    private void bindButtonRemovePhoto() {
        btnRemovePhoto = (ImageButton) findViewById(R.id.btn_remove_image);
        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pathPhoto!=null) {
                    FileUtils.deletePhotoFromPath(pathPhoto);
                    pathPhoto = null;
                }
                photo.setVisibility(View.GONE);
                btnRemovePhoto.setVisibility(View.GONE);
                textViewPreview.setVisibility(View.GONE);
            }
        });
    }

    private void bindTextViewPreview() {
        textViewPreview = (TextView) findViewById(R.id.textViewPreview);
    }

    private void initDate() {
        date = getIntent().getExtras();
    }

    private void bindButtonTakePhoto() {
        btnTakePhoto = (ImageButton) findViewById(R.id.btn_take_picture);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File folder = new File(Environment.getExternalStorageDirectory().toString() + "/FastTask/");
                folder.mkdir();
                String fileName;
                fileName = System.currentTimeMillis() + ".png";
                File photo = new File(folder, fileName);
                Uri uri = Uri.fromFile(photo);
                goToCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(goToCamera, CAMERA_RESULT_OK);

                if(pathPhoto!=null)
                    FileUtils.deletePhotoFromPath(pathPhoto);

                pathPhoto = folder.toString() + "/" + fileName;

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_save:
                onMenuSaveClick();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onMenuSaveClick() {
        bindTask();

        if(StringUtils.validateString(task.getMessage())) {
            task.setId(TaskBusinessService.save(task));
            editTextMessage.setError(null);
            Intent intent = new Intent();
            intent.putExtra(PARAM_TASK, task);
            setResult(RESULT_OK,intent);
            finish();

        }
        else{
            editTextMessage.setError(getString(R.string.error_is_empty));
        }

    }

    private Task bindTask() {
        if(task==null)
        task = new Task();

        task.setDay(String.valueOf(date.getInt(TaskListActivity.PARAM_DAY)));
        task.setMonth(String.valueOf(date.getInt(TaskListActivity.PARAM_MONTH)));
        task.setYear(String.valueOf(date.getInt(TaskListActivity.PARAM_YEAR)));
        task.setMessage(editTextMessage.getText().toString());
        task.setPhoto(pathPhoto);
        return task;
    }

    private void bindEditTextMessage() {
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
    }


    private void bindActionBar() {
        actionBar = (Toolbar) findViewById(R.id.toolbar_task_form);
        actionBar.setTitle(getString(R.string.actionbar_task_form));
        setSupportActionBar(actionBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_RESULT_OK) {
            if (resultCode == RESULT_CANCELED) {
                pathPhoto = null;
            } else {
                textViewPreview.setVisibility(View.VISIBLE);
                photo = (ImageView) findViewById(R.id.imageViewPreview);
                photo.setVisibility(View.VISIBLE);
                btnRemovePhoto.setVisibility(View.VISIBLE);
                Glide.with(TaskFormActivity.this).load(pathPhoto).fitCenter().centerCrop().into(photo);

            }
        }
    }
}
