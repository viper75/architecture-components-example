package org.viper75.architecture_components_example.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.viper75.architecture_components_example.R;
import org.viper75.architecture_components_example.adapters.NoteRecyclerViewAdapter;
import org.viper75.architecture_components_example.databinding.MainActivityLayoutBinding;
import org.viper75.architecture_components_example.viewmodels.NoteViewModel;

public class MainActivity extends AppCompatActivity {

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

    private void initializeViews() {
        mNoteRecyclerViewAdapter = new NoteRecyclerViewAdapter(this);

        RecyclerView notesRecyclerView = layoutBinding.notesRv;

        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(mNoteRecyclerViewAdapter);
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
}