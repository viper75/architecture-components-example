package org.viper75.architecture_components_example.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.viper75.architecture_components_example.R;
import org.viper75.architecture_components_example.adapters.NoteRecyclerViewAdapter;
import org.viper75.architecture_components_example.databinding.MainActivityLayoutBinding;
import org.viper75.architecture_components_example.models.Note;
import org.viper75.architecture_components_example.viewmodels.NoteViewModel;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_NOTE_REQUEST_CODE = 1;
    private static final int EDIT_NOTE_REQUEST_CODE = 2;

    private MainActivityLayoutBinding layoutBinding;
    private NoteViewModel mNoteViewModel;
    private NoteRecyclerViewAdapter mNoteRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutBinding = MainActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        setSupportActionBar(layoutBinding.toolbarLayout.getRoot());
        
        mNoteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(NoteViewModel.class);

        mNoteViewModel.getNotes().observe(this, notes -> {
            mNoteRecyclerViewAdapter.submitList(notes);
        });

        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_action) {
            mNoteViewModel.deleteAll();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEditActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, desc, priority);
            mNoteViewModel.insert(note);
        } else if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEditActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditActivity.EXTRA_PRIORITY, 1);
            int id = data.getIntExtra(AddEditActivity.EXTRA_ID, -1);

            Note note = new Note(title, desc, priority);
            note.setId(id);

            if (id != -1) {
                mNoteViewModel.update(note);
                Toast.makeText(this, "Note updated successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update note.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeViews() {
        mNoteRecyclerViewAdapter = new NoteRecyclerViewAdapter(this);
        mNoteRecyclerViewAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra(AddEditActivity.EXTRA_ID, note.getId());
            intent.putExtra(AddEditActivity.EXTRA_TITLE, note.getTitle());
            intent.putExtra(AddEditActivity.EXTRA_DESCRIPTION, note.getDescription());
            intent.putExtra(AddEditActivity.EXTRA_PRIORITY, note.getPriority());

            startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
        });

        RecyclerView notesRecyclerView = layoutBinding.notesRv;

        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(mNoteRecyclerViewAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteViewModel.delete(mNoteRecyclerViewAdapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(notesRecyclerView);

        FloatingActionButton addNoteBtn = layoutBinding.addNoteFab;
        addNoteBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE);
        });
    }
}