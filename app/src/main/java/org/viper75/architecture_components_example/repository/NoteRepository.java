package org.viper75.architecture_components_example.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import org.viper75.architecture_components_example.dao.NoteDao;
import org.viper75.architecture_components_example.dao.NoteDatabase;
import org.viper75.architecture_components_example.models.Note;
import org.viper75.architecture_components_example.tasks.DatabaseOperationsAsyncTask;
import org.viper75.architecture_components_example.tasks.DatabaseOperationsAsyncTask.DatabaseOperation;

import java.util.List;

public class NoteRepository {

    private final NoteDao noteDao;

    public NoteRepository(@NonNull Application application) {
        noteDao = NoteDatabase.getInstance(application).noteDao();
    }

    public void insert(Note note) {
        new DatabaseOperationsAsyncTask<Note, NoteDao>(noteDao, DatabaseOperation.INSERT)
                .execute(note);
    }

    public void update(Note note) {
        new DatabaseOperationsAsyncTask<Note, NoteDao>(noteDao, DatabaseOperation.UPDATE)
                .execute(note);
    }

    public void delete(Note note) {
        new DatabaseOperationsAsyncTask<Note, NoteDao>(noteDao, DatabaseOperation.DELETE)
                .execute(note);
    }

    public void deleteAll() {
        new DatabaseOperationsAsyncTask<Void, NoteDao>(noteDao, DatabaseOperation.DELETE_ALL)
                .execute();
    }

    public LiveData<List<Note>> getAll() {
        return noteDao.getAll();
    }
}
