package com.ecut.roombasic;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insertWotds(Word... words);
    @Update
    int updateWords(Word... words);
    @Delete
    void deleteWords(Word... words);
    @Query("DELETE FROM WORD")
    void deleteAllWords();
    @Query("SELECT * FROM WORD ORDER BY ID DESC")
//    List<Word> getAllWords();
    LiveData<List<Word>> getAllWordsLive();
}

