package org.viper75.architecture_components_example.tasks;

import android.os.AsyncTask;

import org.viper75.architecture_components_example.dao.NoteDao;
import org.viper75.architecture_components_example.models.Note;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseOperationsAsyncTask<M, D> extends AsyncTask<M, Void, Void> {
    private final D dao;
    private final DatabaseOperation operation;

    public enum DatabaseOperation {
        DELETE_ALL,
        DELETE,
        INSERT,
        UPDATE,
        POPULATE
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(M... ms) {

        if (dao instanceof NoteDao) {
            NoteDao noteDao = (NoteDao) dao;

            switch (operation) {
                case DELETE:
                    Note[] deleteNotes = (Note[]) ms;
                    noteDao.delete(deleteNotes[0]);
                    break;
                case INSERT:
                    Note[] insertNotes = (Note[]) ms;
                    noteDao.insert(insertNotes[0]);
                    break;
                case UPDATE:
                    Note[] updateNotes = (Note[]) ms;
                    noteDao.update(updateNotes[0]);
                    break;
                case DELETE_ALL:
                    noteDao.deleteAll();
                    break;
                case POPULATE:
                    noteDao.insert(new Note("Title 1", "Description 1", 3));
                    noteDao.insert(new Note("Title 2", "Description 2", 1));
                    noteDao.insert(new Note("Title 3", "Description 3", 5));
                    break;
            }
        }

        return null;
    }
}
