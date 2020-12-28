package org.viper75.architecture_components_example.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import com.google.android.material.textfield.TextInputEditText;

import org.viper75.architecture_components_example.R;
import org.viper75.architecture_components_example.databinding.AddEditActivityLayoutBinding;

public class AddEditActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "org.viper75.note.ID";
    public static final String EXTRA_TITLE = "org.viper75.note.TITLE";
    public static final String EXTRA_DESCRIPTION = "org.viper75.note.DESCRIPTION";
    public static final String EXTRA_PRIORITY = "org.viper75.note.PRIORITY";

    private AddEditActivityLayoutBinding layoutBinding;
    private TextInputEditText titleInput;
    private TextInputEditText descriptionInput;
    private NumberPicker priorityNumPicker;

    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutBinding = AddEditActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        Toolbar toolbar = layoutBinding.addEditToolbarLayout.getRoot();
        toolbar.setTitle("Add Note");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_note_action) {
            saveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        titleInput = layoutBinding.titleInput;
        descriptionInput = layoutBinding.descriptionInput;
        priorityNumPicker = layoutBinding.priorityNp;

        priorityNumPicker.setMinValue(1);
        priorityNumPicker.setMaxValue(10);

        Intent intent = getIntent();

        noteId = intent.getIntExtra(EXTRA_ID, -1);

        if (noteId != -1) {
            titleInput.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionInput.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priorityNumPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }
    }

    private void saveNote() {
        String title = titleInput.getText().toString();
        String desc = descriptionInput.getText().toString();
        int priority = priorityNumPicker.getValue();

        if (TextUtils.isEmpty(title)) {
            titleInput.setError("Please enter a title.");
            return;
        }

        if (TextUtils.isEmpty(desc)) {
            descriptionInput.setError("Please enter a description.");
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, desc);
        data.putExtra(EXTRA_PRIORITY, priority);

        if (noteId != -1) {
            data.putExtra(EXTRA_ID, noteId);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}