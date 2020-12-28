package org.viper75.architecture_components_example.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.viper75.architecture_components_example.models.Note;
import org.viper75.architecture_components_example.tasks.DatabaseOperationsAsyncTask;

@Database(entities = {Note.class}, exportSchema = false, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "notes_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }

        return instance;
    }

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DatabaseOperationsAsyncTask<Void, NoteDao>(instance.noteDao(), DatabaseOperationsAsyncTask.DatabaseOperation.POPULATE)
                    .execute();
        }
    };
}
