package org.viper75.architecture_components_example.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.viper75.architecture_components_example.models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);
    @Update
    void update(Note note);
    @Delete
    void delete(Note note);
    @Query("DELETE FROM notes_tbl")
    void deleteAll();
    @Query("SELECT * FROM notes_tbl ORDER BY priority DESC")
    LiveData<List<Note>> getAll();
}
